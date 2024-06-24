package com.example.games_library_android.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository extends GameLibraryDatabase {
    public UserRepository(Context context) {
        super(context);
    }
    public boolean checkIfUserExists(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean exists = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                exists = true;
            }
            cursor.close();
        }
        db.close();
        return exists;
    }


}
