package com.example.localisation;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarGraphFragment extends Fragment {

    BarChart barChart;
    BarDataSet bardataset;
    ArrayList<String> labels;
    List<String> name;
    ArrayList<BarEntry> entries;
    BarData data;
    List<ScanResult> wifiList;
    WifiManager manager;
    Button display;
    ArrayList<String> temp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bar_graph, container, false);

        display = view.findViewById(R.id.display);

        manager = MainActivity.wifiManager;

        display.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(manager.isWifiEnabled()){
                    wifiList = manager.getScanResults();
                }
                else{
                    Toast.makeText(getActivity(),"Please turn On Wifi",Toast.LENGTH_SHORT).show();
                }
                barChart = view.findViewById(R.id.barchart);

                entries = new ArrayList<>();


                for(int i=0;i<wifiList.size();i++){
                    float x = Float.parseFloat(String.valueOf(WifiManager.calculateSignalLevel(wifiList.get(i).level,10)));
                    entries.add(new BarEntry(x,i));
                }


                bardataset = new BarDataSet(entries, "Cells");
                temp = new ArrayList<>();

                for(int i=0;i<wifiList.size();i++){
                    temp.add(wifiList.get(i).SSID);
                }

                labels = new ArrayList<String>(temp);

                data = new BarData(labels, bardataset);
                barChart.setData(data); // set the data and list of lables into chart

                barChart.setDescription("Wifi Signal Strength");  // set the description

                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                barChart.animateY(5000);
            }
        });

        return view;
    }



}
