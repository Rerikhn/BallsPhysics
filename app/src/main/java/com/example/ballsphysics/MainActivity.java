package com.example.ballsphysics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.example.ballsphysics.BallSource.Ball;
import com.example.ballsphysics.BallSource.BallsGame;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BallsGame(this));

        /*//Start the balls to new activity
        final Button start = findViewById(R.id.button_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.bouncing_balls);
            }
        });*/

    }

}
