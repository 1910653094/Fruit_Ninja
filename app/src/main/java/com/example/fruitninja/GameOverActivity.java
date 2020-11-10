package com.example.fruitninja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        if (intent.hasExtra("clicks")) {
            int clicks = intent.getIntExtra("clicks", 0);
            ((TextView) findViewById(R.id.clicks_text)).setText("Wow! You clicked on " + clicks + " circles.");
            ((Button) findViewById(R.id.restart_button)).setOnClickListener((View v) -> {
                Intent restart = new Intent(this, MainActivity.class);
                restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(restart);
            });
        }
    }
}