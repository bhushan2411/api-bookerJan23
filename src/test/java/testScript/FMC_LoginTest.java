package testScript;

import org.testng.Assert;
import org.testng.annotations.Test;

import constant.StatusCode;
import io.restassured.response.Response;
import services.FMC_LoginService;
import utilities.DataGenerator;

public class FMC_LoginTest {
	
	String Emailid=DataGenerator.getEmailId();
	String Password="pass@123";
	
	FMC_LoginService FMC_LoginService=new FMC_LoginService();
	@Test
	public void login() {
		Response res=FMC_LoginService.login(Emailid, Password);
		System.out.println(res.asPrettyString());
		Assert.assertEquals(res.statusCode(),StatusCode.ok );
		
	}

}
