package com.example.vikram.tictactoe;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

    boolean isHost;
    boolean isPlayable=true; //TODO Remove initialization

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        isHost=getIntent().getBooleanExtra("isHost",false);

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
            view.getBackground().setColorFilter(getResources().getColor(R.color.hostColor), PorterDuff.Mode.MULTIPLY);
            view.setTag(R.string.dirtyTag,"host");
        }
        else{
            view.getBackground().setColorFilter(getResources().getColor(R.color.clientColor), PorterDuff.Mode.MULTIPLY);
            view.setTag(R.string.dirtyTag,"client");
        }

        if(isGameComplete()){
            isPlayable=false;
            if(isHost){
                messageTextView.setText(R.string.gameOverHost);
            }
            else{
                messageTextView.setText(R.string.gameOverClient);
            }
        }
    }

    private boolean isGameComplete(){
            //Rows
            if (grid00.getTag(R.string.dirtyTag) != null &&
                    grid01.getTag(R.string.dirtyTag) != null &&
                    grid02.getTag(R.string.dirtyTag) != null &&
                    grid00.getTag(R.string.dirtyTag).equals(grid01.getTag(R.string.dirtyTag)) &&
                    grid01.getTag(R.string.dirtyTag).equals(grid02.getTag(R.string.dirtyTag))) {
                grid00.setText(R.string.winBlockMarker);
                grid01.setText(R.string.winBlockMarker);
                grid02.setText(R.string.winBlockMarker);
                return true;
            } else if (grid10.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid12.getTag(R.string.dirtyTag) != null &&
                    grid10.getTag(R.string.dirtyTag).equals(grid11.getTag(R.string.dirtyTag)) &&
                    grid11.getTag(R.string.dirtyTag).equals(grid12.getTag(R.string.dirtyTag))) {
                grid10.setText(R.string.winBlockMarker);
                grid11.setText(R.string.winBlockMarker);
                grid12.setText(R.string.winBlockMarker);
                return true;
            } else if (grid20.getTag(R.string.dirtyTag) != null &&
                    grid21.getTag(R.string.dirtyTag) != null &&
                    grid22.getTag(R.string.dirtyTag) != null &&
                    grid20.getTag(R.string.dirtyTag).equals(grid21.getTag(R.string.dirtyTag)) &&
                    grid21.getTag(R.string.dirtyTag).equals(grid22.getTag(R.string.dirtyTag))) {
                grid20.setText(R.string.winBlockMarker);
                grid21.setText(R.string.winBlockMarker);
                grid22.setText(R.string.winBlockMarker);
                return true;
            }

            //Columns
            else if (grid00.getTag(R.string.dirtyTag) != null &&
                    grid10.getTag(R.string.dirtyTag) != null &&
                    grid20.getTag(R.string.dirtyTag) != null &&
                    grid00.getTag(R.string.dirtyTag).equals(grid10.getTag(R.string.dirtyTag)) &&
                    grid10.getTag(R.string.dirtyTag).equals(grid20.getTag(R.string.dirtyTag))) {
                grid00.setText(R.string.winBlockMarker);
                grid10.setText(R.string.winBlockMarker);
                grid20.setText(R.string.winBlockMarker);
                return true;
            } else if (grid01.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid21.getTag(R.string.dirtyTag) != null &&
                    grid01.getTag(R.string.dirtyTag).equals(grid11.getTag(R.string.dirtyTag)) &&
                    grid11.getTag(R.string.dirtyTag).equals(grid21.getTag(R.string.dirtyTag))) {
                grid01.setText(R.string.winBlockMarker);
                grid11.setText(R.string.winBlockMarker);
                grid21.setText(R.string.winBlockMarker);
                return true;
            } else if (grid02.getTag(R.string.dirtyTag) != null &&
                    grid12.getTag(R.string.dirtyTag) != null &&
                    grid22.getTag(R.string.dirtyTag) != null &&
                    grid02.getTag(R.string.dirtyTag).equals(grid12.getTag(R.string.dirtyTag)) &&
                    grid12.getTag(R.string.dirtyTag).equals(grid22.getTag(R.string.dirtyTag))) {
                grid02.setText(R.string.winBlockMarker);
                grid12.setText(R.string.winBlockMarker);
                grid22.setText(R.string.winBlockMarker);
                return true;
            }

            //Diagonals
            else if (grid00.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid22.getTag(R.string.dirtyTag) != null &&
                    grid00.getTag(R.string.dirtyTag).equals(grid11.getTag(R.string.dirtyTag)) &&
                    grid11.getTag(R.string.dirtyTag).equals(grid22.getTag(R.string.dirtyTag))) {
                grid00.setText(R.string.winBlockMarker);
                grid11.setText(R.string.winBlockMarker);
                grid22.setText(R.string.winBlockMarker);
                return true;
            } else if (grid02.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid20.getTag(R.string.dirtyTag) != null &&
                    grid02.getTag(R.string.dirtyTag).equals(grid11.getTag(R.string.dirtyTag)) &&
                    grid11.getTag(R.string.dirtyTag).equals(grid20.getTag(R.string.dirtyTag))) {
                grid02.setText(R.string.winBlockMarker);
                grid11.setText(R.string.winBlockMarker);
                grid20.setText(R.string.winBlockMarker);
                return true;
            }

        return false;
    }

    public void resetGame(View view){
        if(isHost && isGameComplete()){
            //Reset Tags
            grid00.setTag(R.string.dirtyTag,null);
            grid01.setTag(R.string.dirtyTag,null);
            grid02.setTag(R.string.dirtyTag,null);
            grid10.setTag(R.string.dirtyTag,null);
            grid11.setTag(R.string.dirtyTag,null);
            grid12.setTag(R.string.dirtyTag,null);
            grid20.setTag(R.string.dirtyTag,null);
            grid21.setTag(R.string.dirtyTag,null);
            grid22.setTag(R.string.dirtyTag,null);

            //Clear Colors
            grid00.getBackground().clearColorFilter();
            grid01.getBackground().clearColorFilter();
            grid02.getBackground().clearColorFilter();
            grid10.getBackground().clearColorFilter();
            grid11.getBackground().clearColorFilter();
            grid12.getBackground().clearColorFilter();
            grid20.getBackground().clearColorFilter();
            grid21.getBackground().clearColorFilter();
            grid22.getBackground().clearColorFilter();

            //Clear Markers
            grid00.setText("");
            grid01.setText("");
            grid02.setText("");
            grid10.setText("");
            grid11.setText("");
            grid12.setText("");
            grid20.setText("");
            grid21.setText("");
            grid22.setText("");

            isPlayable=true;

            messageTextView.setText(R.string.yourTurn);
        }
    }

    public void colorBoxInGrid(View view){

    }
}
