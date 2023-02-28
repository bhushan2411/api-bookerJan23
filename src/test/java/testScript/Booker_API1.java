package testScript;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import constant.StatusCode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.request.BookingDates;
import pojo.request.CreateBooking;

public class Booker_API1 {
	
	String token;
	int bookingID;
	BookingDates bookingdates = new BookingDates();
	CreateBooking createbooking = new CreateBooking();
	StatusCode statusCode=new StatusCode();

	@BeforeMethod
	public void createBookingToken() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		Response res = RestAssured.given()
				.headers("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("/auth");
				System.out.println(res.statusCode());
				Assert.assertEquals(res.statusCode(), statusCode.ok);
				System.out.println(res.asPrettyString());
				token = res.jsonPath().get("token");
				System.out.println(token);
	}

	// @Test alternate method
	public void createBookingToken1() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		Response res = RestAssured.given()
				.headers("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("/auth");
		System.out.println(res.statusCode());
		Assert.assertEquals(res.statusCode(), 200);
		System.out.println(res.asPrettyString());

		String payload = "{\"\\\"username\\\" : \\\"admin\\\",\\r\\n\"\r\n"
				+ "				+ \"    \\\"password\\\" : \\\"password123\\\"\"}";
		RequestSpecification reqSpec = RestAssured.given();
		reqSpec.baseUri("https://restful-booker.herokuapp.com");
		reqSpec.headers("Content-Type", "application/json");
		reqSpec.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}");
		Response res1 = reqSpec.post("/auth");
		System.out.println(res1.statusCode());
		Assert.assertEquals(res1.statusCode(), statusCode.ok);
		System.out.println(res1.asPrettyString());

	}

	@Test(enabled=false)
	public void createBooking() {

		BookingDates bookingdates = new BookingDates();
		CreateBooking createbooking = new CreateBooking();
		createbooking.setFirstname("Bhushan");
		createbooking.setLastname("Gdk");
		createbooking.setTotalprice(234);
		createbooking.setAdditionalneeds("Datafilter");

		bookingdates.setCheckin("2018-01-01");
		bookingdates.setCheckout("2018-01-05");

		Response resc = RestAssured.given().log().all().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.body("{\r\n" + "    \"firstname\" : \"Jim\",\r\n" + "    \"lastname\" : \"Brown\",\r\n"
						+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n" + "        \"checkin\" : \"2018-01-01\",\r\n"
						+ "        \"checkout\" : \"2019-01-01\"\r\n" + "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}")
				.when()
				.post("/booking");
		System.out.println("The Status Code is" + resc.statusCode());
		System.out.println(resc.asPrettyString());
		Assert.assertEquals(resc.statusCode(), statusCode.ok);

	}
	
	@Test(priority=1)
	public void createBookingUsingPOJO() {
		
		createbooking.setFirstname("Bhushan");
		createbooking.setLastname("Gdk");
		createbooking.setTotalprice(234);
		createbooking.setAdditionalneeds("Datafilter");
		createbooking.setBookingdates(bookingdates);

		bookingdates.setCheckin("2018-01-01");
		bookingdates.setCheckout("2018-01-05");

		Response resc = RestAssured.given().log().all().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.body(createbooking)
				.when().post("/booking");
		
		bookingID=resc.jsonPath().getInt("bookingid");
		System.out.println("The Status Code is" + resc.statusCode());
		System.out.println(resc.asPrettyString());
		Assert.assertEquals(resc.jsonPath().getString("booking.firstname"), "Bhushan");
		Assert.assertEquals(resc.jsonPath().getString("booking.lastname"), createbooking.getLastname());
		Assert.assertEquals(resc.statusCode(), statusCode.ok);

	}
	
	@Test(priority=2)
	public void getbookingIDs() {
		//int bookingID=963;
		Response res = RestAssured.given().log().all().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.when()
				.get("/booking");
		
		List<Integer> list =res.jsonPath().getList("bookingid");
		Assert.assertEquals(list.contains(bookingID), true);
		Assert.assertEquals(res.statusCode(), statusCode.ok);
		System.out.println(list.size());
		//System.out.println(res.asPrettyString());	
	}
	
	@Test(priority=3)
	public void getBookingID() {
		Response res=RestAssured.given()
		.headers("Accept","application/json")
		.get("booking/"+bookingID);
		System.out.println(res.asPrettyString());
		Assert.assertEquals(res.statusCode(), statusCode.ok);
		}
	
	@Test(priority=4)
	public void updateBooking() {
		
		createbooking.setFirstname("BhushanUpdate");
		createbooking.setLastname("GdkUpdate");
		createbooking.setTotalprice(234);
		createbooking.setAdditionalneeds("DatafilterUpdate");
		createbooking.setBookingdates(bookingdates);

		bookingdates.setCheckin("2018-01-01");
		bookingdates.setCheckout("2018-01-05");
		
		Response res=RestAssured.given()
				.log().all()
		.headers("Content-Type","application/json")
		.headers("Accept","application/json")
		.headers("Cookie","token="+token)
		.body(createbooking)
		.put("booking/"+bookingID);
		System.out.println(res.statusCode());
		System.out.println(res.asPrettyString());
		System.out.println("Updated name is"+res.jsonPath().getString("firstname"));
		Assert.assertEquals(res.jsonPath().getString("firstname"), createbooking.getFirstname());
		Assert.assertEquals(res.statusCode(), statusCode.ok);
		
//       CreateBookingRequest responseBody = res.as(CreateBookingRequest.class);
//		
//		Assert.assertTrue(createbooking.equals(responseBody));
				
	}
	
	@Test(priority=5)
	public void partialUpdate() {
		createbooking.setFirstname("BhushanUpdate1");
		createbooking.setLastname("GdkUpdate1");
		createbooking.setTotalprice(234);
		createbooking.setAdditionalneeds("DatafilterUpdate");
		createbooking.setBookingdates(bookingdates);

		bookingdates.setCheckin("2018-01-01");
		bookingdates.setCheckout("2018-01-05");

		Response res=RestAssured.given()
				.log().all()
		.headers("Content-Type","application/json")
		.headers("Accept","application/json")
		.headers("Cookie","token="+token)
		.body(createbooking)
		.patch("booking/"+bookingID);
		System.out.println(res.statusCode());
		System.out.println(res.asPrettyString());
		System.out.println("Updated name is"+res.jsonPath().getString("firstname"));
		Assert.assertEquals(res.jsonPath().getString("firstname"), createbooking.getFirstname());
		Assert.assertEquals(res.statusCode(), statusCode.ok);
	}
	
	@Test(priority=6)
	public void deletebooking() {
		Response res=RestAssured.given()
				.log().all()
				.headers("Cookie","token="+token)
				.delete("booking/"+bookingID);
		System.out.println( res.statusCode());
		Assert.assertEquals(res.statusCode(), statusCode.createds);	
	}
	
	@Test(priority=7)
	public void verifydelete() {
		Response res = RestAssured.given().log().all().headers("Content-Type", "application/json")
				.headers("Accept", "application/json")
				.when()
				.get("/booking");
		
		List<Integer> list =res.jsonPath().getList("bookingid");
		Assert.assertEquals(list.contains(bookingID), false);
		Assert.assertEquals(res.statusCode(), statusCode.ok);
		
		
		
	}

	

}
