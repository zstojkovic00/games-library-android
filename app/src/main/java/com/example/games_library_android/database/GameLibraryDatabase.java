package com.example.games_library_android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameLibraryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "games_library.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_USERS_TABLE = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY," +
            "username TEXT," +
            "password TEXT" +
            ");";

    private static final String CREATE_GAMES_TABLE = "CREATE TABLE games (" +
            "id INTEGER PRIMARY KEY," +
            "name TEXT," +
            "background_image TEXT," +
            "playtime INTEGER," +
            "description_raw TEXT," +
            "released TEXT," +
            "rating REAL" +
            ");";

    private static final String CREATE_USER_GAMES_TABLE = "CREATE TABLE user_games (" +
            "user_id INTEGER," +
            "game_id INTEGER," +
            "PRIMARY KEY(user_id, game_id)," +
            "FOREIGN KEY(user_id) REFERENCES users(id)," +
            "FOREIGN KEY(game_id) REFERENCES games(id)" +
            ");";

    private static final String CREATE_TEST_USER = "INSERT INTO users (id, username, password) " +
            "SELECT 1, 'test', 'test' " +
            "WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 1);";

    public GameLibraryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_GAMES_TABLE);
        db.execSQL(CREATE_USER_GAMES_TABLE);
        db.execSQL(CREATE_TEST_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user_games");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS games");
        onCreate(db);
    }
}
