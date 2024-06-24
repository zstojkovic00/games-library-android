package com.example.games_library_android.database.model;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Games {
    private int count;
    private String next;
    private String previous;
    private List<Game> results;
}