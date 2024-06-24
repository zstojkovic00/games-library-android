package com.example.games_library_android.activity;

import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.games_library_android.database.Game;
import com.example.games_library_android.R;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterNewGames, adapterUpcomingGames;
    private RecyclerView recyclerViewNewGames, recyclerViewUpcomingGames;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest, mStringRequest2;
    private ProgressBar loadingNewGames, loadingUpcomingGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        sendRequest();
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        loadingNewGames.setVisibility(View.VISIBLE);
        mStringRequest = new StringRequest(Request.Method.GET, "url-new-games", response -> {
            Gson gson = new Gson();
            loadingNewGames.setVisibility(View.GONE);


            Game game = gson.fromJson(response, Game.class);
        }, error -> {

        });
    }

    private void initView() {
        recyclerViewNewGames.findViewById(R.id.newGames);
        recyclerViewNewGames.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        recyclerViewUpcomingGames.findViewById(R.id.upcomingGames);
        recyclerViewUpcomingGames.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        loadingNewGames.findViewById(R.id.loadingNewGames);
        loadingNewGames.findViewById(R.id.loadingUpcomingGames);
    }
}