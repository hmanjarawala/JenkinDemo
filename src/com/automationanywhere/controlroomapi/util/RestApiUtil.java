package com.automationanywhere.controlroomapi.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestApiUtil {
    private RestApiUtil(){}

    private static class SingletonHelper{
        public static final RestApiUtil INSTANCE = new RestApiUtil();
    }

    public static RestApiUtil getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public String getAccessToken(String controlRoomUrl, String username, String password)
            throws IOException, MalformedURLException {
        String token = "INVALID TOKEN";
        StringBuilder builder = new StringBuilder("{");
        builder.append("\r\n\"username\": \"" + username + "\",");
        builder.append("\r\n\"password\": \"" + password + "\"");
        builder.append("\r\n}");
        final String requestBody = builder.toString();
        URL url = new URL( controlRoomUrl + "/v1/authentication");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setReadTimeout(3000);
        conn.setRequestProperty("Content-Type", "application/json");

        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(requestBody.getBytes());
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject object = new JSONObject(response.toString());
            token = (String) object.get("token");
        }
        return token;
    }
}
