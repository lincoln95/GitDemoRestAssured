package com.files;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ResponseMethods
{

	public static JsonPath rawToJson(Response res)
	{
		String responseAsString=res.asString();//showing the raw response as string
		JsonPath json= new JsonPath(responseAsString);
		return json;
	}
	
}
