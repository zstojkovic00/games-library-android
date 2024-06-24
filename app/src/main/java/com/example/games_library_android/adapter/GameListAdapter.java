package com.example.games_library_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.games_library_android.database.GameList;
import com.example.games_library_android.R;
import org.jetbrains.annotations.NotNull;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    GameList items;
    Context context;

    public GameListAdapter(GameList items) {
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public GameListAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int i) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_game, parent, false);

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GameListAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return items.getGames().size();
    }

    public class ViewHolder {

    }
}
