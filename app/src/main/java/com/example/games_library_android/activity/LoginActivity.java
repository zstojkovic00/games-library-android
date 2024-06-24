package com.example.games_library_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.games_library_android.R;
import com.example.games_library_android.database.UserRepository;
import com.example.games_library_android.database.model.User;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button loginButton;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(this);

        initView();
    }

    private void initView() {
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();

            if (usernameText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(this, "Username and password cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                User user = userRepository.checkIfUserExists(usernameText, passwordText);
                if (user != null) {
                    SharedPreferences prefs = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("userId", user.getId());
                    editor.apply();
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));

                } else {
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
