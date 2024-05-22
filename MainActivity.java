package com.example.demosmslistener;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_CONTACTS;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSION_REQUEST_CODE = 12;

    TextView textWatch;
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageButton = findViewById(R.id.imageButton);

        textWatch=findViewById(R.id.textWatch);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        TextView textSoil=findViewById(R.id.textSoil);

        Intent intent = getIntent();
         if(intent.getSerializableExtra("Message")!=null) {

             String messageBody = (String) intent.getSerializableExtra("Message");
//             Toast.makeText(MainActivity.this, messageBody, Toast.LENGTH_SHORT).show();
             textSoil.setText(messageBody);

         }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            READ_PHONE_STATE,
                            READ_SMS,
                            RECEIVE_SMS
                    },
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
        }else{

            // Start Background Service
            if(!foregroundServiceRunning()) {
                try{
                    Intent serviceIntent = new Intent(this, SmsListenerService.class);
                    startService(serviceIntent);
                }catch (Exception e){
                    Log.d("Error", e.toString());
                }

            }
        }
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Define a formatter if you want to format the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
        String formattedDateTime = currentDateTime.format(formatter);
        textWatch.setText(formattedDateTime);

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean phoneState = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean phoneNo = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean callLog = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                }

                break;
        }
    }
    // Get current date and time



    // Print or use the obtained date and time
//        System.out.println("Current Date and Time: " + formattedDateTime);

    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if(SmsListenerService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

//
//    TextView textSoil = findViewById(R.id.textSoil);
//        textSoil.setText(messageBody.message);
}