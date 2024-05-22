package com.example.demosmslistener;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {

    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();

                    // Here you can do whatever you want with the incoming SMS
                    // For example, you can display a Toast message
//                    Toast.makeText(context, "Incoming SMS from: " + sender + "\nMessage: " + messageBody, Toast.LENGTH_LONG).show();


                        Toast.makeText(context, messageBody, Toast.LENGTH_LONG).show();
                        Intent intentMain = new Intent(context, MainActivity.class);
                        intentMain.putExtra("Message", messageBody);
                        intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intentMain);

                }
            }
        }
    }

}