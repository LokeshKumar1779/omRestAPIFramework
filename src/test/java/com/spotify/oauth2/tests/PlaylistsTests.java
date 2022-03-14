package com.spotify.oauth2.tests;

import static com.spotify.oauth2.api.PlaylistApi.get;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.api.PlaylistApi.post;
import static com.spotify.oauth2.api.PlaylistApi.postWithInvalidToken;
import static com.spotify.oauth2.api.PlaylistApi.update;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static com.spotify.oauth2.pojo.Playlist.PlaylistBuilder;
import org.testng.annotations.Test;
import static com.spotify.oauth2.utils.Common.*;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
@Epic("Spotify Oauth 2.0")
@Features(@Feature("User Playlist"))
@Feature("User Playlist")
public class PlaylistsTests {
	

	static Response response;
	
	public static String playlist_id;
	
	@Step
	public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist) {
		
		assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
		assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
		assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
	}
	
	
	@Step
	public Playlist newPlaylist(String name, String desc, boolean Public) {
	
		
		/*
		 * Playlist requestPlaylist = new Playlist(); requestPlaylist.setName(name);
		 * requestPlaylist.setDescription(desc); requestPlaylist.set_public(Public);
		 * 
		 * return requestPlaylist;
		 */
		 
		//Builder Pattern
		return Playlist.builder().name(name).description(desc)._public(Public).build();
		//	setName(name)
		//.setDescription(desc)
		//.set_public(Public);
		
	}
	@Story("create Playlist")
	@TmsLink("test-1")
	@Issue("123")
	@Link("https://example.org")
	@Link(name = "allure", type = "mylink")
	@Description("This is test description from allure")
	
	@Test(enabled=true,description="user is able to create playlist")
	public void createPlaylist() {
		
	
		Playlist requestPlaylist = newPlaylist(generateName(),generateDescription(), false);
				
		
		response = post(requestPlaylist);
		
		assertStatusCode(response.getStatusCode(),StatusCode.CODE_201);	
		
		playlist_id = response.as(Playlist.class).getId();
		assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);
		
		
	}
	
	@Story("get Playlist")
	@Test(enabled=true,description="user is able to fetch the playlist")
	public void getPlaylist() {
	
	
		response = get(playlist_id);
		assertStatusCode(response.getStatusCode(),StatusCode.CODE_200);
		Playlist responsePlaylist = response.as(Playlist.class);
		assertThat(responsePlaylist.getId(),equalTo(playlist_id));

	
	}
	
	@Story("update Playlist")
	@Test(enabled=true)
	public void updatePlaylist() {
	
		
		Playlist requestPlaylist = newPlaylist("Updated Playlist", "Updated Playlist Desc", false);	
		response = update(requestPlaylist, playlist_id);
		assertStatusCode(response.getStatusCode(),StatusCode.CODE_200);
		
		
	}
	
	@Story("create Playlist")
	@Test(enabled=true)
	public void createPlaylistWithInvalidAccessToken() {
		
		
		Playlist requestPlaylist = newPlaylist(generateName(),generateDescription(), false);
		response = postWithInvalidToken(requestPlaylist);
		assertStatusCode(response.getStatusCode(),StatusCode.CODE_401);
		assertError(response.as(Error.class), StatusCode.CODE_401);


		
	}
	
	@Story("create Playlist")
	@Test(enabled=true)
	public void createPlaylistWithoutName() {
	
		Playlist requestPlaylist = newPlaylist("",generateDescription(), false);
		response = post(requestPlaylist);
		assertStatusCode(response.getStatusCode(),StatusCode.CODE_400);
		assertError(response.as(Error.class), StatusCode.CODE_400);
	
	}
	

}
