package com.brody.gestiondeconvertion.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import com.brody.gestiondeconvertion.exception.XecdApiException;

public interface XecdHttpClient {
	
	String getResponse(String url, String username, String password) throws URISyntaxException, ClientProtocolException, IOException, XecdApiException;

}
