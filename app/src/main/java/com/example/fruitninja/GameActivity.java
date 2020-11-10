package com.example.fruitninja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    int time, clicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ConstraintLayout layout = findViewById(R.id.constraint_layout);
        layout.addView(createButton());

        time = 30;
        clicks = 0;

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    ((TextView) findViewById(R.id.timer)).setText("" + --time + "s");
                    if (time == 0) {
                        Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("clicks", clicks);
                        startActivity(intent);
                    }
                });
            }
        };
        timer.schedule(task, 0L, 1000L);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            Button b = (Button) v;
            ConstraintLayout c = findViewById(R.id.constraint_layout);
            if (b == c.getChildAt(c.getChildCount()-1)) {
                b.clearAnimation();
                clicks++;
                int radius = getRandom(100, 500);
                b.setLayoutParams(setConstParams(radius));
                animateButton(b);
            }
        }
    }

    private View createButton() {
        Button b = new Button(this);
        @SuppressLint("UseCompatLoadingForDrawables")
        Drawable circle = getApplicationContext().getResources().getDrawable(R.drawable.circle);
        b.setBackground(circle);
        int radius = getRandom(100, 500);
        b.setLayoutParams(setConstParams(radius));
        b.setOnClickListener(this);
        animateButton(b);
        return b;
    }

    private void animateButton(Button b) {
        //set position TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta
        final Animation animation = new TranslateAnimation(0,getRandom(0, 300),0,getRandom(100, 600));
        // set Animation for 5 sec
        animation.setDuration(getRandom(2, 5) * 1000);
        //for button stops in the new position.
        animation.setFillAfter(true);
        b.startAnimation(animation);

        //not same as Translate -> Y and X go up sometimes
        //b.animate().translationX(getRandom(0, 300)).translationY(getRandom(100, 600)).setDuration(getRandom(2, 5) * 1000).start();
    }

    private ConstraintLayout.LayoutParams setConstParams(int radius) {
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(radius, radius);

        params.leftToLeft = 0;
        params.rightToRight = 0;
        params.topToTop = 0;
        params.bottomToBottom = 0;

        float vertical = new Random().nextFloat();
        while (vertical > 0.85f) {
            vertical = new Random().nextFloat();
        }
        params.verticalBias = vertical;
        params.horizontalBias = new Random().nextFloat();

        return params;
    }

    private int getRandom(int min, int max) {
        Random rand = new Random();
        return rand.nextInt( (max - min) + 1) + min;
    }
}

//circle should not leave constraintLayout