package com.example.games_library_android.activity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.games_library_android.R;
import com.example.games_library_android.adapter.GamesAdapter;
import com.example.games_library_android.database.GameRepository;
import com.example.games_library_android.database.model.Game;
import com.example.games_library_android.database.model.Games;

public class CompletedGamesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCompletedGames;
    private GamesAdapter gamesAdapter;
    private ProgressBar loadingCompletedGames;
    private GameRepository gameRepository;
    private int userId;
    private ImageView arrowBackCompletedGames;
    private TextView textViewTotalPlaytime;
    private long totalPlaytime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_display);

        gameRepository = new GameRepository(this);
        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        userId = prefs.getInt("userId", -1);

        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        fetchGames(userId);
    }

    private void initView() {
        recyclerViewCompletedGames = findViewById(R.id.recyclerViewGames);
        recyclerViewCompletedGames.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        loadingCompletedGames = findViewById(R.id.progressBarGames);
        textViewTotalPlaytime = findViewById(R.id.textViewTotalPlaytime);

        arrowBackCompletedGames = findViewById(R.id.arrowBackGames);
        arrowBackCompletedGames.setOnClickListener(v -> finish());
    }

    private void fetchGames(int userId) {
        loadingCompletedGames.setVisibility(View.VISIBLE);
        Games games = gameRepository.getAllGamesByUserId(userId);
        runOnUiThread(() -> {
            loadingCompletedGames.setVisibility(View.GONE);
            if (games != null && !games.getResults().isEmpty()) {
                gamesAdapter = new GamesAdapter(games);
                recyclerViewCompletedGames.setAdapter(gamesAdapter);

                totalPlaytime = games.getResults().stream()
                        .mapToLong(Game::getPlaytime).sum();

                textViewTotalPlaytime.setText("Total Playtime: " + totalPlaytime + " hours");
                textViewTotalPlaytime.setVisibility(View.VISIBLE);

            } else {
                gamesAdapter = null;
                recyclerViewCompletedGames.setAdapter(null);
                textViewTotalPlaytime.setVisibility(View.GONE);
                Toast.makeText(this, "No completed games found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
