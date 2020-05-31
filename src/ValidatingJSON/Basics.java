package ValidatingJSON;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import ValidatingJSON.ReUsableMethods;
import ValidatingJSON.payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
// validate if Add Place API is workimg as expected 
		//Add place-> Update Place with New Address -> Get Place to validate if New address is present in response
		
		//given - all input details 
		//when - Submit the API -resource,http method
		//Then - validate the response
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		String response  =given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json") //here log.all will log the request in console
		      .body(payload.AddPlace()).when().post("maps/api/place/add/json")
		      .then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))//here log.all will log the response info on console
		      .header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();    // the whole response is captured as String and kept in a String variable
		
		System.out.println(response);
		
		JsonPath js=new JsonPath(response); //for parsing Json and get a key value out of it
		String placeId=js.getString("place_id");
		
		System.out.println(placeId);
		
		//Update Place
		String newAddress = "Summer Walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+placeId+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").
		when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place to validate if New address is present in response
		
	String getPlaceResponse=	given().log().all().queryParam("key", "qaclick123") //authenticate through params to access the api 
		                       .queryParam("place_id",placeId)
		                       .when().get("maps/api/place/get/json")
		                       .then().assertThat().log().all().statusCode(200).extract().response().asString();
	
	JsonPath js1=ReUsableMethods.rawToJson(getPlaceResponse);
	String actualAddress =js1.getString("address");
	System.out.println(actualAddress);
	Assert.assertEquals(actualAddress, "Pacific ocean");
	
	//Cucumber Junit, Testng
	
	
	
	
	
	
	
	
	

		
		
		
		
		
		
		
		
		
		
		
	}

}
