package com.salazarisaiahnoel.studentregistrationapp;

import android.os.Bundle;
import android.os.Handler;
import android.transition.Slide;
import android.view.Gravity;

import androidx.appcompat.app.AppCompatActivity;

import com.github.saiaaaaaaa.mywebsite_androiddependency.Fullscreen;
import com.salazarisaiahnoel.studentregistrationapp.fragments.HomeFragment;
import com.salazarisaiahnoel.studentregistrationapp.fragments.LogoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogoFragment logoFragment = new LogoFragment();
        HomeFragment homeFragment = new HomeFragment();

        logoFragment.setExitTransition(new Slide(Gravity.START));
        homeFragment.setEnterTransition(new Slide(Gravity.END).setStartDelay(250));

        getSupportFragmentManager().beginTransaction().replace(R.id.main, logoFragment).commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().replace(R.id.main, homeFragment).commit();
            }
        }, 1500);
    }
}