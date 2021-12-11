package com.example.gestiondesstages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class IntroActivity extends AppCompatActivity {


    ImageView appTitre;
    LottieAnimationView logoAnimation;
    LottieAnimationView horlogeAnimation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // Objects.requireNonNull(getSupportActionBar()).hide();

        appTitre = findViewById(R.id.imageView2);
        logoAnimation = findViewById(R.id.animation);
        horlogeAnimation = findViewById(R.id.horloge);


        AnimationIntro();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(IntroActivity.this, ActiviteConnexion.class);
                startActivity(i);
                finish();
            }
        }, 6000);
    }

    private void AnimationIntro() {
        appTitre.animate().translationY(-1600).setDuration(1200).setStartDelay(4000);
        logoAnimation.animate().translationY(1600).setDuration(1200).setStartDelay(4000);
        horlogeAnimation.animate().translationY(1600).setDuration(1200).setStartDelay(4000);
    }

}