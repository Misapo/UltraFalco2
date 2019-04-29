package com.example.ultrafalco;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sPref;
    private String prefVol;
    private SeekBar volBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        sPref = getPreferences(MODE_PRIVATE);
        prefVol = getString(R.string.preferences_volume);
        volBar = findViewById(R.id.volume_bar);
        volBar.setProgress(sPref.getInt(prefVol, 50));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sPref.edit();
        editor.putInt(prefVol, volBar.getProgress());
        editor.apply();
    }
}
