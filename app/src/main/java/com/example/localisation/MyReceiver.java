package com.example.localisation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {

    private final Context context;
    private final WifiManager manager;
    private final EditText roomNo;
    List<ScanResult> wifiList,result;
    RoomDB database;

    public MyReceiver(Context context, WifiManager manager, EditText roomNo) {

        this.context = context;
        this.manager = manager;
        this.roomNo = roomNo;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Entered...",Toast.LENGTH_SHORT).show();

        database = RoomDB.getInstance(context);

        String action = intent.getAction();
        System.out.println(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action));
        manager.startScan();

        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {

            wifiList = manager.getScanResults();
            System.out.println(wifiList);
            result = new ArrayList<>(wifiList);
            System.out.println(result);
            AccessPoints ap = new AccessPoints();
            for (int i = 0; i < result.size(); i++) {
                String ssid = result.get(i).SSID;
                int rssi = WifiManager.calculateSignalLevel(result.get(i).level, 10);
                if(ssid.equals("bhavi")){
                    ap.setBhavi(rssi);
                }else if(ssid.equals("vivo 1907")){
                    ap.setVivo_1907(rssi);
                }else if(ssid.equals("Shivaay(Prerna)")){
                    ap.setShivaay_Prerna(rssi);
                }
            }
            String rNo = roomNo.getText().toString();
            ap.setRoomno(rNo);
            database.apDao().insert(ap);
        }
    }

}