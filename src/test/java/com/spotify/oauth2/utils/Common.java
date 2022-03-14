package com.spotify.oauth2.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.pojo.Error;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class Common {
	
	@Step
	public static void  assertStatusCode(int actualRspCode  , StatusCode code){
		assertThat(actualRspCode, equalTo(code.getCode()));
	}
	
	@Step
	public static void assertError(Error rsp, StatusCode code) {
		assertThat(rsp.getError().getStatus(), equalTo(code.getCode()));
		assertThat(rsp.getError().getMessage(), equalTo(code.getMsg()));
	}
	

}
