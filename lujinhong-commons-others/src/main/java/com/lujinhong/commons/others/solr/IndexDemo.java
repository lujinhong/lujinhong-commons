package com.lujinhong.commons.others.solr;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;

/**
 * date: 2016年8月16日 上午9:49:26
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年8月16日 上午9:49:26
 */

public class IndexDemo {

	private static final String DEFAULT_COLLECTION = "collection_1";
	// private static final String SOLR_ZK = "10.120.69.101:2181/solr";
	private static final String SOLR_ZK = "10.120.69.101:2181/solr55";
	private static final int DOC_NUM = 10000;

	public static void main(String[] args) throws SolrServerException, IOException {

		try (CloudSolrClient client = new CloudSolrClient(SOLR_ZK);) {// cloud模式
			//
			// SolrInputDocument doc = new SolrInputDocument();// 实例化索引Doc
			//
			// doc.addField("id", "ljh3");// 添加主键
			// //doc.addField("score", "100.0");
			// doc.addField("qualifier_s", "best2");
			client.setDefaultCollection(DEFAULT_COLLECTION);
			// client.add(doc);
			// // client.add("collection1", doc);
			// client.commit();

			// 1、每次提交一个文档。注意，不只是commit()效率不高，client.add()的效率也是非常低的，因此需要将所有文档先add进一个collection，然后client.add(collection)
			// 100条就要5秒多，根本不可接受
			Long current = System.currentTimeMillis();
			for (int i = 0; i < DOC_NUM; i++) {
				SolrInputDocument doc2 = new SolrInputDocument();
				// printTimeDuration(current);
				doc2.addField("id", "way1" + i);
				// printTimeDuration(current);
				// doc2.addField("score_s",Collections.singletonMap("set","score"));
				Set set = new HashSet();
				// printTimeDuration(current);
				for (String s : "abc,edf,kkk,lll".split(",")) {
					set.add(s);
				}
				// printTimeDuration(current);
				Map map = new HashMap();
				map.put("set", set);
				doc2.addField("key_ss", map);
				// printTimeDuration(current);
				client.add(doc2);
				// printTimeDuration(current);
				// client.commit();
			}
			client.commit();
			printTimeDuration(current);

			// 2、方式二：将所有文档先add进一个collection，然后client.add(collection)
			current = System.currentTimeMillis();
			//List<SolrInputDocument> docList = new LinkedList<SolrInputDocument>();
			ConcurrentLinkedQueue<SolrInputDocument> docList = new ConcurrentLinkedQueue<SolrInputDocument>();

			for (int i = 0; i < DOC_NUM; i++) {
				SolrInputDocument doc2 = new SolrInputDocument();
				doc2.addField("id", "way2" + i);
				Set set = new HashSet();
				for (String s : "abc,edf,kkk,lll".split(",")) {
					set.add(s);
				}
				Map map = new HashMap();
				map.put("set", set);
				doc2.addField("key_ss", map);
				docList.add(doc2);
			}
			client.add(docList);
			client.commit();
			printTimeDuration(current);

			// 3、方式三：和方式二类似，只是add时指定一个commitWith的参数
			// client.add(docList,2000);
		}

	}

	private static void printTimeDuration(Long current) {

		System.out.println("Time use: " + (System.currentTimeMillis() - current));
	}

}
