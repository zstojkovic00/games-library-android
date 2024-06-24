package com.example.games_library_android.activity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.games_library_android.R;
import com.example.games_library_android.adapter.GamesAdapter;
import com.example.games_library_android.database.GameRepository;
import com.example.games_library_android.database.model.Games;

public class CompletedGamesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCompletedGames;
    private GamesAdapter gamesAdapter;
    private ProgressBar loadingCompletedGames;
    private GameRepository gameRepository;
    private int userId;
    private ImageView arrowBackCompletedGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_games);

        gameRepository = new GameRepository(this);
        SharedPreferences prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        userId = prefs.getInt("userId", -1);

        initView();
        new Thread(() -> fetchGames(userId)).start();
    }

    private void initView() {
        recyclerViewCompletedGames = findViewById(R.id.recyclerViewCompletedGames);
        recyclerViewCompletedGames.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        loadingCompletedGames = findViewById(R.id.progressBarCompletedGames);

        arrowBackCompletedGames = findViewById(R.id.arrowBackCompletedGames);
        if (arrowBackCompletedGames == null) {
            Log.e("CompletedGamesActivity", "arrowBackCompletedGames is null");
        } else {
            Log.d("CompletedGamesActivity", "arrowBackCompletedGames found");
            arrowBackCompletedGames.setOnClickListener(v -> {
                Log.d("CompletedGamesActivity", "Back button clicked");
                finish();
            });
        }
    }

    private void fetchGames(int userId) {
        loadingCompletedGames.setVisibility(View.VISIBLE);
        Games games = gameRepository.getAllGamesByUserId(userId);
        runOnUiThread(() -> {
            loadingCompletedGames.setVisibility(View.GONE);
            if (games != null && !games.getResults().isEmpty()) {
                gamesAdapter = new GamesAdapter(games);
                recyclerViewCompletedGames.setAdapter(gamesAdapter);
            } else {
                Toast.makeText(this, "No completed games found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
