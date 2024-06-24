package com.example.games_library_android.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.games_library_android.database.model.Game;
import com.example.games_library_android.database.model.Games;

import java.util.ArrayList;
import java.util.List;

public class GameRepository extends GameLibraryDatabase {

    public GameRepository(Context context) {
        super(context);
    }

    public void addGameToUser(int userId, Game game) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insertGameQuery = "INSERT OR IGNORE INTO games (id, name, background_image, playtime, description_raw, released, rating) VALUES (?, ?, ?, ?, ?, ?, ?)";
        db.execSQL(insertGameQuery, new Object[]{
                game.getId(),
                game.getName(),
                game.getBackground_image(),
                game.getPlaytime(),
                game.getDescription_raw(),
                game.getReleased(),
                game.getRating()
        });

        String insertUserGameQuery = "INSERT INTO user_games (user_id, game_id) VALUES (?, ?)";
        db.execSQL(insertUserGameQuery, new Object[]{userId, game.getId()});

        db.close();
    }

    public boolean isGameAlreadyAdded(int userId, int gameId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user_games WHERE user_id = ? AND game_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), String.valueOf(gameId)});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists;
    }

    public Games getAllGamesByUserId(int userId) {
        Games userGames = new Games();
        List<Game> gameList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT g.* FROM games g INNER JOIN user_games ug ON g.id = ug.game_id WHERE ug.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Game game = new Game();
                game.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                game.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                game.setBackground_image(cursor.getString(cursor.getColumnIndexOrThrow("background_image")));
                game.setPlaytime(cursor.getLong(cursor.getColumnIndexOrThrow("playtime")));
                game.setDescription_raw(cursor.getString(cursor.getColumnIndexOrThrow("description_raw")));
                game.setReleased(cursor.getString(cursor.getColumnIndexOrThrow("released")));
                game.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow("rating")));
                gameList.add(game);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        userGames.setResults(gameList);

        return userGames;
    }

}


