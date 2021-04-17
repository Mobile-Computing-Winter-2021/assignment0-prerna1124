package com.example.localisation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.hardware.SensorManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static WifiManager wifiManager;
    Context context;
    Switch switchWifi;
    TextView text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViewPager();

        context = this;
        wifiManager = (WifiManager) getApplicationContext().getSystemService(context.WIFI_SERVICE);
        text = findViewById(R.id.text);

        text.setVisibility(View.VISIBLE);

        switchWifi = findViewById(R.id.switchWifi);

        if(wifiManager.isWifiEnabled()){
            text.setText("Turn Off Wifi");
            switchWifi.setChecked(true);
        }
        else{
            text.setText("Turn On Wifi");
            switchWifi.setChecked(false);
        }

        switchWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"Wifi On",Toast.LENGTH_SHORT).show();
                    wifiManager.setWifiEnabled(true);
                    text.setText("Turn Off Wifi");

                }
                else{
                    Toast.makeText(context,"Wifi Off",Toast.LENGTH_SHORT).show();
                    wifiManager.setWifiEnabled(false);
                    text.setText("Turn On Wifi");
                }
            }
        });



    }
    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new ConnectionsFragment(),"Connections");
        viewPagerAdapter.addFragments(new BarGraphFragment(),"Signal Strength");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> listOfFragments;
        private ArrayList<String> titlesOfSongs;

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return listOfFragments.get(position);
        }

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.listOfFragments = new ArrayList<Fragment>();
            this.titlesOfSongs= new ArrayList<String>();
        }

        @Override
        public int getCount() {
            return listOfFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titlesOfSongs.get(position);
        }

        public void addFragments(Fragment fragment, String title){
            listOfFragments.add(fragment);
            titlesOfSongs.add(title);
        }
    }
}