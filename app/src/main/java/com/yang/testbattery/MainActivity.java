package com.yang.testbattery;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BatteryChangeListener{

    /**
     *
     <action android:name="android.intent.action.ACTION_POWER_CONNECTED"/>
     <action android:name="android.intent.action.ACTION_POWER_DISCONNECTED"/>
     <action android:name="android.intent.action.ACTION_BATTERY_LOW"/>
     <action android:name="android.intent.action.ACTION_BATTERY_OKAY"/>
     <action android:name="android.intent.action.ACTION_BATTERY_CHANGED"/>
     */
    private PowerConnectionReceiver powerReceiver;
    private TextView tvCharging;
    private TextView tvLevel;
    private TextView tvVoltage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCharging = (TextView) findViewById(R.id.tv);
        tvLevel = (TextView) findViewById(R.id.textView);
        tvVoltage = (TextView) findViewById(R.id.textView2);
        powerReceiver = new PowerConnectionReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        registerReceiver(powerReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(powerReceiver);
    }

    @Override
    public void onPowerLevelChange(int level) {
        tvLevel.setText("Battery:"+level+"％");
    }

    @Override
    public void onBatteryCharging(BatteryChargeType type) {
        switch (type){
            case AC:
                tvCharging.setText("Charging:AC");
                break;
            case USB:
                tvCharging.setText("Charging:USB");
                break;
            case NONE:
                tvCharging.setText("Is not charging");
                break;
        }
    }

    @Override
    public void onVoltageChange(int voltage) {
        tvVoltage.setText("Voltage:"+voltage);
    }
}
