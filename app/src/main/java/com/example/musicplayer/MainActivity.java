package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    String TAG;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.registerReceiver(this.receiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        this.registerReceiver(this.receiver,new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        this.registerReceiver(this.receiver,new IntentFilter(Intent.ACTION_POWER_DISCONNECTED));
        this.registerReceiver(this.receiver,new IntentFilter(Intent.ACTION_BATTERY_OKAY));
        this.registerReceiver(this.receiver,new IntentFilter(Intent.ACTION_BATTERY_LOW));
        initViewPager();

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                Log.i(TAG, "onReceive: Power connected");
                Toast.makeText(MainActivity.this, "" + "Charger is connected", Toast.LENGTH_SHORT).show();
            }
            if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)) {
                Toast.makeText(MainActivity.this, "" + "Charger has disconnected", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onReceive: Power disconnected");
            }
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                if (level <= 15) {
                    Toast.makeText(MainActivity.this, "" + "Please Charge Battery is less than 15%", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onReceive: battery less than 15%");
                }
                if (level > 15) {
                    Toast.makeText(MainActivity.this, "" + "Battery is fine!!!", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onReceive: battery greater than 15%");
                }
            }
            if(intent.getAction().equals(Intent.ACTION_BATTERY_LOW)){
                Toast.makeText(MainActivity.this, "" + "Battery Low", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onReceive: battery low");
            }
            if(intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)){
                Toast.makeText(MainActivity.this, "" + "Battery is Okay", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onReceive: battery is Okay");
            }
        }
    };


    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragments(new SongFragment(),"listOfSongs");
        viewPagerAdapter.addFragments(new Download(),"Download");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    public static class ViewPagerAdapter extends FragmentPagerAdapter {

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