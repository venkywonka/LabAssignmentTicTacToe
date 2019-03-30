package com.example.vikram.tictactoe;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView messageTextView;

    boolean isHost=true; //TODO Remove initialization
    boolean isPlayable=true; //TODO Remove initialization

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

        messageTextView = findViewById(R.id.messageTextView);

//        int temp = getResources().getIdentifier("grid00","id",getPackageName());
    }

    public void onGridClicked(View view){
        if(!isPlayable){
            return;
        }

        if(view.getTag(R.string.dirtyTag) != null){
            Toast.makeText(this, "Box already filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isHost){
            view.setBackgroundColor(getResources().getColor(R.color.hostColor));
            view.setTag(R.string.dirtyTag,"host");
        }
        else{
            view.setBackgroundColor(getResources().getColor(R.color.clientColor));
            view.setTag(R.string.dirtyTag,"client");
        }

        if(isGameComplete()){
            isPlayable=false;
            messageTextView.setText("Game Over!");
        }
    }

    private boolean isGameComplete(){
            //Rows
            if (grid00.getTag(R.string.dirtyTag) != null &&
                    grid01.getTag(R.string.dirtyTag) != null &&
                    grid02.getTag(R.string.dirtyTag) != null &&
                    grid00.getTag(R.string.dirtyTag).equals(grid01.getTag(R.string.dirtyTag)) &&
                    grid01.getTag(R.string.dirtyTag).equals(grid02.getTag(R.string.dirtyTag))) {
                return true;
            } else if (grid10.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid12.getTag(R.string.dirtyTag) != null &&
                    grid10.getTag(R.string.dirtyTag).equals(grid11.getTag(R.string.dirtyTag)) &&
                    grid11.getTag(R.string.dirtyTag).equals(grid12.getTag(R.string.dirtyTag))) {
                return true;
            } else if (grid20.getTag(R.string.dirtyTag) != null &&
                    grid21.getTag(R.string.dirtyTag) != null &&
                    grid22.getTag(R.string.dirtyTag) != null &&
                    grid20.getTag(R.string.dirtyTag).equals(grid21.getTag(R.string.dirtyTag)) &&
                    grid21.getTag(R.string.dirtyTag).equals(grid22.getTag(R.string.dirtyTag))) {
                return true;
            }

            //Columns
            else if (grid00.getTag(R.string.dirtyTag) != null &&
                    grid10.getTag(R.string.dirtyTag) != null &&
                    grid20.getTag(R.string.dirtyTag) != null &&
                    grid00.getTag(R.string.dirtyTag).equals(grid10.getTag(R.string.dirtyTag)) &&
                    grid10.getTag(R.string.dirtyTag).equals(grid20.getTag(R.string.dirtyTag))) {
                return true;
            } else if (grid01.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid21.getTag(R.string.dirtyTag) != null &&
                    grid01.getTag(R.string.dirtyTag).equals(grid11.getTag(R.string.dirtyTag)) &&
                    grid11.getTag(R.string.dirtyTag).equals(grid21.getTag(R.string.dirtyTag))) {
                return true;
            } else if (grid02.getTag(R.string.dirtyTag) != null &&
                    grid12.getTag(R.string.dirtyTag) != null &&
                    grid22.getTag(R.string.dirtyTag) != null &&
                    grid02.getTag(R.string.dirtyTag).equals(grid12.getTag(R.string.dirtyTag)) &&
                    grid12.getTag(R.string.dirtyTag).equals(grid22.getTag(R.string.dirtyTag))) {
                return true;
            }

            //Diagonals
            else if (grid00.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid22.getTag(R.string.dirtyTag) != null &&
                    grid00.getTag(R.string.dirtyTag).equals(grid11.getTag(R.string.dirtyTag)) &&
                    grid11.getTag(R.string.dirtyTag).equals(grid22.getTag(R.string.dirtyTag))) {
                return true;
            } else if (grid02.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid20.getTag(R.string.dirtyTag) != null &&
                    grid02.getTag(R.string.dirtyTag).equals(grid11.getTag(R.string.dirtyTag)) &&
                    grid11.getTag(R.string.dirtyTag).equals(grid20.getTag(R.string.dirtyTag))) {
                return true;
            }

        return false;
    }

    public void colorBoxInGrid(View view){

    }
}
