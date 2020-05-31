
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.files.*;

//1)Add book into the library
//2)get the book with author name
//3)get the book with id
//4)delete the book

public class DynamicJson {

@Test(dataProvider="BooksData")

public void addBook(String loc,String user)
{
RestAssured.baseURI="http://216.10.245.166";

Response resp=given().

header("Content-Type","application/json").

body(Payload.createCC(loc,user)).

when().

post("/axis/api/v2.0/inventory/count").

then().assertThat().statusCode(200).

extract().response();

JsonPath js= ResponseMethods.rawToJson(resp);

   String id=js.get("ID");

   System.out.println(id);

   

   //deleteBOok

}

@DataProvider(name="BooksData")

public Object[][]  getData()

{

//array=collection of elements

//multidimensional array= collection of arrays

return new Object[][] {{"00FFFFFFF111111000100009","9363"},{"00FFFFFFF11111100010001A","4253"}, {"00FFFFFFF111111000100001B","533"} };

}





}



