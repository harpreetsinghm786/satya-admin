package com.official.satyaadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {

    Animation animation;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        title=findViewById(R.id.title);
        animation= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        title.startAnimation(animation);
        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    Intent i;
                    i = new Intent(splash.this, login.class);
                    startActivity(i);
                    finish();
                }else{
                    startActivity(new Intent(splash.this,MainActivity.class));
                    finish();
                }



            }
        },4000);

    }
}
