package com.example.mynangosia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class flash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                displayData();
            }
        }, 2000);
    }

    private void displayData() {
        Intent main = new Intent(flash.this , MainActivity.class);
        startActivity(main);
    }
}
