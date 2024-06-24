package com.example.games_library_android.activity;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.example.games_library_android.R;
import com.example.games_library_android.database.model.Game;
import com.example.games_library_android.network.RawgApiService;
import com.google.android.material.imageview.ShapeableImageView;

public class GameDisplayActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView gameTitle, gameDescription, gamePlaytime, gameReleasedDate, gameRating;
    private ShapeableImageView gameImage;
    private ImageView gameBackgroundImage, arrowBack;
    private int gameId;
    private RawgApiService rawgApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_display);
        rawgApiService = new RawgApiService();

        gameId = getIntent().getIntExtra("id", 0);
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



        arrowBack = findViewById(R.id.arrowBack);
        arrowBack.setOnClickListener(v -> finish());
    }

    private void fetchGameById(int gameId) {
//        progressBar.setVisibility(RecyclerView.VISIBLE);
        Game game = rawgApiService.getGameById(gameId);
        runOnUiThread(() -> {
//            progressBar.setVisibility(View.GONE);
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
            }
        });

    }
}