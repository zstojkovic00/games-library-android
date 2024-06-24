CREATE TABLE users (
                       id INTEGER PRIMARY KEY,
                       username TEXT,
                       password TEXT
);

CREATE TABLE games (
                       id INTEGER PRIMARY KEY,
                       name TEXT,
                       background_image TEXT,
                       playtime INTEGER,
                       description_raw TEXT,
                       released TEXT,
                       rating REAL
);

CREATE TABLE user_games (
                            user_id INTEGER,
                            game_id INTEGER,
                            PRIMARY KEY(user_id, game_id),
                            FOREIGN KEY(user_id) REFERENCES users(id),
                            FOREIGN KEY(game_id) REFERENCES games(id)
);
