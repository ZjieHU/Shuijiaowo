package com.shuijiaowo.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AchieveURLText {
	
	public String getURLText(String url) {
		StringBuilder text = new StringBuilder();
		try {
			URL http = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) http.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String txt = new String();
			while((txt = read.readLine()) != null) {
				text.append(txt);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text.toString();
	}
	

}
