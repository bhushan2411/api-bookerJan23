package testScript;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import services.FMC_Add_Report;

public class FMC_addReport {
	@Test
	public void addReport() {

		FMC_Add_Report addReport = new FMC_Add_Report();
		Response res = addReport.addReport();
		System.out.println(res.asPrettyString());
		System.out.println("content => " + res.jsonPath().getInt("content"));
		Assert.assertEquals(res.statusCode(), 200);
		Assert.assertTrue(res.jsonPath().getInt("content") > 0);
		Assert.assertEquals(res.jsonPath().getString("status"), "Success");

	}

}
