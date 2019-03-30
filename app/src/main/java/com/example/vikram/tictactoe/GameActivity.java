package com.example.vikram.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {

    Button grid00;
    Button grid01;
    Button grid02;

    Button grid10;
    Button grid11;
    Button grid12;

    Button grid20;
    Button grid21;
    Button grid22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        grid00 = findViewById(R.id.grid00);
        grid01 = findViewById(R.id.grid01);
        grid02 = findViewById(R.id.grid02);

        grid10 = findViewById(R.id.grid10);
        grid11 = findViewById(R.id.grid11);
        grid12 = findViewById(R.id.grid12);

        grid20 = findViewById(R.id.grid20);
        grid21 = findViewById(R.id.grid21);
        grid22 = findViewById(R.id.grid22);

    }
}
