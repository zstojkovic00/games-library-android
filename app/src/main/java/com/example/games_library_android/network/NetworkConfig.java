package com.example.games_library_android.network;

import android.annotation.SuppressLint;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class NetworkConfig {

    protected OkHttpClient client;
    protected ObjectMapper mapper;

    public NetworkConfig() {

        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();

        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        this.mapper.setDateFormat(dateFormat);
    }

}
