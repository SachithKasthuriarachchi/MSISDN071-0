package com.sachith.msisdn;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TelephonyManager manager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        String msisdn = null;
        if (manager != null) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) !=
                    PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                            Manifest.permission.READ_PHONE_NUMBERS) !=
                    PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                            Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            msisdn = manager.getLine1Number();
        }

        System.out.println(msisdn + "Chit");
        String TAG = "PhoneActivityTAG";
        //This works for multiple sims
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<SubscriptionInfo> subscription = SubscriptionManager.from(getApplicationContext()).getActiveSubscriptionInfoList();
            for (int i = 0; i < subscription.size(); i++) {
                SubscriptionInfo info = subscription.get(i);
                Log.d(TAG, "number " + info.getNumber());
                Log.d(TAG, "network name : " + info.getCarrierName());
                Log.d(TAG, "country iso " + info.getCountryIso());
            }
        }
    }
}
