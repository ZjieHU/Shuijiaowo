package com.shuijiaowo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class PostUtil {

	public static String sendPost(String url, String params) {

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";

		try {

			URL realURL = new URL(url);
			URLConnection conn = realURL.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0(compatible;MSIE 6.0 ;Windows NT 5.1;SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new PrintWriter(conn.getOutputStream());
			out.print(params);
			out.flush();

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;

			}

		} catch (Exception e) {
			System.out.println("����POST��������쳣�� " + e);
			e.printStackTrace();
		}

		finally {
			try {

				if (out != null) {
					out.close();

				}
				if (in != null) {
					in.close();

				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		return result;

	}
}
