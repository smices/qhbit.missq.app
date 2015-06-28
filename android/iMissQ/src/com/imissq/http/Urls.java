package com.imissq.http;

public class Urls {

	private static final String BASE_URL = "http://imissq.com/apiv1/";
	
	public static final String getLoginUrl(){
		return BASE_URL+"login?type=default";
	}
	
	public static final String getWeatherUrl(){
		return BASE_URL+"weather";
	}
	
	public static final String getUpdateUrl(int version){
		return BASE_URL+"update?os=android&version="+version;
	}
	
	public static final String getProductUrl(String token){
		return BASE_URL+"product?token="+token;
	}
	
	public static final String getTopicUrl(String token){
		return BASE_URL+"topic?token="+token;
	}
	
	public static final String getTestUploadUrl(String token){
		return BASE_URL+"facerecord?token="+token;
	}
	
}
