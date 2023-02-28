 package testScript;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.DataGenerator;

public class FMC_Test_StandAlone {
	String token;
	String otp;
	String Email=DataGenerator.getEmailId();
	String password="Pass@123";
	int userID;

	@Test
	public void createToken() {
		RestAssured.baseURI = "http://Fmc-env.eba-5akrwvvr.us-east-1.elasticbeanstalk.com";
		Response res = RestAssured.given().log().all().headers("Accept", "application/json").when().get("/fmc/token");
		System.out.println(res.asPrettyString());
		token = res.jsonPath().getString("accessToken");
		Assert.assertEquals(res.statusCode(), 200);

	}

	@Test(priority=1)
	public void createLogin() {
		JSONObject emailSignUpPayLoad = new JSONObject();
		//Email=DataGenerator.getEmailId();
		emailSignUpPayLoad.put("email_id", Email);
		Response res = RestAssured.given()
				 .log().all().
				 headers("Content-Type", "application/json")
				.headers("Accept", "application/json").headers("Authorization", "Bearer " + token)
				.body(emailSignUpPayLoad).when().post("/fmc/email-signup-automation");
		System.out.println("Post status code " + res.getStatusCode());
		System.out.println(res.asPrettyString());
		
		Assert.assertEquals(res.statusCode(), 201);

	}

	@Test(priority=2,enabled=false)
	public void verifyOtp() {
		
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
		otp=res.jsonPath().getString("content.otp");
		userID=res.jsonPath().getInt("content.userId");
		Assert.assertEquals(res.statusCode(), 200);
		
	}
	@Test(priority=3)
	public void login() {
		
		String paload="{\r\n"
				+ "   \"email_id\":\""+Email+"\",\r\n"
				+ "   \"password\":\""+password+"\"\r\n"
				+ "}";
		
		
		Response res=RestAssured.given()
				.log().all()
				.headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.headers("Authorization", "Bearer " + token)
				.body(paload)
				.when()
				.post("/fmc/login");
				
				System.out.println("login method status code " + res.getStatusCode());
				System.out.println(res.asPrettyString());
				userID=res.jsonPath().getInt("content.userId");
				Assert.assertEquals(res.statusCode(), 200);
	}
	
	@Test(priority=4)
	public void addReport() {
		
		String payload="{\r\n"
				+ "   \"reporter_details\": {\r\n"
				+ "       \"request_id\": \"BHUSHANG1\",\r\n"
				+ "       \"user_id\": "+userID+",\r\n"
				+ "       \"report_date\": \"2022-09-01T01:37:30Z\",\r\n"
				+ "       \"reporter_fullname\": \"Samrat Kohli\",\r\n"
				+ "       \"reporter_age\": 40,\r\n"
				+ "       \"reporter_gender\": \"Male\",\r\n"
				+ "       \"reporter_relation\": \"Father\",\r\n"
				+ "       \"parenting_type\": \"Own Child\",\r\n"
				+ "       \"contact_address_type\": \"Home\",\r\n"
				+ "       \"contact_address_line_1\": \"Paud Road\",\r\n"
				+ "       \"contact_address_line_2\": \"Kothrud\",\r\n"
				+ "       \"pincode\": \"411058\",\r\n"
				+ "       \"country\": \"India\",\r\n"
				+ "       \"primary_country_code\": \"+91\",\r\n"
				+ "       \"primary_contact_number\": \"1234567890\",\r\n"
				+ "       \"secondary_country_code\": \"+91\",\r\n"
				+ "       \"secondary_contact_number\": \"1234567891\",\r\n"
				+ "       \"communication_language\": \"English\",\r\n"
				+ "       \"status\": \"INCOMPLETE\"\r\n"
				+ "   },\r\n"
				+ "   \"child_details\": {\r\n"
				+ "       \"fullname\": \"Naira Kohli\",\r\n"
				+ "       \"age\": 10,\r\n"
				+ "       \"gender\": \"Female\",\r\n"
				+ "       \"height\": \"5ft\",\r\n"
				+ "       \"weight\": \"45kg\",\r\n"
				+ "       \"complexion\": \"fair\",\r\n"
				+ "       \"clothing\": \"Red top and black pant\",\r\n"
				+ "       \"birth_signs\": \"mark on right hand\",\r\n"
				+ "       \"other_details\": \"wears spectacles\",\r\n"
				+ "       \"image_file_key\": null,\r\n"
				+ "       \"nickname\":\"Kara\"\r\n"
				+ "   },\r\n"
				+ "   \"incident_details\": {\r\n"
				+ "       \"incident_date\": \"2022-08-15T10:37:30Z\",\r\n"
				+ "       \"incident_brief\": \"Child went missing near the school\",\r\n"
				+ "       \"location\": \"Pune\",\r\n"
				+ "       \"landmark_signs\": \"Near Kasba peth\",\r\n"
				+ "       \"nearby_police_station\": \"City Police station\",\r\n"
				+ "       \"nearby_NGO\": \"Samruddhi NGO\",\r\n"
				+ "       \"allow_connect_police_NGO\": true,\r\n"
				+ "       \"self_verification\": true,\r\n"
				+ "       \"community_terms\": true\r\n"
				+ "   }\r\n"
				+ "}";
		
		Response res=RestAssured.given()
				.log().all()
				.headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.headers("Authorization", "Bearer " + token)
				.body(payload)
				.when()
				.post("/fmc/reports");
				
				System.out.println("ADD Report method status code " + res.getStatusCode());
				System.out.println(res.asPrettyString());
				Assert.assertEquals(res.statusCode(), 200);
		
		
		
		
	}
		
	
}
