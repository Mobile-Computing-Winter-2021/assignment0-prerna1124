package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener{

    Button button,stopDownBtn,playAgain;
    DownloadManager downloadManager;
    MediaPlayer mp;
    File file;
    final String url = "http://faculty.iiitd.ac.in/~mukulika/s1.mp3";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("Message");
        Log.i("INFO", "fetched info from Main Activity Successfully");
        TextView textField = (TextView) findViewById(R.id.textView);
        textField.setText(msg);
        button = (Button) findViewById(R.id.button);
        stopDownBtn = (Button) findViewById(R.id.stopDownBtn);
        playAgain = (Button) findViewById(R.id.playDownBtn);
        button.setOnClickListener(this);
        stopDownBtn.setOnClickListener(this);
        playAgain.setOnClickListener(this);
    }


    private class DownloadSong extends AsyncTask<String,Integer,String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity2.this);
            progressDialog.setTitle("Progress");
            progressDialog.setMessage("Downloading, Please wait!!!");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            try{

                URL url = new URL(urls[0]);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                int fileLength = urlConnection.getContentLength();

                FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("s1.mp3", Context.MODE_PRIVATE);
                InputStream input = new BufferedInputStream(url.openStream());
                byte data[] = new byte[fileLength];
                int total = 0;
                int count;
                while ((count = input.read(data)) != -1)
                {
                    total += count;
                    publishProgress((int)(total*100/fileLength));
                    fileOutputStream.write(data,0,count);
                }
                //after completing the background jobs close all the streams
                input.close();
                fileOutputStream.close();


            }catch(final Exception e)
            {
                Log.e("ERROR MESSAGE",e.getMessage());
            }

            return "Download Complete .....";
        }

       @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            System.out.println("Done downloading");
            file = new File(getApplicationContext().getFilesDir() + "/" + "s1.mp3");
            System.out.println(file);
            mp = new MediaPlayer();
            try{
                mp.setDataSource(file.getPath());
                mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    public void onPrepared(MediaPlayer player) {
                        mp.start();
                        stopDownBtn.setVisibility(View.VISIBLE);
                        playAgain.setVisibility(View.VISIBLE);
                    }
                });

                mp.prepareAsync();
            }catch (Exception e)
            {

            }
        }
    }
    @Override
    public void onClick(View v) {
        if(v == button){

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mob = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((wifi != null && wifi.isConnected())||(mob != null && mob.isConnected())) {
                DownloadSong downloadSong = new DownloadSong();
                downloadSong.execute(url);
            }
            else{
                Toast.makeText(this,"Please check your Connection",Toast.LENGTH_SHORT).show();
            }
        }
        if(v==stopDownBtn){
            if(!mp.isPlaying()) {
                Toast.makeText(this,"Downloaded Song already stopped!!!",Toast.LENGTH_SHORT).show();
            }
            else{
                mp.stop();
                mp = MediaPlayer.create(this,R.raw.song);
            }
        }
        if(v==playAgain){
            if(mp.isPlaying()) {
                Toast.makeText(this,"Downloaded Song already playing!!!",Toast.LENGTH_SHORT).show();
            }
            else {
                mp = MediaPlayer.create(this,R.raw.song);
                mp.start();
            }
        }
    }
}