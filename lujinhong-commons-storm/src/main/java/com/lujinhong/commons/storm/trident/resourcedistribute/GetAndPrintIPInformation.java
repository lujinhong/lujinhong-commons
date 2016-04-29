package com.lujinhong.commons.storm.trident.resourcedistribute;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class GetAndPrintIPInformation extends BaseFunction {
	private File database = null;
	private Reader reader  = null;

	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		super.prepare(conf, context);
		InputStream is = this.getClass().getResourceAsStream("/ip.mmdb");
		try {
			reader = new Reader(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void cleanup() {
		super.cleanup();
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		String ip = (String) tuple.getValue(0);

		InetAddress address = null;
		try {
			address = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		JsonNode response = null;
		try {
			response = reader.get(address);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(ip + response);
	}
	
}
