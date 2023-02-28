package services;

import org.testng.Assert;

import base.BaseService1;
import constant.APIEndPoints;
import constant.StatusCode;
import io.restassured.response.Response;
import pojo.request.addReport.Add_Report;
import pojo.request.addReport.ChildDetails;
import pojo.request.addReport.IncidentDetails;
import pojo.request.addReport.ReporterDetails;
import utilities.DataGenerator;


public class FMC_Add_Report {
	String Emailid=DataGenerator.getEmailId();
	String RequestId=DataGenerator.getfirstName();
	String Password="pass@1234";
	int userID;
	
	FMC_LoginService FMC_LoginService=new FMC_LoginService();

	
	public void login() {
	Response res=FMC_LoginService.login(Emailid, Password);
	System.out.println(res.asPrettyString());
	Assert.assertEquals(res.statusCode(),StatusCode.ok );
	userID=res.jsonPath().getInt("content.userId");
	System.out.println("User ID > "+userID);

	
	}
	
	
	public Response addReport() {
		
		   login();
		
		Add_Report addReport = new Add_Report();
		ReporterDetails reporter_details = new ReporterDetails();
		ChildDetails child_details = new ChildDetails();
		IncidentDetails incident_details = new IncidentDetails();

		reporter_details.setRequest_id(RequestId);
		reporter_details.setUser_id(userID);
		reporter_details.setReport_date("2022-08-01T01:37:30Z");
		reporter_details.setReporter_fullname("Bhushan G1");
		reporter_details.setReporter_age(27);
		reporter_details.setReporter_gender("Male");
		reporter_details.setReporter_relation("Father");
		reporter_details.setParenting_type("Own Child");
		reporter_details.setContact_address_type("Home");
		reporter_details.setContact_address_line_1("Paud Road");
		reporter_details.setContact_address_line_2("reporter_details");
		reporter_details.setPincode("431235");
		reporter_details.setCountry("India");
		reporter_details.setPrimary_country_code("+91");
		reporter_details.setPrimary_contact_number("1234567891");
		reporter_details.setSecondary_country_code("+91");
		reporter_details.setSecondary_contact_number("1234567891");
		reporter_details.setCommunication_language("English");
		reporter_details.setStatus("INCOMPLETE");
		
		child_details.setFullname("Bhu s g");
		child_details.setAge(10);
		child_details.setGender("Female");
		child_details.setHeight("5ft");
		child_details.setWeight("45kg");
		child_details.setComplexion("Fair");
		child_details.setClothing("Red top and black pant");
		child_details.setBirth_signs("mark on right hand");
		child_details.setOther_details("wears spectacles");
		child_details.setImage_file_key(null);
		child_details.setNickname("kara");
		
		incident_details.setIncident_date("2022-08-15T10:37:30Z");
		incident_details.setIncident_brief("Child went missing near the school");
		incident_details.setLocation("Pune");
		incident_details.setLandmark_signs("Near Kasba peth");
		incident_details.setNearby_police_station("City Police station");
		incident_details.setNearby_NGO("Sanskriti NGO");
		incident_details.setAllow_connect_police_NGO(true);
		incident_details.setSelf_verification(true);
		incident_details.setCommunity_terms(true);
		
		addReport.setChild_details(child_details);
		addReport.setIncident_details(incident_details);
		addReport.setReporter_details(reporter_details);
		
		
		 BaseService1 baseService1=new BaseService1();
		 GenerateTokenService generateTokenService=new GenerateTokenService();
		String temp=generateTokenService.getTOKEN();
		
		Response res=baseService1.executePOSTApi(APIEndPoints.ADDREPORT, baseService1.getHeaderHavingAutorizationToken(temp), addReport);
		
		return res;

	}
	
}

