package com.lujinhong.commons.storm.trident.mapstate;

import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.FilterNull;
import storm.trident.operation.builtin.MapGet;
import storm.trident.operation.builtin.Sum;
import storm.trident.testing.FixedBatchSpout;
import storm.trident.tuple.TridentTuple;

import com.lujinhong.commons.storm.trident.TridentWordCount.Split;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MapStateWordCount {
    public static class Split extends BaseFunction {
        @Override
        public void execute(TridentTuple tuple, TridentCollector collector) {
            String sentence = tuple.getString(0);
            for (String word : sentence.split(" ")) {
                collector.emit(new Values(word));
            }
        }
    }

    public static StormTopology buildTopology(LocalDRPC drpc) {
        FixedBatchSpout spout =
                new FixedBatchSpout(new Fields("sentence"), 3, new Values(
                        "the cow jumped over the moon"), new Values(
                        "the man went to the store and bought some candy"), new Values(
                        "four score and seven years ago"),
                        new Values("how many apples can you eat"), new Values(
                                "to be or not to be the person"));
        spout.setCycle(true);

        //创建拓扑对象
        TridentTopology topology = new TridentTopology();
        
        //这个流程用于统计单词数据，结果将被保存在wordCounts中
        TridentState wordCounts =
                topology.newStream("spout1", spout)
                        .parallelismHint(16)
                        .each(new Fields("sentence"), new Split(), new Fields("word"))
                        .groupBy(new Fields("word"))
                        .persistentAggregate(new MemoryMapStateFacotry(), new Count(),
                                new Fields("count")).parallelismHint(16);
        //这个流程用于查询上面的统计结果
        topology.newDRPCStream("words", drpc)
                .each(new Fields("args"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"));
                //.each(new Fields("count"), new FilterNull())
              // .aggregate(new Fields("count"), new Sum(), new Fields("sum"));
        return topology.build();
    }

    public static void main(String[] args) throws Exception {
        Config conf = new Config();
        conf.setMaxSpoutPending(20);
        if (args.length == 0) {
            LocalDRPC drpc = new LocalDRPC();
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("wordCounter", conf, buildTopology(drpc));
            for (int i = 0; i < 100; i++) {
                System.out.println("DRPC RESULT: " + drpc.execute("words", "cat the dog jumped"));
                Thread.sleep(1000);
            }
        } else {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopologyWithProgressBar(args[0], conf, buildTopology(null));
        }
    }
}
