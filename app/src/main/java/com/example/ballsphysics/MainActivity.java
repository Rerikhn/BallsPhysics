package com.example.ballsphysics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.ballsphysics.BallSource.Ball;
import com.example.ballsphysics.BallSource.BallsGame;

public class MainActivity extends AppCompatActivity {
    private boolean truthCollide = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final BallsGame game = new BallsGame(this);
        setContentView(R.layout.activity_main);

        //Start the balls to new activity
        final Button start = findViewById(R.id.button_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(game);
            }
        });

        CheckBox collide = findViewById(R.id.checkBox_collide);
        collide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                game.setCollide(b);
                System.out.println("COLLIDE " + game.getCollide());
            }
        });

        CheckBox eatSmall = findViewById(R.id.checkBox_agarIO);
        eatSmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                game.setEatSmall(b);
            }
        });

        CheckBox reverse = findViewById(R.id.checkBox_revese);
        reverse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                game.setReverse(b);
                System.out.println("REVERSE: "+game.getReverse());
            }
        });

    }

}
