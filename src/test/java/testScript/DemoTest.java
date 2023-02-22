package testScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DemoTest {

	@Test
	public void printPhoneNumbers() {
		
	RestAssured.baseURI="https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io/";
	
	Response res=RestAssured.given()
		.log().all()
		.when()
		.get("/test");
	
		System.out.println(res.asPrettyString());
		System.out.println(res.jsonPath().getList("phoneNumbers.number"));
		List<String> listofPhonenumber=res.jsonPath().getList("phoneNumbers.number");
		System.out.println(listofPhonenumber);
		Assert.assertEquals(listofPhonenumber.size(), 2);
	}
	
	@Test
	public void verifyphoneNumber() {
		
		RestAssured.baseURI="https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io/";
		
		Response res=RestAssured.given()
					.log().all()
					.when()
					.get("/test");
		
			System.out.println(res.asPrettyString());
			System.out.println(res.jsonPath().getList("phoneNumbers"));
		  List<Object> listofPhonenumber=res.jsonPath().getList("phoneNumbers");
		  int totalsize=listofPhonenumber.size();
		  for(int index=0;index<totalsize;index++) {
		  
			Map<String,String> MapofPhoneNumbers= (Map<String,String>)listofPhonenumber.get(index);
			System.out.println(MapofPhoneNumbers);
			System.out.println(MapofPhoneNumbers.get("type")+">"+MapofPhoneNumbers.get("number"));
			
			if(MapofPhoneNumbers.get("type").equals("iPhone")) {
				Assert.assertTrue(MapofPhoneNumbers.get("number").startsWith("3456"));
			}else if(MapofPhoneNumbers.get("type").equals("home")) {
				Assert.assertTrue(MapofPhoneNumbers.get("number").startsWith("0123"));
			}
		
		  }
	}
	
}
