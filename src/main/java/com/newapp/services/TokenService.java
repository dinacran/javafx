package com.newapp.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("deprecation")
public class TokenService {

    public static String getToken() throws Exception {
        URL url = new URL("http://127.0.0.1:5000/token");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;

        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {

            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        JSONParser parser = new JSONParser();

        JSONObject obj = (JSONObject) parser.parse(content.toString());

        return obj.get("access_token").toString();
    }

    public static String promptForToken() {
        return null;
    }
}
