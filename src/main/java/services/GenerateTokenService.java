package services;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import constant.APIEndPoints;
import io.restassured.response.Response;
import utilities.DataGenerator;


public class GenerateTokenService extends base.BaseService1 {
	String Email = DataGenerator.getEmailId();
	
	public Response getTokenResponse() {
		Map<String, String> headerMap = new HashMap<String, String>();
		Response res = executeGetApi(APIEndPoints.TOKEN, headerMap);
		return res;
	}
	
	public String getTOKEN() {
		Response res = getTokenResponse();
		return res.jsonPath().getString("accessToken");
	}
	
	public Response getEmailSignupResponse(JSONObject emailSignUpPayLoad) {
		
		Map<String, String> headerMAP=	getHeaderHavingAutorizationToken(getTOKEN());
		Response res=executePOSTApi(APIEndPoints.EMAILSignup, headerMAP, emailSignUpPayLoad);
		return res;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public Response getEmailSignupResponse() {

		JSONObject emailSignUpPayLoad = new JSONObject();

		emailSignUpPayLoad.put("email_id", Email);

		Map<String, String> headerMAP = getHeaderHavingAutorizationToken(getTOKEN());
		Response res = executePOSTApi(APIEndPoints.EMAILSignup, headerMAP, emailSignUpPayLoad);
		return res;

	}
	
	public String getOTPfromEmailSignup(JSONObject emailSignUpPayLoad) {
		
		Map<String, String> headerMAP=	getHeaderHavingAutorizationToken(getTOKEN());
		Response res=executePOSTApi(APIEndPoints.EMAILSignup, headerMAP, emailSignUpPayLoad);
		String otp = res.jsonPath().getString("content.otp");
		return otp;
		
	}
	
	public Response getVerifyOTPResponse(JSONObject emailSignUpPayLoad) {
		
		Map<String, String> headerMAP=	getHeaderHavingAutorizationToken(getTOKEN());
		
		Response res=executePUTApi(APIEndPoints.VerifyOTP, headerMAP, emailSignUpPayLoad);
		
		return res;
	}
	
	public int getUserIDfromVerifyOTPResponse(JSONObject verifyOTPPayLoad) {
		String email_id=String.valueOf(verifyOTPPayLoad);
		Response res = getVerifyOTPResponse(verifyOTPPayLoad);
		return res.jsonPath().getInt("content.userId");

	}
	
	@SuppressWarnings("unchecked")

	public int getUserID(String Email, String password) {

		JSONObject emailSignUpPayLoad = new JSONObject();
		emailSignUpPayLoad.put("email_id", Email);
		String otp = getOTPfromEmailSignup(emailSignUpPayLoad);

		JSONObject verifyOTPPayLoad = new JSONObject();
		verifyOTPPayLoad.put("email_id", Email);
		verifyOTPPayLoad.put("full_name", DataGenerator.getfirstName());
		verifyOTPPayLoad.put("phone_number", DataGenerator.getphoneNumber());
		verifyOTPPayLoad.put("password", password);
		verifyOTPPayLoad.put("otp", otp);

		return getUserIDfromVerifyOTPResponse(verifyOTPPayLoad);

	}

}
