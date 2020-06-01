import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
public class BasicPOSTReq {

	public static void main(String[] args) 
	{
		//In Post request we donot send any parameter  at end point of the request
		//It should be send as request body
		//Path parameters are not meant for post request,for post req we use query parameter and body parameters.
		//https://maps.googleapis.com/maps/api/place/add/json?key=AIzaSyDIQgAh0B4p0SdyYkyW8tlG-y0yJMfss5Y
		
		System.out.println("Basic req");
		
		RestAssured.baseURI="https://maps.googleapis.com";
		given().
		
		queryParam("key","AIzaSyDIQgAh0B4p0SdyYkyW8tlG-y0yJMfss5Y").
		body("{"+
  "\"location\": {"+
    "\"lat\": -33.8669710,"+ 
    "\"lng\": 151.1958750"+
  "},"+
  "\"accuracy\": 50,"+
  "\"name\": \"Google Shoes!\","+
  "\"phone_number\": \"(02) 9374 4000\","+
  "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","+
  "\"types\": [\"shoe_store\"],"+
  "\"website\": \"http://www.google.com.au/\","+
  "\"language\": \"en-AU\""+
"}").
		when().
		post("/maps/api/place/add/json").
		then().log().all().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK")).header("server", "ubuntu");
		
	// Create a place =response (place id)
		
		// delete Place = (Request - Place id)	
		
		//Add place ->updtate place with new address-> get place to validate if new address is present in response
		
		
		
		
		 

	}

}
