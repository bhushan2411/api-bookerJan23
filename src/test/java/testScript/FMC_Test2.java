package testScript;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseService1;
import io.opentelemetry.api.trace.StatusCode;
import io.restassured.response.Response;
import services.GenerateTokenService;
import utilities.DataGenerator;

public class FMC_Test2 {
	String token;
	String otp;
	String Email;
	String password="Pass@123";
	String UserID;
	BaseService1 bs = new BaseService1();
	
	GenerateTokenService generateTokenService=new GenerateTokenService();
	
	@Test 
	public void createToken() {
		
		
		Response res=generateTokenService.getTokenResponse();
		System.out.println(res.asPrettyString());
		token = res.jsonPath().getString("accessToken");
		Assert.assertTrue(token.length()>0);
		Assert.assertEquals(res.jsonPath().getString("tokenType"), "bearer");

	}
	

	
	
	@SuppressWarnings("unchecked")
	@Test(priority = 1)
	public void createLogin() {

		JSONObject emailSignUpPayLoad = new JSONObject();
		Email = DataGenerator.getEmailId();
		emailSignUpPayLoad.put("email_id", Email);

		Response res = generateTokenService.getEmailSignupResponse(emailSignUpPayLoad);

		System.out.println("Post Feednack " + res.asPrettyString());
		otp = res.jsonPath().getString("content.otp");
		Assert.assertEquals(res.statusCode(), 201);

	}

	
	
	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void verifyOtp() {
		
		JSONObject emailSignUpPayLoad = new JSONObject();
		Email = DataGenerator.getEmailId();
		emailSignUpPayLoad.put("email_id", Email);

		Response res = generateTokenService.getEmailSignupResponse(emailSignUpPayLoad);

		System.out.println("Post Feednack " + res.asPrettyString());
		otp = res.jsonPath().getString("content.otp");
		
		JSONObject verifyOTPPayLoad = new JSONObject();
		verifyOTPPayLoad.put("email_id", Email);
		verifyOTPPayLoad.put("full_name", DataGenerator.getfirstName());
		verifyOTPPayLoad.put("phone_number", DataGenerator.getphoneNumber());
		verifyOTPPayLoad.put("password", password);
		verifyOTPPayLoad.put("otp", otp);
		
		Response res1=generateTokenService.getVerifyOTPResponse(verifyOTPPayLoad);
		UserID=res1.jsonPath().getString("content.userId");
		System.out.println(res1.asPrettyString());
		Assert.assertEquals(res1.statusCode(), 200);
		
		
	}
	
	
	@Test
	public void getUserID() {
		int userid=generateTokenService.getUserID("Bhushan@gmail.com", "Pass@1223");
		System.out.println(userid);
				
		
	}
}
