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

    public static BufferedReader getResponse(String doc, String client, String token) throws Exception {

        BufferedReader in = null;
        try {

            URL url = new URL("http://127.0.0.1:5000/response/" + client + "/" + token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Authorization",
                    "Bearer " + token);

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            return in;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return in;
    }

    public static BufferedReader getRequest(String doc, String client, String token) throws Exception {
        BufferedReader in = null;
        try {

            URL url = new URL("http://127.0.0.1:5000/request/" + token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Authorization",
                    "Bearer " + token);

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            return in;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return in;

    }

    public static BufferedReader getBundler(String doc, String client, String token) throws Exception {

        BufferedReader in = null;
        try {

            URL url = new URL("http://127.0.0.1:5000/bundler/" + token);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("Authorization",
                    "Bearer " + token);

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            return in;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return in;
    }

}
