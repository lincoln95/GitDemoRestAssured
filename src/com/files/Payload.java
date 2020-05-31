package com.files;

public class Payload 
{

public static String getReuestBody()
{
	String bodyres="{"+
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
			"}";
	return bodyres;
}


public static String createCC(String testloc,String testuser)
{
	String payload="{\r\n   \"productAttributes\": [\r\n      \"Size\",\r\n      \"Color\"\r\n   ],\r\n   \"locationEpc\": \""+testloc+"\",\r\n   \"userName\": \""+testuser+"\",\r\n   \"invSessionId\": \"12345\"\r\n}";

     return payload;
}


	
}
