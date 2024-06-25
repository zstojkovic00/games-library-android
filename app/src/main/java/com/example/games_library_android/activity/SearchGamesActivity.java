package com.example.games_library_android.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.games_library_android.R;
import com.example.games_library_android.adapter.GamesAdapter;
import com.example.games_library_android.database.model.Game;
import com.example.games_library_android.database.model.Games;
import com.example.games_library_android.network.RawgApiService;

import java.util.List;
import java.util.stream.Collectors;

public class SearchGamesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSearchedGames;
    private GamesAdapter gamesAdapter;
    private ProgressBar loadingSearchedGames;
    private RawgApiService rawgApiService;
    private ImageView arrowBackSearchedGames;
    private TextView textViewTotalPlaytime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_display);
        rawgApiService = new RawgApiService();

        initView();
        new Thread(this::fetchSearchedGames).start();
    }

    private void initView() {
        recyclerViewSearchedGames = findViewById(R.id.recyclerViewGames);
        recyclerViewSearchedGames.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        loadingSearchedGames = findViewById(R.id.progressBarGames);
        arrowBackSearchedGames = findViewById(R.id.arrowBackGames);
        textViewTotalPlaytime = findViewById(R.id.textViewTotalPlaytime);
        textViewTotalPlaytime.setVisibility(View.GONE);

        if (arrowBackSearchedGames == null) {
            Log.e("CompletedGamesActivity", "arrowBackCompletedGames is null");
        } else {
            Log.d("CompletedGamesActivity", "arrowBackCompletedGames found");
            arrowBackSearchedGames.setOnClickListener(v -> {
                Log.d("CompletedGamesActivity", "Back button clicked");
                finish();
            });
        }
    }

    private void fetchSearchedGames() {
        loadingSearchedGames.setVisibility(View.VISIBLE);
        String searchQuery = getIntent().getStringExtra("searchQuery");
        if (searchQuery != null && !searchQuery.isEmpty()) {
            Games games = rawgApiService.searchGames(searchQuery);

            List<Game> filteredGames = games.getResults().stream()
                    .filter(game -> game.getRating() != 0 && game.getBackground_image() != null)
                    .collect(Collectors.toList());
            games.setResults(filteredGames);

            runOnUiThread(() -> {
                loadingSearchedGames.setVisibility(View.GONE);
                if (games != null && !games.getResults().isEmpty()) {
                    gamesAdapter = new GamesAdapter(games);
                    recyclerViewSearchedGames.setAdapter(gamesAdapter);
                } else {
                    Toast.makeText(this, "No completed games found", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
