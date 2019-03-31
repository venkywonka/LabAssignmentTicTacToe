package com.example.vikram.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void hostGame(View view){
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("isHost",true);
        startActivity(intent);
    }

    public void searchGame(View view){
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("isHost",false);
        startActivity(intent);
    }
}
