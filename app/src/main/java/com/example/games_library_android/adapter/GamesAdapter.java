package com.example.games_library_android.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.games_library_android.R;
import com.example.games_library_android.activity.GameDisplayActivity;
import com.example.games_library_android.database.model.Games;
import org.jetbrains.annotations.NotNull;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> {
    Games games;
    Context context;
    public GamesAdapter(Games games) {
        this.games = games;
    }
    @NonNull
    @NotNull
    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int i) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_game, parent, false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GamesAdapter.ViewHolder viewHolder, int i) {

        viewHolder.titleText.setText(games.getResults().get(i).getName());
        viewHolder.scoreText.setText(String.valueOf(games.getResults().get(i).getRating()));

        Glide.with(viewHolder.itemView.getContext())
                .load(games.getResults().get(i).getBackground_image())
                .into(viewHolder.gameImage);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(viewHolder.itemView.getContext(), GameDisplayActivity.class);
            int id = games.getResults().get(i).getId();
            intent.putExtra("id", id);
            Log.d("GamesAdapter", "GAME ID: " + id);
            viewHolder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return games.getResults().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleText, scoreText;
        ImageView gameImage;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.gameTitleText);
            scoreText = itemView.findViewById(R.id.gameScoreText);
            gameImage = itemView.findViewById(R.id.gameImage);
        }
    }
}
