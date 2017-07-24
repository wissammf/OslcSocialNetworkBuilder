package com.wissam.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.wissam.xmlutils.FileNames;

public class OslcDataFetcher {
	public static void loadTestCase(String username, String password, String tcUrl) {
		try {
			URL url = new URL(tcUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			String encoding = getBasicAuthenticationEncoding(username, password);
			conn.setRequestProperty("Authorization", "Basic " + encoding);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			File file = new File(FileNames.TEMP_TEST_CASE_FILE_NAME);
			FileOutputStream fop = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}

			IOUtils.copy(conn.getInputStream(), fop);

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadChangeRequest(String username, String password, String uri) {
		try {
			URL url = new URL(uri);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			String encoding = getBasicAuthenticationEncoding(username, password);
			conn.setRequestProperty("Authorization", "Basic " + encoding);

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			File file = new File(FileNames.TEMP_CHANGE_REQUEST_FILE_NAME);
			FileOutputStream fop = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}

			IOUtils.copy(conn.getInputStream(), fop);

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getBasicAuthenticationEncoding(String username, String password) {
		String userPassword = username + ":" + password;
		return new String(Base64.encodeBase64(userPassword.getBytes()));
	}
}
