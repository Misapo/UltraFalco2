package com.example.ultrafalco;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class LeaderboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_leaderboard);

        Set<String> scores = getPreferences(MODE_PRIVATE).getStringSet("Scores", new HashSet<>());
        LinearLayout layout = findViewById(R.id.leaderboard_layout);

        layout.addView(createLeaderboardPosition(getPreferences(MODE_PRIVATE).getString("hi", "nope")));

        if (scores != null)
            scores.stream().sorted().forEach(s -> layout.addView(createLeaderboardPosition(s)));
    }
    private TextView createLeaderboardPosition(String s) {
        TextView position = new TextView(this);
        position.setText(s);
        position.setTextSize(30);
        position.setPadding(0, 0, 0, 15);
        return position;
    }
}
