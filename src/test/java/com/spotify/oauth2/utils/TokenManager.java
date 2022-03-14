package com.spotify.oauth2.utils;
import static com.spotify.oauth2.utils.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

import java.time.Instant;
import java.util.HashMap;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class TokenManager {
	
	private static String access_token;
	private static Instant expiry_time;
	
	public synchronized static String getToken(){
        try{
            if(access_token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing token ...");
                Response response = renewToken();
                access_token = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else{
                System.out.println("Token is good to use");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("ABORT!!! Failed to get token");
        }
        return access_token;
    }
	
	public static Response renewToken() {
		
		HashMap<String, String> formparams = new HashMap<String, String>();
		formparams.put("grant_type", ConfigLoader.getInstance().getGrantType());
		formparams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
		formparams.put("client_id", ConfigLoader.getInstance().getClientId());
		formparams.put("client_secret", ConfigLoader.getInstance().getClientSecret());
		
		 Response response = given().log().all().baseUri(System.getProperty("ACCOUNT_BASE_URI")).contentType(ContentType.URLENC).formParams(formparams).
		when().post("/api/token").then().log().all().spec(getResponseSpec()).extract().response();
		
		
		if (response.getStatusCode()!=200) {
			throw new RuntimeException("Abort!! ---Access token renew failed");
		}
		
		return response;
		
	}

}
