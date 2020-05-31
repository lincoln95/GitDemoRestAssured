package com.files;

import static io.restassured.RestAssured.*;



import java.io.File;



import org.testng.Assert;



import io.restassured.RestAssured;

import io.restassured.filter.session.SessionFilter;

import io.restassured.path.json.JsonPath;



public class JiraTest {



public static void main(String[] args) {

// TODO Auto-generated method stub

RestAssured.baseURI="http://localhost:8080";

//Login Scenario and extract the session id from dat and pass it to add comment request to authenticate

SessionFilter session=new SessionFilter();// this variable always keeps the newly created seesion and we can use the session in susequent req

String response=given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{\r\n" +

"    \"username\": \"RahulShetty\",\r\n" +

"    \"password\": \"XXXX11\"\r\n" +

"}").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();

// filter method is used to keep the session cookie of the response when the code is executed
String expectedMessage ="Hi How are you?";

//Add comment to jira id 10101

//we are passing the key as 10101 in request {key}
String addCommentResponse = given().pathParam("key", "10101").log().all().header("Content-Type","application/json").body("{\r\n" +

"    \"body\": \""+expectedMessage+"\",\r\n" +

"    \"visibility\": {\r\n" +

"        \"type\": \"role\",\r\n" +

"        \"value\": \"Administrators\"\r\n" +
  
"    }\r\n" +

"}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all()  /* At runtime rest assured will see the key with 
                                                                                           curly braces and treat it is path param and 
                                                                                           will search if anything is declared as pathParam key
                                                                                           and will retrieve its value from pathParam("key", "10101").*/
//here this adding comment request will use the already created session to authenticate when we use filter method with session 

.assertThat().statusCode(201).extract().response().asString();

JsonPath js=new JsonPath(addCommentResponse);

String commentId= js.getString("id");

//Add Attachment
//The request must have a X-Atlassian-Token: no-check header, if not it is blocked. See Special headers for more information.
given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10101")

.header("Content-Type","multipart/form-data") //here we are not sending ant string , rather we sent a file 

.multiPart("file",new File("jira.txt")).when().  //multipart method is used to attach  the file from a particular path

post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

//Get Issue

String issueDetails=given().filter(session).pathParam("key", "10101")

.queryParam("fields", "comment")   //we only need comment from the response , by drilling down the response with query param

.log().all().when().get("/rest/api/2/issue/{key}").then()

.log().all().extract().response().asString();

System.out.println(issueDetails);

JsonPath js1 =new JsonPath(issueDetails);

int commentsCount=js1.getInt("fields.comment.comments.size()");

for(int i=0;i<commentsCount;i++)

{

String commentIdIssue =js1.get("fields.comment.comments["+i+"].id").toString();

if (commentIdIssue.equalsIgnoreCase(commentId))

{

String message= js1.get("fields.comment.comments["+i+"].body").toString();

System.out.println(message);

Assert.assertEquals(message, expectedMessage);

}

}

}



}

