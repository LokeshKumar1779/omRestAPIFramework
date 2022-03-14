package com.spotify.oauth2.tests;

import java.lang.reflect.Method;

import org.testng.annotations.BeforeMethod;

public class BaseTest {
	
	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println("Printing method name : " +m.getName());
		System.out.println("Printing Thread ID : " +Thread.currentThread().getId());
	}

}
