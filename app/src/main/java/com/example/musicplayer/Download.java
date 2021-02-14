package com.example.musicplayer;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Download extends Fragment implements View.OnClickListener {

    Button downloadBtn;
    DownloadManager downloadManager;
    public Download(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        downloadBtn = (Button) view.findViewById(R.id.downloadBtn);
        downloadBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v==downloadBtn){
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo datac = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((wifi != null || datac != null)
                    && (wifi.isConnected() || datac.isConnected())) {
                Toast toast = Toast.makeText(getActivity(), "Connected", Toast.LENGTH_LONG);
                toast.show();
                Log.i("INFO", "submit successfully!!!");
                Intent intent = new Intent(getActivity(),MainActivity2.class);
                intent.putExtra("Message", "Hurrahhh!!! You are connected to a network");
                getActivity().startActivity(intent);
            }else{
                //no connection
                Toast toast = Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    };
}