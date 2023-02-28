package testScript;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import pojo.request.BookingDates;
import pojo.request.CreateBooking;

public class CreateBookingUsingJsonObject {
	
	@Test()
	public void createBooking() {

		Faker faker =new Faker();
		JSONObject jsobobject =new JSONObject();
		//jsobobject.put("firstname","Bhushan");
		jsobobject.put("firstname",faker.name().firstName());
		jsobobject.put("lastname",faker.name().lastName());
		jsobobject.put("totalprice",faker.number());
		jsobobject.put("depositpaid",true);
		
		JSONObject jsobobjectCreateBooking =new JSONObject();
		jsobobjectCreateBooking.put("checkin", "2018-01-01");
		jsobobjectCreateBooking.put("checkout", "2018-02-03");
		
		jsobobject.put("additionalneeds","Breakfast");
		jsobobject.put("bookingdates", jsobobjectCreateBooking);
		
		
		
		

		Response resc = RestAssured.given().log().all().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.body(jsobobject)
				.when()
				.post("/booking");
		System.out.println("The Status Code is" + resc.statusCode());
		System.out.println(resc.asPrettyString());
		Assert.assertEquals(resc.statusCode(), 201);

	}
	

}
