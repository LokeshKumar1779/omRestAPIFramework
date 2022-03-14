package com.spotify.oauth2.utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.authentication.OAuth2Scheme;
import io.restassured.builder.RequestSpecBuilder;
import static com.spotify.oauth2.utils.Route.*;

import javax.naming.AuthenticationException;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.OAuthConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.internal.http.HTTPBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class SpecBuilder {
	

	//static String access_token = "BQCC_Vs4v6GYEfnSH8uqBRN77ta6yoGTDL7YHLkdEmtLSsLZ9Pal59BF1_9FoiKGKcePuJADDFLmBjpNOzR49hUZ8CDNYPfFWCshyYRrJSdG1AwHtp_SVHhcd6UaPSh8UMt5Xyd-MMLy8MUE3nXKQfOH3lz490CO-qB6e8dsHJA1YHFzRXg6Ds7RU0h92zG41D26Vgt_9_ilj9XAWBbgb7K7L0Q2ovpWGsLAXxnyXGMX";
	static String invalidToken  = "12334";
	
	
	public static RequestSpecification getRequestSpec() {
		
		
		RequestSpecification requestSpecification= new RequestSpecBuilder().
				//setBaseUri("https://api.spotify.com").
				setBaseUri(System.getProperty("BASE_URI")).
				setBasePath(BASE_PATH).
			
				addHeader("Authorization", "Bearer "+TokenManager.getToken()).
				log(LogDetail.ALL).
				addFilter(new AllureRestAssured()).
				setContentType(ContentType.JSON).build();
		
		return requestSpecification;
		
		
		
	}
	
	
	public static ResponseSpecification getResponseSpec() {
		
		return new ResponseSpecBuilder().log(LogDetail.ALL).build();
		
	}
	
	public static RequestSpecification getRequestSpecWithInvalidToken() {
		
		
		RequestSpecification requestSpecification= new RequestSpecBuilder().
				setBaseUri(System.getProperty("BASE_URI")).
				setBasePath(BASE_PATH).addHeader("Authorization", "Bearer "+invalidToken).
				log(LogDetail.ALL).
				setContentType(ContentType.JSON).build();
		
		return requestSpecification;
		
		
		
	}

}
