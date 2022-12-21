package com.example.projekt_zaliczeniowy.Notifcations;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Notifications {

    Context context;
    Activity activity;
    public static final int PERMISSION_REQUEST_SEND_SMS = 1;

    public Notifications(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void sendSmsWithSmsManager(String phoneNumber, String message) {

        Log.d("SMS", "phoneNumber: " + String.valueOf(phoneNumber));
        Log.d("SMS", "message: " + String.valueOf(message));

        if(checkPermission(Manifest.permission.SEND_SMS, PERMISSION_REQUEST_SEND_SMS)) {

            Log.d("SMS", "permisje git");

            if(!phoneNumber.equals("") && !message.equals("")) {
                SmsManager smsManager = SmsManager.getDefault();

                Log.d("SMS", "sending sms");
                smsManager.sendTextMessage(
                        phoneNumber,
                        null,
                        message,
                        null,
                        null
                );
            }
        } else {
            Log.d("SMS", "permission nie git");
        }
    }

    public boolean checkPermission(String permission, int requestCode) {
        if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[] { permission }, requestCode);
        }

        if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) return false;

        return true;
    }
}
