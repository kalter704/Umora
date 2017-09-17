package ru.comp.vas.umora;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UmoriApiClass {
    public static List<Content> fetch(Source source) {
        return parseJson(request(source));
    }

    private static String request(Source source) {
        BufferedReader reader = null;
        String urlString = Uri.parse("http://umorili.herokuapp.com/api/get")
                .buildUpon()
                .appendQueryParameter("site", source.getSite())
                .appendQueryParameter("name", source.getName())
                .appendQueryParameter("num", "30")
                .build()
                .toString();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder buf=new StringBuilder();
            String line;
            while ((line=reader.readLine()) != null) {
                buf.append(line).append("\n");
            }
            return(buf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Content> parseJson(String json) {
        List<Content> list = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject object = jsonArray.getJSONObject(i);
                list.add(new Content(
                        object.getString("site"),
                        object.getString("name"),
                        object.getString("desc"),
                        object.getString("link"),
                        object.getString("elementPureHtml")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
