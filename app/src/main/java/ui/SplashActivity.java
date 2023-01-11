package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.mypfaproject.MainActivity;
import com.example.mypfaproject.R;

public class SplashActivity extends AppCompatActivity {

    //constant time Delay ( this means 3 seconds)
    private final int Splash_Delay=3000;
  //Fields (widgets)
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getWindow().setBackgroundDrawable(null);
        //Methods to call
        initializeView();
        animateLogo();
        goToMainActivity();



    }

    private void goToMainActivity() {
        //This method will take the user to main Activity when animation is finished

        Handler h = new Handler();
        h.postDelayed(new Runnable(){
            public void run() {
            startActivity(new Intent(SplashActivity. this, MainActivity. class));
                finish ();
            }
        }, Splash_Delay);
    }

    private void animateLogo() {
        //this method will animate Logo
        Animation fadingInAnimation = AnimationUtils.loadAnimation(this,R.anim.fade_in_without_duration);
        fadingInAnimation.setDuration(Splash_Delay);
        imageView.startAnimation (fadingInAnimation);

    }

    private void initializeView() {
        imageView = findViewById(R.id.imageView);
    }
}