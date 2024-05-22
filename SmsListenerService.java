package com.example.demosmslistener;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.util.Log;
import android.widget.Toast;

public class  SmsListenerService extends Service {
    private static final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1001;
    private SmsReceiver smsReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getApplicationContext(), "SMS Listerner Service", Toast.LENGTH_SHORT).show();

        smsReceiver = new SmsReceiver();
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, intentFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Your background task code goes here
        Log.d("MyBackgroundService", "Background task running");

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            Log.e("Service", "Service is running...");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();

        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(smsReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}