package base;
import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class BaseService1 {
	
	

	public BaseService1() {
		RestAssured.baseURI="http://Fmc-env.eba-5akrwvvr.us-east-1.elasticbeanstalk.com";
		
	}
	
	public Map<String, String> getHeaderHavingAutorizationToken(String token){
		
		Map <String, String> headerMap=new HashMap<String,String>();
		
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		headerMap.put("Authorization", "Bearer " + token);
		
		return headerMap;
		
	}
	
public Map<String, String> getHeaderWithoutgAutorizationToken(){
		
		Map <String, String> headerMap=new HashMap<String,String>();
		
		headerMap.put("Content-Type", "application/json");
		headerMap.put("Accept", "application/json");
		return headerMap;
		
	}

	public Response executeGetApi(String endPoint, Map<String, String> headerMAP) {

		Response res= RestAssured.given()
				.log().all()
				.headers(headerMAP)
				.when()
				.get(endPoint);
		System.out.println(res.asPrettyString());
		System.out.println(res.statusCode());
		return res;

	}
	
	public Response executePOSTApi(String endPoint, Map<String, String> headerMAP,Object payload) {

		Response res= RestAssured.given()
				.log().all()
				.headers(headerMAP)
				.body(payload)
				.when()
				.post(endPoint);
		System.out.println(res.asPrettyString());
		System.out.println(res.statusCode());
		return res;

	}
	
	public Response executePUTApi(String endPoint, Map<String, String> headerMAP,Object payload) {

		Response res= RestAssured.given()
				.log().all()
				.headers(headerMAP)
				.body(payload)
				.when()
				.put(endPoint);
		System.out.println(res.asPrettyString());
		System.out.println(res.statusCode());
		return res;

	}
	
}
