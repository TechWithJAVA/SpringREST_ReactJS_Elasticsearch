package com.yash.es.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yash.es.model.Employee;

@Repository
public class EmpDaoImpl implements EmpDao {

	@Autowired
	Environment env;

	@Autowired
	TransportClient client;

	@Autowired
	Gson gson;

	public Map<String, Object> insertQuery(Employee employee, Map<String, Object> dataMap) {
		// IndexResponse response=null;
		ActionFuture<GetResponse> getResponse = null;
		GetResponse response = null;
		IndexRequest indexRequest = new IndexRequest(env.getProperty("indexname"), env.getProperty("indextype"),
				employee.getEmpId()).source(dataMap);
		Map<String, Object> error = new HashMap<>();
		error.put("Error", "Unable to create employee");
		try {
			ActionFuture<IndexResponse> actionResponse = client.index(indexRequest);
			// ActionFuture<IndexResponse> actionResponse =client.index(indexRequest);
			IndexResponse response1 = actionResponse.actionGet();
			GetRequest getRequest = new GetRequest(env.getProperty("indexname"), env.getProperty("indextype"),
					employee.getEmpId());
			getResponse = client.get(getRequest);
			response = getResponse.actionGet();
			Map<String, Object> sourceAsMap = response.getSourceAsMap();
			System.out.println(sourceAsMap);
			return sourceAsMap;
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();

		}

		return error;
	}

	public Employee fetchQuery(String empId, boolean flag) {
		Map<String, Object> sourceAsMap = null;
		if (flag == true) {
			GetResponse response = null;
			GetRequest getRequest = new GetRequest(env.getProperty("indexname"), env.getProperty("indextype"), empId);
			ActionFuture<GetResponse> getResponse = null;
			try {
				getResponse = client.get(getRequest);
				response = getResponse.actionGet();
			} catch (Exception e) {
				e.getLocalizedMessage();
			}
			sourceAsMap = response.getSourceAsMap();
		} else {
			QueryBuilder qb = QueryBuilders.matchQuery("EmpId", empId);
			// QueryBuilder qb = QueryBuilders.matchAllQuery();
			// QueryBuilder qb = QueryBuilders.queryStringQuery("EmpId:" + empId);
			SearchResponse sr = client.prepareSearch(env.getProperty("indexname")).setQuery(qb).execute().actionGet();
			System.out.println(sr);
			SearchHits hits = sr.getHits();
			// List<Map<String, Object>> lm=new LinkedList<>();
			for (SearchHit hit : hits) {

				// always returns fine

				System.out.println(hit.getId());
				sourceAsMap = hit.getSourceAsMap();
				// lm.add(sourceAsMap);

				// will be null if any fields specified
				// System.out.println(hit.getSourceAsString());
			}
			// System.out.println(lm);

		}
		 ObjectMapper mapper = new ObjectMapper();
		 Employee employee = mapper.convertValue(sourceAsMap, Employee.class);
		return employee;
	}

	public Map<String, Object> updateQuery(String empId, String employeeJson) {
		UpdateResponse response = null;
		UpdateRequest updateRequest = new UpdateRequest(env.getProperty("indexname"), env.getProperty("indextype"),
				empId).fetchSource(true); // Fetch Object after its
		// update
		Map<String, Object> error = new HashMap<>();
		error.put("Error", "Unable to update employee");
		try {
			updateRequest.doc(employeeJson, XContentType.JSON);
			ActionFuture<UpdateResponse> updateResponse = client.update(updateRequest);
			response = updateResponse.actionGet();
			Map<String, Object> sourceAsMap = response.getGetResult().sourceAsMap();
			return sourceAsMap;
		} catch (Exception e) {
			e.getMessage();
		}

		return error;
	}

	public String deleteQuery(String empId) {
		DeleteResponse response = null;
		DeleteRequest deleteRequest = new DeleteRequest(env.getProperty("indexname"), env.getProperty("indextype"),
				empId);
		ActionFuture<DeleteResponse> deleteResponse = client.delete(deleteRequest);
		response = deleteResponse.actionGet();
		if (!response.getResult().toString().equals("NOT_FOUND"))
			return response.getResult().toString() + " sucessfully";
		else
			return "Pleasae input existed Employee ID";

	}

	public List<Map<String, Object>> matchQuery(Map<String, String> headerMap) {
		Map<String, Object> sourceAsMap = new HashMap<>();
		ArrayList<Map<String, Object>> employeeList = new ArrayList<>();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		Iterator<String> iterator = headerMap.keySet().iterator();

		while (iterator.hasNext()) {
			String propertyName = iterator.next();
			String propertValue = headerMap.get(propertyName);
			MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(propertyName, propertValue);
			boolQueryBuilder.must(matchQuery);
		}
		SearchResponse sr = client.prepareSearch(env.getProperty("indexname")).setQuery(boolQueryBuilder).execute()
				.actionGet();
		SearchHits hits = sr.getHits();

		for (SearchHit hit : hits) {
			sourceAsMap = hit.getSourceAsMap();
			employeeList.add(sourceAsMap);

		}

		return employeeList;
	}

	@Override
	public List<Employee> fetchAll() {
		SearchResponse scrollResp= null;
		Map<String, Object> sourceAsMap = new HashMap<>();
		ArrayList<Employee> employeeList = new ArrayList<>();
		QueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
	    scrollResp = client.prepareSearch(env.getProperty("indexname")).setSearchType(SearchType.DEFAULT)
	                 .setScroll(new TimeValue(60000))                            
	                 .setQuery(queryBuilder)
	                 .setSize(100).execute().actionGet();

	   /* scrollResp = client.prepareSearchScroll(scrollResp.getScrollId())
	                .setScroll(new TimeValue(1000000))
	                .execute()
	                .actionGet();*/
	    SearchHits hits = scrollResp.getHits();
	    for (SearchHit hit : hits) {
			sourceAsMap = hit.getSourceAsMap();
			 ObjectMapper mapper = new ObjectMapper();
			 Employee employee = mapper.convertValue(sourceAsMap, Employee.class);
			 employeeList.add(employee);
		}
	    return employeeList;
	   
	   
	}
}
