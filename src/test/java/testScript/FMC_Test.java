package testScript;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService1;
import io.opentelemetry.api.trace.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.DataGenerator;

public class FMC_Test {
	String token;
	String otp;
	String Email;
	String password="Pass@123";
	String UserID;
	BaseService1 bs = new BaseService1();
	

	@Test(enabled=false)
	public void createToken1() {
		RestAssured.baseURI = "http://Fmc-env.eba-5akrwvvr.us-east-1.elasticbeanstalk.com";
		Response res = RestAssured.given().log().all().headers("Accept", "application/json").when().get("/fmc/token");
		System.out.println(res.asPrettyString());
		token = res.jsonPath().getString("accessToken");

	}
	
	@Test 
	public void createToken() {
		BaseService1 bs = new BaseService1();

		Map<String, String> headerMAP = new HashMap<String, String>();
		headerMAP.put("Accept", "application/json");

		Response res = bs.executeGetApi("/fmc/token", headerMAP);
		System.out.println(res.asPrettyString());
		token = res.jsonPath().getString("accessToken");

	}
	

	@SuppressWarnings("unchecked")
	@Test(priority = 1,enabled=false)
	public void createLogin1() {
		JSONObject emailSignUpPayLoad = new JSONObject();
		Email=DataGenerator.getEmailId();
		emailSignUpPayLoad.put("email_id", Email);
		Response res = RestAssured.given()
				 .log().all().
				 headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.headers("Authorization", "Bearer " + token)
				.body(emailSignUpPayLoad).when().post("/fmc/email-signup-automation");
		System.out.println("Post status code " + res.getStatusCode());
		System.out.println(res.asPrettyString());
		otp=res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.statusCode(), 200);
		

	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void createLogin() {
		
		Map<String, String> headerMAP = new HashMap<String, String>();
		headerMAP.put("Content-Type", "application/json");
		headerMAP.put("Accept", "application/json");
		headerMAP.put("Authorization", "Bearer " + token);
		headerMAP.put("Accept", "application/json");
		
		JSONObject emailSignUpPayLoad = new JSONObject();
		Email=DataGenerator.getEmailId();
		emailSignUpPayLoad.put("email_id", Email);
		
		
		Response res=bs.executePOSTApi("/fmc/email-signup-automation", headerMAP, emailSignUpPayLoad);
		
		System.out.println("Post Feednack "+res.asPrettyString());
		otp=res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.statusCode(), 201);	
		
	}
	

	@SuppressWarnings("unchecked")
	@Test(priority = 2,enabled=false)
	public void verifyOtp1() {
		
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", Email);
		emailSignUpPayLoad.put("full_name", DataGenerator.getfirstName());
		emailSignUpPayLoad.put("phone_number", DataGenerator.getphoneNumber());
		emailSignUpPayLoad.put("password", password);
		emailSignUpPayLoad.put("otp", otp);
		
		
		Response res=RestAssured.given()
		.log().all()
		.headers("Content-Type", "application/json")
		.headers("Accept", "application/json")
		.headers("Authorization", "Bearer " + token)
		.body(emailSignUpPayLoad)
		.when()
		.put("/fmc/verify-otp");
		System.out.println("Put status code " + res.getStatusCode());
		System.out.println(res.asPrettyString());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void verifyOtp() {
		
		Map<String,String> mapofHeaders=new HashMap<String,String>();
		mapofHeaders.put("Content-Type", "application/json");
		mapofHeaders.put("Accept", "application/json");
		mapofHeaders.put("Authorization", "Bearer "+token);
		
		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", Email);
		emailSignUpPayLoad.put("full_name", DataGenerator.getfirstName());
		emailSignUpPayLoad.put("phone_number", DataGenerator.getphoneNumber());
		emailSignUpPayLoad.put("password", password);
		emailSignUpPayLoad.put("otp", otp);
		
		Response res=bs.executePUTApi("/fmc/verify-otp", mapofHeaders, emailSignUpPayLoad);
		UserID=res.jsonPath().getString("content.userId");
		System.out.println(res.asPrettyString());
		Assert.assertEquals(res.statusCode(), 200);
		
		
	}
}
