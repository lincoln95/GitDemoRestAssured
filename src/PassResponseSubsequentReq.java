import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.files.Payload;
import com.files.Resources;
import com.files.ResponseMethods;

import java.util.*;  
import java.io.*; 



//Here we will capture the response and will check if it is proper with the help of assertion and then pass it to subsequent req.
//the request body for post req is kept in one string variable and then we pass it
public class PassResponseSubsequentReq 
{
	Properties prop=new Properties();
	
	@BeforeMethod
	public void getData() throws IOException
	{
		
	    FileInputStream fis = new FileInputStream("C:\\Users\\BeheraS\\eclipse-workspace\\DemoProject\\src\\com\\files\\data");
	    prop.load(fis); //properties object will load the file from where data needs to be fetched
	    
	    
	}
	
	@Test
	public void Test()
	{
		//Task 1 to send post request and create place id.
		
		
	RestAssured.baseURI=prop.getProperty("HOST");
	Response res=given().
	
	queryParam("key",prop.getProperty("KEY")).
	body(Payload.getReuestBody()).log().all().  //Log all request specification details including parameters, headers and body
	when().
	post(Resources.getPostResource()).
	then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
	body("status",equalTo("OK")).log().all(). //Log all response specification
	extract().response();
	//The response we extract will be in raw format.
	//Task 2 grab the task 1 
 
	JsonPath json=ResponseMethods.rawToJson(res);
	
	String placeID=json.get("placeid");
	
    //Task 3 grab the place id and pass it in delete request 	
   
	given().
	queryParam("key","AIzaSyDIQgAh0B4p0SdyYkyW8tlG-y0yJMfss5Y").
	body("{\"place_id\":"+placeID+"\"}").when().post("/maps/api/place/delete/json").then().assertThat().contentType(ContentType.JSON).and().
	statusCode(200).and().body("status", equalTo("OK"));
	
	}
}