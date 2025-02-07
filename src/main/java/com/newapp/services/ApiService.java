package com.newapp.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

public class ApiService {

    
    
    public static String findClient(String doc, String token) {
        return null;
    }
    
    public void fetchData(String client, String doc, String token) {
        
    }

    public static JSONArray getResponse(String doc, String client, String token) throws Exception {
        String apiResponse = "";

        URL url = new URL("http://127.0.0.1:5000/response/"+ client + "/" + token);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Authorization",
                "Bearer " + token);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;

        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {

            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        apiResponse = content.toString();

        JSONParser parser = new JSONParser();

        JSONArray array = (JSONArray) parser.parse(apiResponse);

        return array;
    }

    public static JSONObject getRequest(String doc, String client, String token) throws Exception {
        JSONObject obj = null;
        try {

            URL url = new URL("http://127.0.0.1:5000/request/" + token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Authorization",
                    "Bearer " + token);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;

            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {

                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            String apiResponse = content.toString();

            JSONParser parser = new JSONParser();

            obj = (JSONObject) parser.parse(apiResponse);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;

    }

    public static JSONObject getBundler(String doc, String client, String token) throws Exception {

        JSONObject obj = null;
        try {

            URL url = new URL("http://127.0.0.1:5000/bundler/" + token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Authorization",
                    "Bearer " + token);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;

            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {

                content.append(inputLine);
            }
            in.close();

            con.disconnect();

            String apiResponse = content.toString();

            JSONParser parser = new JSONParser();

            obj = (JSONObject) parser.parse(apiResponse);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;    }


}
