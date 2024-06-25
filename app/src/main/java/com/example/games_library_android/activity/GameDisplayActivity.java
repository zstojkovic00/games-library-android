package com.example.games_library_android.activity;

import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.example.games_library_android.R;
import com.example.games_library_android.database.GameRepository;
import com.example.games_library_android.database.model.Game;
import com.example.games_library_android.network.RawgApiService;
import com.google.android.material.imageview.ShapeableImageView;

public class GameDisplayActivity extends AppCompatActivity {
    private TextView gameTitle, gameDescription, gamePlaytime, gameReleasedDate;
    private ShapeableImageView gameImage;
    private ImageView gameBackgroundImage, arrowBack, completedGamesTrophy;
    private int gameId, userId;
    private RawgApiService rawgApiService;
    private GameRepository gameRepository;
    private SharedPreferences prefs;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_display);
        rawgApiService = new RawgApiService();
        gameRepository = new GameRepository(this);

        gameId = getIntent().getIntExtra("id", 0);
        prefs = getSharedPreferences("preferences", MODE_PRIVATE);
        userId = prefs.getInt("userId", -1);

        initView();
        new Thread(() -> fetchGameById(gameId)).start();
    }

    private void initView() {
        gameTitle = findViewById(R.id.gameTitle);
        gameDescription = findViewById(R.id.gameDescription);
        gamePlaytime = findViewById(R.id.gamePlaytime);
        gameReleasedDate = findViewById(R.id.gameReleasedDate);
        gameImage = findViewById(R.id.gameDisplayImage);
        gameBackgroundImage = findViewById(R.id.gameDisplayBigImage);

        arrowBack = findViewById(R.id.arrowBackCompletedGames);
        arrowBack.setOnClickListener(v -> finish());

        completedGamesTrophy = findViewById(R.id.completedGamesTrophy);
        completedGamesTrophy.setOnClickListener(v -> {
            if (game != null) {
                if (gameRepository.isGameAlreadyAdded(userId, gameId)) {
                    gameRepository.removeGameFromUser(userId, gameId);
                    completedGamesTrophy.setImageResource(R.drawable.trophy_empty);
                    Toast.makeText(this, "Game removed from completed games", Toast.LENGTH_SHORT).show();
                } else {
                    gameRepository.addGameToUser(userId, game);
                    completedGamesTrophy.setImageResource(R.drawable.trophy_full);
                    Toast.makeText(this, "Game added to completed games", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchGameById(int gameId) {
        // progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            game = rawgApiService.getGameById(gameId);
            runOnUiThread(() -> {
                // progressBar.setVisibility(View.GONE);
                if (game != null) {
                    gameTitle.setText(game.getName());
                    gameDescription.setText(game.getDescription_raw());
                    gamePlaytime.setText(String.valueOf(game.getPlaytime()));
                    gameReleasedDate.setText(game.getReleased());

                    Glide.with(this)
                            .load(game.getBackground_image())
                            .into(gameBackgroundImage);

                    Glide.with(this)
                            .load(game.getBackground_image())
                            .into(gameImage);

                    if (gameRepository.isGameAlreadyAdded(userId, gameId)) {
                        completedGamesTrophy.setImageResource(R.drawable.trophy_full);
                    }
                }
            });
        }).start();
    }
}
