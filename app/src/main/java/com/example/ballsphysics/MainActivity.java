package com.example.ballsphysics;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.ballsphysics.BallSource.BallsGame;

import jp.wasabeef.blurry.Blurry;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Blurry.with(MainActivity.this)
                .radius(25)
                .sampling(1)
                .color(Color.argb(66, 0, 255, 255))
                .async()
                .capture(findViewById(R.id.checkBox_revese))
                .into((ImageView) findViewById(R.id.bottom));*/
        final BallsGame game = new BallsGame(this);
        setContentView(R.layout.activity_main);
        //Start the balls to new activity
        final Button start = findViewById(R.id.button_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(game);
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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
