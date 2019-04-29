package com.example.ultrafalco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        findViewById(R.id.leaderboard_button).setOnClickListener(this::showLeaderboard);
        findViewById(R.id.settings_button).setOnClickListener(this::showSettings);
        findViewById(R.id.start_button).setOnClickListener(this::startGame);
    }
    void showLeaderboard(@SuppressWarnings("unused") View view) {
        startActivity(new Intent(this, LeaderboardActivity.class));
    }
    void showSettings(@SuppressWarnings("unused") View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }
    void startGame(@SuppressWarnings("unused") View view) {
        startActivity(new Intent(this, GameActivity.class));
        //finish();
    }
}
