package com.example.musicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class SongFragment extends Fragment  implements View.OnClickListener {

    String TAG;
    Button tumHiHo,titliyaan,tempPyaar,kaleJeLibas,stopBtn;
    MusicService ms;
    MediaPlayer mediaPlayer;
    int[] ids = new int[5];
    boolean isRunning = false;
    public SongFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        ids[0] = R.raw.tumhiho;
        ids[1] = R.raw.kalejelibaasdikaka;
        ids[2] = R.raw.temporarypyarkaka;
        ids[3] = R.raw.titliaanafsanakhan;
        ids[4] = R.raw.chhordenge;

        tumHiHo = (Button) view.findViewById(R.id.tumHiHo);
        titliyaan = (Button) view.findViewById(R.id.titliyan);
        tempPyaar = (Button) view.findViewById(R.id.tempPyaar);
        kaleJeLibas = (Button) view.findViewById(R.id.kaaleJeLibas);
        stopBtn = (Button) view.findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(this);
        tumHiHo.setOnClickListener(this);
        titliyaan.setOnClickListener(this);
        tempPyaar.setOnClickListener(this);
        kaleJeLibas.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        if(v==tumHiHo){
            Log.i(TAG,"Tum hi ho");
            if(!isRunning) {
                isRunning = true;
                Intent intent = new Intent(getActivity(), MusicService.class);
                intent.putExtra("songName","tumHiHo");
                getActivity().startService(intent);
            }
            else{
                Toast.makeText(getActivity(),""+"Song is Already Playing!!!",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == titliyaan){
            Log.i(TAG,"Titliyaan Playing >>>");
            if(!isRunning) {
                isRunning = true;
                Intent intent = new Intent(getActivity(), MusicService.class);
                intent.putExtra("songName","titliyaan");
                getActivity().startService(intent);
            }
            else{
                Toast.makeText(getActivity(),""+"Song is Already Playing!!!",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v== kaleJeLibas){
            Log.i(TAG,"Kale Je Libas KAKA Playing >>>");
            if(!isRunning) {
                isRunning = true;
                Intent intent = new Intent(getActivity(), MusicService.class);
                intent.putExtra("songName","kaleJeLibas");
                getActivity().startService(intent);
            }
            else{
                Toast.makeText(getActivity(),""+"Song is Already Playing!!!",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v==tempPyaar){
            Log.i(TAG,"Temporary Pyaar Playing >>>");
            if(!isRunning) {
                isRunning = true;
                Intent intent = new Intent(getActivity(), MusicService.class);
                intent.putExtra("songName","tempPyaar");
                getActivity().startService(intent);
            }
            else{
                Toast.makeText(getActivity(),""+"Song is Already Playing!!!",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v==stopBtn){
            Log.i(TAG,"Stop Button Clicked.");
            if(isRunning) {
                getActivity().stopService(new Intent(getActivity(), MusicService.class));
                ms = new MusicService();
                isRunning = false;
            }
            else {
                getActivity().stopService(new Intent(getActivity(), MusicService.class));
                Toast.makeText(getActivity(), "" + "Song has already stopped!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}