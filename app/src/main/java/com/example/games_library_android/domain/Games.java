package com.example.games_library_android.domain;

import lombok.*;

import java.io.Serializable;
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