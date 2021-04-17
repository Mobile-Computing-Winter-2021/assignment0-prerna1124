package com.example.localisation;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class ConnectionsFragment extends Fragment {

    WifiManager manager;
    MyReceiver receiver;
    ListAdapter adapter;
    ListView list;
    Context context;
    List wifiList,ls;
    Button scanBtn, stop,scan,findLocation;
    EditText roomNo;
    ArrayList<ScanResult> result;
    RoomDB database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_connections, container, false);
        context = getActivity();
        list = (ListView) view.findViewById(R.id.list);
        manager = MainActivity.wifiManager;
        scanBtn = view.findViewById(R.id.scanBtn);
        roomNo = view.findViewById(R.id.roomNo);
        database = RoomDB.getInstance(context);
        stop = view.findViewById(R.id.stopBtn);
        scan = view.findViewById(R.id.scan);

        findLocation = view.findViewById(R.id.findLocation);

        scanBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (manager.isWifiEnabled()) {

                    Toast.makeText(getActivity(), "Searching...", Toast.LENGTH_SHORT).show();

                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    } else {
                        wifiList = manager.getScanResults();
                        receiver = new MyReceiver(context,manager,roomNo);
                        context.registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                        System.out.println(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
                        scanWifiList();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please turn On Wifi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                context.unregisterReceiver(receiver);
            }

        });

        scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (manager.isWifiEnabled()) {

                    Toast.makeText(getActivity(), "Searching...", Toast.LENGTH_SHORT).show();

                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    } else {
                        ls = manager.getScanResults();
                        scanWifiList();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please turn On Wifi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findLocation.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(manager.isWifiEnabled()) {
                    Intent i = new Intent(context, FindRoom.class);
                    startActivity(i);
                }
                else{

                    Toast.makeText(context,"Please connect to wifi !!!",Toast.LENGTH_SHORT).show();

                }
            }
        });


        return view;
    }


    private void scanWifiList() {
        manager.startScan();
        ls = manager.getScanResults();
        adapter = new ListAdapter(getActivity(), ls);
        list.setAdapter(adapter);
    }
}

