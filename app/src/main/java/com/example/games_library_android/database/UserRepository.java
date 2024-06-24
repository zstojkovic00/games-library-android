package com.example.games_library_android.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.games_library_android.database.model.User;

public class UserRepository extends GameLibraryDatabase {
    public UserRepository(Context context) {
        super(context);
    }

    public User checkIfUserExists(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        User user = new User();

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                user.setId(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
            }
            cursor.close();
        }
        db.close();
        return user;
    }


}
