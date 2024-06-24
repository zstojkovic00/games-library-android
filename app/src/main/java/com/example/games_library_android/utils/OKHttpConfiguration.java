package com.example.games_library_android.utils;

import okhttp3.OkHttpClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class OKHttpConfiguration {

    protected OkHttpClient client;
    protected ObjectMapper mapper;

    public Handler() {

        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();

        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        this.mapper.setDateFormat(dateFormat);

    }

}
