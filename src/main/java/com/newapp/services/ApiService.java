package com.newapp.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@SuppressWarnings("deprecation")

public class ApiService {

    public static String findClient(String doc, String token) {
        return null;
    }

    public void fetchData(String client, String doc, String token) {

    }

    public static String getResponse(String doc, String client, String token) throws Exception {

        StringBuilder responseBuilder = new StringBuilder();
        try {

            URL url = new URL("http://127.0.0.1:5000/response/" + client + "/" + token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Authorization",
                    "Bearer " + token);

            BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));

            r.lines().forEach(line -> responseBuilder.append(line).append('\n'));

            r.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseBuilder.toString();
    }

    public static String getRequest(String doc, String client, String token) throws Exception {
        StringBuilder requestBuilder = new StringBuilder();

        try {

            URL url = new URL("http://127.0.0.1:5000/request/" + token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Authorization",
                    "Bearer " + token);

            BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));

            r.lines().forEach(line -> requestBuilder.append(line).append('\n'));

            r.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestBuilder.toString();
    }

    public static String getBundler(String doc, String client, String token) throws Exception {

        StringBuilder bundlerBuilder = new StringBuilder();
        try {

            URL url = new URL("http://127.0.0.1:5000/bundler/" + token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Authorization",
                    "Bearer " + token);

            BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));

            r.lines().forEach(line -> bundlerBuilder.append(line).append('\n'));

            r.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bundlerBuilder.toString();
    }

}
