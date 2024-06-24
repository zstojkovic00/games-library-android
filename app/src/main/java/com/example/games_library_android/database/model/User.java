package com.example.games_library_android.database.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    private int id;
    private String username;
    private String password;
}
