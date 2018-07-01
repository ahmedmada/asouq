package com.example.hp.aswaq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity
{
    ImageView splash;
    private final int SPLASH_DISPLAY_LENGTH = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splash=findViewById(R.id.splashTv);
        new android.os.Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                //  splash.setTextSize(60)splash.setTextSize(70);;


                android.content.Intent mainIntent = new android.content.Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
