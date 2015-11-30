package com.yang.testbattery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {

    private static final String TAG = PowerConnectionReceiver.class.getSimpleName();
    private BatteryChangeListener batteryChangeListener;
    public PowerConnectionReceiver(BatteryChangeListener batteryChangeListener) {
        this.batteryChangeListener = batteryChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG,"action="+action);

        if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;
            int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,-1);

            if(batteryChangeListener != null){
                batteryChangeListener.onPowerLevelChange(level);
                batteryChangeListener.onVoltageChange(voltage);

                if(isCharging){
                    if(usbCharge)
                        batteryChangeListener.onBatteryCharging(BatteryChargeType.USB);
                    if(acCharge)
                        batteryChangeListener.onBatteryCharging(BatteryChargeType.AC);
                }else{
                    batteryChangeListener.onBatteryCharging(BatteryChargeType.NONE);
                }
            }

        }

       /* int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        Log.d(TAG,"status="+status);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        Log.d(TAG,"isCharging="+isCharging);
        int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        Log.d(TAG,"usbCharge="+usbCharge);
        Log.d(TAG,"acCharge="+acCharge);

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        Log.d(TAG,"level="+level);
        Log.d(TAG,"scale="+scale);
        float batteryPct = level / (float)scale;
        Log.d(TAG,"batteryPct="+batteryPct);*/
    }
}
