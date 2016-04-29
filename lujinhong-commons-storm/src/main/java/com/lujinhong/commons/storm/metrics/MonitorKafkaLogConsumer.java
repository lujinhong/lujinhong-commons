package com.lujinhong.commons.storm.metrics;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.metric.api.IMetricsConsumer;
import backtype.storm.task.IErrorReporter;
import backtype.storm.task.TopologyContext;

public class MonitorKafkaLogConsumer implements IMetricsConsumer {

	public static final Logger LOG = LoggerFactory
			.getLogger(MonitorKafkaLogConsumer.class);

	@Override
	public void prepare(Map stormConf, Object registrationArgument,
			TopologyContext context, IErrorReporter errorReporter) {

	}

	@Override
	public void handleDataPoints(TaskInfo taskInfo,
			Collection<DataPoint> dataPoints) {

		StringBuilder sb = new StringBuilder();
		for (DataPoint p : dataPoints) {
			sb.append(p.name).append(":").append(p.value);
			LOG.info(sb.toString());
		}

	}

	@Override
	public void cleanup() {

	}

}
