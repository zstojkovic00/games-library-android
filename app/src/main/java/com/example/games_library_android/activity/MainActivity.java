package com.example.games_library_android.activity;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.games_library_android.R;
import com.example.games_library_android.adapter.GamesAdapter;
import com.example.games_library_android.database.model.Games;
import com.example.games_library_android.network.RawgApiService;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUpcomingGames, recyclerViewNewGames;
    private ProgressBar loadingUpcomingGames, loadingNewGames;
    private RawgApiService rawgApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rawgApiService = new RawgApiService();

        initView();
        new Thread(() -> fetchGames("upcoming-games", recyclerViewUpcomingGames, loadingUpcomingGames)).start();
        new Thread(() -> fetchGames("new-games", recyclerViewNewGames, loadingNewGames)).start();
    }

    private void initView() {
        recyclerViewNewGames = findViewById(R.id.newGames);
        recyclerViewNewGames.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        recyclerViewUpcomingGames = findViewById(R.id.upcomingGames);
        recyclerViewUpcomingGames.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        loadingUpcomingGames = findViewById(R.id.loadingUpcomingGames);
        loadingNewGames = findViewById(R.id.loadingNewGames);
    }

    private void fetchGames(String criteria, RecyclerView recyclerView, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        Games games = rawgApiService.getGames(criteria, "40");
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            if (games != null && games.getResults() != null && !games.getResults().isEmpty()) {
                RecyclerView.Adapter<GamesAdapter.ViewHolder> adapter = new GamesAdapter(games);
                recyclerView.setAdapter(adapter);
            } else {
                Log.d("RawgApiService", "No games found for criteria: " + criteria);
            }
        });
    }
}
