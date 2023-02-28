package services;

import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;

import base.BaseService1;
import constant.APIEndPoints;
import io.restassured.response.Response;

public class FMC_LoginService extends BaseService1 {

	GenerateTokenService generateTokenService = new GenerateTokenService();

	@SuppressWarnings("unchecked")
	public Response login(String emailId, String password) {
//		String payload="{\r\n"
//				+ "   \"email_id\":\""+emailId+"\",\r\n"
//				+ "   \"password\":\""+password+"\"\r\n"
//				+ "}\r\n"
//				+ "";
//		
		
		JSONObject loginpayload=new JSONObject();
		loginpayload.put("email_id",emailId);
		loginpayload.put("password",password);

//		Map<String, String> headerMAP = getHeaderHavingAutorizationToken(generateTokenService.getTOKEN());
//		Response res = executePOSTApi(APIEndPoints.LOGIN, headerMAP, loginpayload);
//		return res;
		return login(loginpayload);
		
		
	}
	
	public Response login(JSONObject loginpayload) {
		generateTokenService.getUserID(loginpayload.get("email_id").toString(), loginpayload.get("password").toString());
		Map<String, String> headerMAP = getHeaderHavingAutorizationToken(generateTokenService.getTOKEN());
		Response res = executePOSTApi(APIEndPoints.LOGIN, headerMAP, loginpayload);
		return res;
		
	}
	
	
	@DataProvider(name="loginData")
	public void loginData(){
		String [][] data=new String[1][1];
		data[0][0]="Bhus1@gmail.com";
		data[0][1]="pass123";
		
		
	}

}
