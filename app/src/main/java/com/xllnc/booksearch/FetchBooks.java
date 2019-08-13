package com.xllnc.booksearch;

import android.net.Uri;
import android.util.Log;

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

public class FetchBooks {

    private static final String TAG = "FetchBooks";


    private static final Uri URI = Uri.parse("https://www.googleapis.com/books/v1/volumes")
            .buildUpon()
            .appendQueryParameter("q", "harry potter")
            .build();


    public List<Book> buildJsonString() {
        List<Book> items = new ArrayList<>();
        try {
            String jsonString = getJsonString();
            JSONObject jsonObject = new JSONObject(jsonString);
            Log.i(TAG, "Received JSON String: " + jsonString);
            parseJsonItems(items, jsonObject);

        } catch (IOException e) {
            Log.e(TAG, "Failed to get Json String");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.i(TAG, "Failed to create JSONObject");
            e.printStackTrace();
        }
        return items;
    }

    private String buildUrl(){
        Uri.Builder builder = URI.buildUpon();
        return builder.build().toString();
    }

    private String getJsonString() throws IOException {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try{
            String urlString = buildUrl();
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ":with " + url);
            }
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                buf.append(line);
            }
            return buf.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader != null) {
                reader.close();
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        return null;
    }

    private void parseJsonItems(List<Book> items, JSONObject jsonObject) throws IOException, JSONException{
        try {
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            for(int i = 0; i < itemsArray.length(); i++){
                JSONObject item = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                String smallThumbnailUrl = volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");

                Book book = new Book();
                book.setTitle(title);
                book.setSmallThumbnailUrl(smallThumbnailUrl);
                for(int j = 0; j < authors.length(); j++){
                   book.addAuthor(authors.getString(j));
                }
                items.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
