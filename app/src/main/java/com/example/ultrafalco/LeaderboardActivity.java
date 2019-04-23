package com.example.ultrafalco;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        LinearLayout layout = findViewById(R.id.leaderboard_layout);
        for (int i = 0; i < 10; i++) {
            String text = String.format(Locale.getDefault(), "%d. %s", i + 1, "placeholder");
            layout.addView(createLeaderboardPosition(text));
        }
    }
    private TextView createLeaderboardPosition(String s) {
        TextView position = new TextView(this);
        position.setText(s);
        position.setTextSize(30);
        position.setPadding(0, 0, 0, 15);
        return position;
    }
}
