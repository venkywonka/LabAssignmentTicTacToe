package com.example.vikram.tictactoe;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

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
    boolean isPlayable=false;

    String peerEndpointId;
    ConnectionLifecycleCallback connectionManagementCallback;
    PayloadCallback payloadCallback;

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
        messageTextView.setText(R.string.searching);

        //Nearby Connections Initialization
        payloadCallback = new PayloadCallback() {
            @Override
            public void onPayloadReceived(@NonNull String s, @NonNull Payload payload) {

                String message = new String(payload.asBytes());
                if(message.equals(getString(R.string.resetGame))){
                    reinitializeGrid();
                    setMyTurn(false);
                }
                else{
                    setMyTurn(true);
                    colorBoxInGrid(message); //message contains gridIdInString
                }
            }

            @Override
            public void onPayloadTransferUpdate(@NonNull String s, @NonNull PayloadTransferUpdate payloadTransferUpdate) {
            }
        };

        connectionManagementCallback = new ConnectionLifecycleCallback() {
            @Override
            public void onConnectionInitiated(@NonNull String endpointId, @NonNull ConnectionInfo connectionInfo) {
                Nearby.getConnectionsClient(getApplicationContext()).acceptConnection(endpointId,payloadCallback);
                Log.d("nearbyConnection","Connection initiated...");
            }

            @Override
            public void onConnectionResult(@NonNull String endPointId, @NonNull ConnectionResolution result) {
                Log.d("nearbyConnection","Connection initiated...");
                int statusCode = result.getStatus().getStatusCode();
                if(statusCode==ConnectionsStatusCodes.STATUS_OK){
                    peerEndpointId = endPointId;
                    if(isHost){
                        setMyTurn(true);
                        Nearby.getConnectionsClient(getApplicationContext()).stopAdvertising();
                    }
                    else{
                        setMyTurn(false);
                        Nearby.getConnectionsClient(getApplicationContext()).stopDiscovery();
                    }
                }
            }

            @Override
            public void onDisconnected(@NonNull String s) {
                isPlayable=false;
                messageTextView.setText(R.string.errorDisconnect);
                Log.d("nearbyConnection","Lost Connection");
            }
        };

        if(isHost){
            startAdvertisement();
        }
        else{
            startDiscovery();
        }
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

        String buttonIdInString = Integer.toString(view.getId());
        Payload selectedGridButton = Payload.fromBytes(buttonIdInString.getBytes());
        Nearby.getConnectionsClient(getApplicationContext()).sendPayload(peerEndpointId,selectedGridButton);
        setMyTurn(false);

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

            //All boxes filled
            else if(grid00.getTag(R.string.dirtyTag) != null &&
                    grid01.getTag(R.string.dirtyTag) != null &&
                    grid02.getTag(R.string.dirtyTag) != null &&
                    grid10.getTag(R.string.dirtyTag) != null &&
                    grid11.getTag(R.string.dirtyTag) != null &&
                    grid12.getTag(R.string.dirtyTag) != null &&
                    grid20.getTag(R.string.dirtyTag) != null &&
                    grid21.getTag(R.string.dirtyTag) != null &&
                    grid22.getTag(R.string.dirtyTag) != null ){
                return true;
            }
        return false;
    }

    private void reinitializeGrid(){
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
    }

    public void resetGame(View view){
        if(isHost && isGameComplete()){
            reinitializeGrid();

            Payload resetPayload = Payload.fromBytes(getString(R.string.resetGame).getBytes());
            Nearby.getConnectionsClient(getApplicationContext()).sendPayload(peerEndpointId,resetPayload);

            setMyTurn(true);
        }
    }

    public void colorBoxInGrid(String idInString){
        int buttonId = getResources().getIdentifier(idInString,"id",getPackageName());
        Button targetButton = findViewById(buttonId);

        if(isHost){
            targetButton.getBackground().setColorFilter(getResources().getColor(R.color.clientColor), PorterDuff.Mode.MULTIPLY);
            targetButton.setTag(R.string.dirtyTag,"client");
        }
        else{
            targetButton.getBackground().setColorFilter(getResources().getColor(R.color.hostColor), PorterDuff.Mode.MULTIPLY);
            targetButton.setTag(R.string.dirtyTag,"host");
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

    private void startAdvertisement(){
        Nearby.getConnectionsClient(getApplicationContext()).startAdvertising(
                getString(R.string.serverEndpointName),
                getApplicationContext().getPackageName(),
                connectionManagementCallback,
                new AdvertisingOptions(Strategy.P2P_STAR)
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("nearbyConnection","Advertisment Started!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Internal Error - Advertisement Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startDiscovery(){
        EndpointDiscoveryCallback discoveryCallback = new EndpointDiscoveryCallback() {
            @Override
            public void onEndpointFound(@NonNull String endpointId, @NonNull DiscoveredEndpointInfo discoveredEndpointInfo) {
                Log.d("nearbyConnection","Endpoint found...");

                Nearby.getConnectionsClient(getApplicationContext()).requestConnection(
                        getString(R.string.clientEndpointName),
                        endpointId,
                        connectionManagementCallback).addOnFailureListener(new OnFailureListener() {

                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("nearbyConnection","Request Connection Failed!");
                                Toast.makeText(getApplicationContext(),"Internal Error - Request Connection Failed!",Toast.LENGTH_SHORT).show();
                            }
                });
            }

            @Override
            public void onEndpointLost(@NonNull String endpointId) {
                Log.d("nearbyConnection","Endpoint lost...");
                Toast.makeText(getApplicationContext(),"Endpoint lost!",Toast.LENGTH_SHORT).show();
            }
        };

        Nearby.getConnectionsClient(getApplicationContext()).startDiscovery(
                getApplicationContext().getPackageName(),
                discoveryCallback,
                new DiscoveryOptions(Strategy.P2P_STAR)
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("nearbyConnection","Discovery Started!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Internal Error - Discovery Failed",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setMyTurn(boolean isMyTurn){
        if(isMyTurn){
            isPlayable=true;
            messageTextView.setText(R.string.yourTurn);
        }
        else{
            isPlayable=false;
            messageTextView.setText(R.string.opponentTurn);
        }
    }

    @Override
    protected void onPause() {
        Log.d("nearbyConnection","onPause Called!");
        super.onPause();
        Nearby.getConnectionsClient(getApplicationContext()).stopAllEndpoints();
        if(isHost){
            Nearby.getConnectionsClient(getApplicationContext()).stopAdvertising();
        }
        else{
            Nearby.getConnectionsClient(getApplicationContext()).stopDiscovery();
        }
    }
}
