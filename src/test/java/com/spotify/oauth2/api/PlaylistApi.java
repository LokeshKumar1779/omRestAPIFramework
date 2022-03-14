package com.spotify.oauth2.api;
import static com.spotify.oauth2.utils.Route.*;
import static com.spotify.oauth2.utils.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.utils.SpecBuilder.getResponseSpec;
import static com.spotify.oauth2.utils.SpecBuilder.getRequestSpecWithInvalidToken;
import static io.restassured.RestAssured.given;

import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class PlaylistApi {
	
	@Step
	public static Response post(Playlist requestPlaylist) {
		
		return given().spec(getRequestSpec())
		.body(requestPlaylist).
		when().post(USERS+ConfigLoader.getInstance().getUser()+PLAYLISTS).
		then().spec(getResponseSpec()).extract().response();
	}
	
	@Step
	public static Response postWithInvalidToken(Playlist requestPlaylist) {
		
		return given().spec(getRequestSpecWithInvalidToken())
		.body(requestPlaylist).
		when().post(USERS+ConfigLoader.getInstance().getUser()+PLAYLISTS).
		then().spec(getResponseSpec()).extract().response();
	}
	
	@Step
	public static Response get(String playlist_id) {
		
		return given().spec(getRequestSpec()).
		when().get(PLAYLISTS+playlist_id).
		then().spec(getResponseSpec()).extract().response();
		
	}
	
	@Step
	public static Response update(Playlist requestPlaylist, String playlist_id) {
		
		 return given().spec(getRequestSpec())
			.body(requestPlaylist).
			when().put(PLAYLISTS+playlist_id).
			then().extract().response();
		
	}
	
	

}
