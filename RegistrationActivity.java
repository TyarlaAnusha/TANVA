package com.example.demosmslistener;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class RegistrationActivity extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button loginButton = findViewById(R.id.loginButton);
        EditText receiverNo=findViewById (R.id.receiverNo);
        EditText senderNo=findViewById (R.id.senderNo);

//         goTomainactivity();
        // Permission check
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.SEND_SMS},
                    1);
        } else {
            // Permission has already been granted
//            sendSMS();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSMS(RegistrationActivity.this,receiverNo.getText().toString(),senderNo.getText().toString() );

                goTomainactivity();
//
//                Intent intent = new Intent( RegistrationActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
//                sendSMS();
            } else {
                // Permission denied
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void sendSMS(Context context,String receiverNo, String senderNo){
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(receiverNo,null,"*"+senderNo,null,null);
            Toast.makeText(context, "Registration under process.", Toast.LENGTH_SHORT).show();

            // go to Main Activity


            }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("erorr", e.toString());
            e.printStackTrace();

        }
    }
    public void goTomainactivity(){
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Open new activity here
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: close this activity if needed
            }
        }, 45000); // 60 seconds delay (1 minute)
    }
}