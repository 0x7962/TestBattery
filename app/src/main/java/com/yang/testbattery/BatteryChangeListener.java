package com.yang.testbattery;

/**
 * Created by yangbin on 2015/11/30.
 * Email : ybjaychou@gmail.com
 */
public interface BatteryChangeListener {

    void onPowerLevelChange(int level);

    void onBatteryCharging(BatteryChargeType type);

    void onVoltageChange(int voltage);
}
