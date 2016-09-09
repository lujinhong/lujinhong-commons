package com.lujinhong.commons.others.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;

/**
 * date: 2016年8月16日 上午9:53:53
 * 
 * @author LUJINHONG lu_jin_hong@163.com Function: TODO last modified:
 *         2016年8月16日 上午9:53:53
 */

public class QueryDemo {

	private static final String DEFAULT_COLLECTION = "us";
	private static final String SOLR_ZK = "10.120.69.101:2181/solr55";
//	private static final String SOLR_ZK = "10.120.69.101:2181/solr";

	public static void main(String[] args) throws SolrServerException, IOException {
		try (CloudSolrClient client = new CloudSolrClient(SOLR_ZK);) {// cloud模式
			//根据id查doc
			SolrDocument doc = client.getById(DEFAULT_COLLECTION, "181700000");
			if(null != doc){
			
			for (String field : doc.getFieldNames()) {
				System.out.println(field + " : " + doc.getFieldValues(field));
			}
		}
			
			
			//根据field查doc
//			SolrQuery query = new SolrQuery();
//			//query.setQuery(mQueryString);
//			query.setRequestHandler("/query");
//			query.set("q", "qualifier_col:valuedemo");//返回index_demo
////			query.set("q", "qualifier_col:valuedemo OR id:index_demo2");//返回index_demo
////			query.set("q", "qualifier_col:valuedemo AND id:index_demo2");//返回空
////			query.set("q", "qualifier_col:valuede*");//返回index_demo
//			QueryResponse response = client.query(DEFAULT_COLLECTION, query);
//			for(SolrDocument doc2 :response.getResults()){
//				System.out.println(doc2.getFieldValue("id"));
//			}
		}

	}

}
