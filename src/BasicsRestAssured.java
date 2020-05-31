import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import org.apache.http.annotation.ThreadingBehavior;
import static org.hamcrest.Matchers.equalTo;

public class BasicsRestAssured {

	public static void main(String[] args) 
	{
		//Provide Base uri or host
		
		RestAssured.baseURI="https://babyshoplm.checkpointsystems.com/";
		
		//now we need to tell what are the parameters or resources should be provided along with request
		//and it can be added with the help of given() block
		/*gln=0062921.10005.0&start-ts=1581448140000&end-ts=1581503400000&rid=landmarkbs
		in above example gln is the parameter key and its value is 0062921.10005.0 , similarly start_ts and end_ts are key and 
		   they have their respective values*/
		
		given().
		param("gln","0062921.10005.0").
		param("start-ts","1581448140000").
		param("end-ts","1581503400000").
		param("rid", "landmarkbs").
		when().
		get("axis/api/v1.0/inventory/CCReport/ccList").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("ccList[0].location", equalTo("ABA-60116-ASM")).and().
		header("server", "servername from where response came");
		
		
	
		
		//validating content type of response to be json type as the request we are sending is json type
		//If we get xml or html as response then the assertion fails the T.C
		
		
		
		
		

	}

}
