package com.lujinhong.commons.storm.multispout;

import storm.trident.operation.TridentCollector;
import storm.trident.spout.ITridentSpout.Emitter;
import storm.trident.topology.TransactionAttempt;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.tuple.Values;

public class TimerEmitter implements Emitter<Long>, Serializable {
    private static final long serialVersionUID = 1L;
    private static Logger LOG = LoggerFactory.getLogger(TimerEmitter.class);
    AtomicInteger successfulTransactions = new AtomicInteger(0);
    //使用特殊字符，以避免其它消息中含有这个字符串。
    private static final String TIMER_MESSAGE = "$$$TIMERMESSAGE$$$";
    

    @Override
    public void emitBatch(TransactionAttempt tx, Long coordinatorMeta, TridentCollector collector) {
    	try {
    		LOG.info("sleep 10 seconds");
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	Object v = new Values(TIMER_MESSAGE);
    	LOG.info("Emit one timer tuple at :" +   Calendar.getInstance().getTime());
    	collector.emit(Arrays.asList(v));
    }

    @Override
    public void success(TransactionAttempt tx) {
        successfulTransactions.incrementAndGet();
    }

    @Override
    public void close() {
    }

}