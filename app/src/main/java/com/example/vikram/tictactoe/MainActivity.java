package com.example.vikram.tictactoe;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private PermissionManager permissionManagerObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();
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

    private void checkPermissions(){
        permissionManagerObj = new PermissionManager(this);

        if(!permissionManagerObj.checkPermission(Manifest.permission.ACCESS_WIFI_STATE) ||
                !permissionManagerObj.checkPermission(Manifest.permission.CHANGE_WIFI_STATE) ||
                !permissionManagerObj.checkPermission(Manifest.permission.INTERNET) ||
                !permissionManagerObj.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)||
                !permissionManagerObj.checkPermission(Manifest.permission.BLUETOOTH_ADMIN)){

            permissionManagerObj.askPermission(permissionManagerObj.getPermissionList());
        }
    }
}
