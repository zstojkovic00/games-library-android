package com.example.games_library_android.network;

import android.util.Log;
import com.example.games_library_android.database.model.Game;
import com.example.games_library_android.database.model.Games;
import com.example.games_library_android.utils.Environment;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Request;
import okhttp3.Response;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RawgApiService extends NetworkConfig {

    private static final String BASE_URL = Environment.GAMES_API_URL;
    private static final String API_KEY = Environment.GAMES_API_KEY;
    private static final String TAG = "RawgApiService";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Games getGames(String criteria, String pageSize) {
        String query = createQuery(criteria, pageSize);
        String url = BASE_URL + "?key=" + API_KEY + query;
        return fetchData(url, new TypeReference<Games>() {
        });
    }

    public Game getGameById(int id) {
        String url = BASE_URL + "/" + id + "?key=" + API_KEY;
        return fetchData(url, new TypeReference<Game>() {
        });
    }

    public Games searchGames(String searchQuery) {
        String url = BASE_URL + "?search=" + searchQuery + "&search_precise&key=" + API_KEY;
        return fetchData(url, new TypeReference<Games>() {
        });
    }


    private <T> T fetchData(String url, TypeReference<T> typeReference) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.d(TAG, "Request URL: " + url);

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            if (response.body() != null) {
                return mapper.readValue(response.body().string(), typeReference);
            } else {
                Log.e(TAG, "Response body is null");
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error fetching data", e);
            return null;
        }
    }


    private static String createQuery(String criteria, String pageSize) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime nextYear = LocalDateTime.now().plusYears(1);
        LocalDateTime lastYear = LocalDateTime.now().minusYears(1);

        String currentDate = currentTime.format(FORMATTER);
        String lastYearDate = lastYear.format(FORMATTER);
        String nextYearDate = nextYear.format(FORMATTER);

        String query = "";

        switch (criteria) {
            case "popular-games" ->
                    query = "&dates=" + lastYearDate + "," + currentDate + "&ordering=-rating&page_size=" + pageSize;
            case "upcoming-games" ->
                    query = "&dates=" + currentDate + "," + nextYearDate + "&ordering=-added&page_size=" + pageSize;
            case "new-games" ->
                    query = "&dates=" + lastYearDate + "," + currentDate + "&ordering=-released&page_size=" + pageSize;
            case "best-games" -> query = "&page_size=" + pageSize;
        }
        return query;
    }


}
