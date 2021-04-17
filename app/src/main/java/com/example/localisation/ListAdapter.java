package com.example.localisation;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    public List<ScanResult> wifiList;
    Context context;
    LayoutInflater inflater;
    WifiInfo info;

    public ListAdapter(){

    }

    public ListAdapter(Context context, List<ScanResult> wifiList) {
        this.context = context;
        this.wifiList = wifiList;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wifiList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;
        View view = convertView;


        if(view == null){
            view = inflater.inflate(R.layout.activity_listview,null);
            holder = new Holder();
            holder.txtWifi = view.findViewById(R.id.txtWifi);
            view.setTag(holder);
        }
        else{
            holder = (Holder)view.getTag();
        }

        holder.txtWifi.setText("Network  : "+wifiList.get(position).SSID+"\nRSSI Value : "+WifiManager.calculateSignalLevel(wifiList.get(position).level,10));

        return view;
    }

    class Holder{
        TextView txtWifi;
    }
}
