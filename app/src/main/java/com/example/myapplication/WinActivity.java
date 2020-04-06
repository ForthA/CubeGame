package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity implements View.OnClickListener {
    int shots = 0;
    Button button;
    TextView textViewShots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_win2);
        button = findViewById(R.id.button);
        textViewShots = findViewById(R.id.textViewShots);
        button.setOnClickListener(this);
        Intent i = getIntent();
        shots = i.getIntExtra("shots",0);
        textViewShots.setText("Кол-во произведённых выстрелов: " + shots);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
