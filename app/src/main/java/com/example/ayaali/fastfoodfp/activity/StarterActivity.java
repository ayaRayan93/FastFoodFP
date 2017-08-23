package com.example.ayaali.fastfoodfp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ayaali.fastfoodfp.R;

public class StarterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        Thread timer=new Thread() {
            public void run() {

                try
                {
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally {
                    Intent questions=new Intent("LISTITEM");
                    startActivity(questions);
                }
            }

        };
        timer.start();

    }
}
