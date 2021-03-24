package com.example.sensors;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.RoomDatabase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MAIN_ACTIVITY";
    FusedLocationProviderClient client;
    Switch gpsSwitch,accSwitch,linSwitch,lightSwitch,proSwitch,tempSwitch;
    Context context;
    Location location;
    LocationRequest locationRequest;
    Sensor accelerometer,gravity,linear,light,proximity,temperature;
    SensorManager sm;
    float x,y,z;
    float lx,ly,lz,ill,pro,temp;
    private float[] mGravity;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    long time;
    int date;
    Date d1;
    Button tempBtn,accBtn;
    TextView textView;
    List<Temperature> lst;
    List<Accelerator> lst1;
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        client = LocationServices.getFusedLocationProviderClient(this);

        database = RoomDB.getInstance(this);

        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        tempBtn = findViewById(R.id.tempButton);
        accBtn = findViewById(R.id.accButton);

        accBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lst1 = database.accDao().fetchData(System.currentTimeMillis()-3600000,System.currentTimeMillis());
//                System.out.println(lst.get(0).getId()+" "+lst.get(0).getTemp());
                System.out.println("========================================="+lst1.size());

                long sumx = 0,sumy=0,sumz=0;
                for (int i = 0;i<lst1.size();i++)
                {
                    sumx += lst1.get(i).getX();
                    sumy += lst1.get(i).getY();
                    sumy += lst1.get(i).getZ();
                }

                sumx = sumx/lst1.size();
                sumy = sumy/lst1.size();
                sumz = sumz/lst1.size();
                textView.setText("X Average : "+sumx+"\n"+"Y Average : "+sumy+"\n"+"Z Average : "+sumz+"\n");

            }
        });

        tempBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lst = database.tempDao().data(System.currentTimeMillis()-3600000,System.currentTimeMillis());
//                System.out.println(lst.get(0).getId()+" "+lst.get(0).getTemp());
                System.out.println("========================================="+lst.size());

                long sum = 0;
                for (int i = 0;i<lst.size();i++)
                {
                    sum += lst.get(i).getTemp();
                }

                sum = sum/lst.size();
                textView.setText(String.valueOf(sum));

            }
        });

        textView = findViewById(R.id.textView);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        //-----------------------------------------------------------------------------------------------
        //--------------------------------------Accelerometer--------------------------------------------
        //-----------------------------------------------------------------------------------------------

        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accSwitch = findViewById(R.id.accSwitch);

        accSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"ON.......",Toast.LENGTH_SHORT).show();
                    sm.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
                    Toast.makeText(context,"Registered.....",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"OFF.......",Toast.LENGTH_SHORT).show();
                    sm.unregisterListener(MainActivity.this,accelerometer);
                }
            }
        });

        //-----------------------------------------------------------------------------------------------
        //------------------------------Linear Acceleration---------------------------------------------
        //-----------------------------------------------------------------------------------------------

        gravity = sm.getDefaultSensor(Sensor.TYPE_GRAVITY);
        linear = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        linSwitch = findViewById(R.id.linSwitch);

        linSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"ON.......",Toast.LENGTH_SHORT).show();
                    sm.registerListener(MainActivity.this,gravity,SensorManager.SENSOR_DELAY_NORMAL);
                    sm.registerListener(MainActivity.this,linear,SensorManager.SENSOR_DELAY_NORMAL);
                    Toast.makeText(context,"Registered.....",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"OFF.......",Toast.LENGTH_SHORT).show();
                    sm.unregisterListener(MainActivity.this,linear);
                }
            }
        });

        //-----------------------------------------------------------------------------------------------
        //--------------------------------------------Light----------------------------------------------
        //-----------------------------------------------------------------------------------------------


        light = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSwitch = findViewById(R.id.lightSwitch);

        lightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"ON.......",Toast.LENGTH_SHORT).show();
                    sm.registerListener(MainActivity.this,light,SensorManager.SENSOR_DELAY_NORMAL);
                    Toast.makeText(context,"Registered.....",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"OFF.......",Toast.LENGTH_SHORT).show();
                    sm.unregisterListener(MainActivity.this,light);
                }
            }
        });


        //-----------------------------------------------------------------------------------------------
        //--------------------------------------------Proximity------------------------------------------
        //-----------------------------------------------------------------------------------------------


        proximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        proSwitch = findViewById(R.id.proxSwitch);

        proSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"ON.......",Toast.LENGTH_SHORT).show();
                    sm.registerListener(MainActivity.this,proximity,SensorManager.SENSOR_DELAY_NORMAL);
                    Toast.makeText(context,"Registered.....",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"OFF.......",Toast.LENGTH_SHORT).show();
                    sm.unregisterListener(MainActivity.this,proximity);
                }
            }
        });

        //-----------------------------------------------------------------------------------------------
        //------------------------------------------Temperature------------------------------------------
        //-----------------------------------------------------------------------------------------------


        temperature = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        tempSwitch = findViewById(R.id.tempSwitch);

        tempSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"ON.......",Toast.LENGTH_SHORT).show();
                    sm.registerListener(MainActivity.this,temperature,SensorManager.SENSOR_DELAY_NORMAL);
                    Toast.makeText(context,"Registered.....",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context,"OFF.......",Toast.LENGTH_SHORT).show();
                    sm.unregisterListener(MainActivity.this,temperature);
                }
            }
        });





        //-----------------------------------------------------------------------------------------------
        //--------------------------------------GPS------------------------------------------------------
        //-----------------------------------------------------------------------------------------------

        gpsSwitch = (Switch) findViewById(R.id.gpsSwitch);
        gpsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(context,"ON.......",Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(getApplicationContext(),
                                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                            //When permission is granted call method
                            getCurrentLocation();
                        } else {
                            //When permission not granted
                            //Request permission

                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                        }
                    }

                } else {
                    Toast.makeText(context,"OFF.......",Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check condition

        if(requestCode ==100 && grantResults.length > 0&&
                ( grantResults[0]+grantResults[1])==PackageManager.PERMISSION_GRANTED){

            //When permissions are granted
            //Call method
            getCurrentLocation();
        }
        else {
            Toast.makeText(context,"Permission Denied....",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.
                getSystemService(Context.LOCATION_SERVICE);


        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>(){

                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    location = task.getResult();
                    if(location != null){
                        String lat = String.valueOf(location.getLatitude());
                        String lon = String.valueOf(location.getLongitude());
                        GPS g = new GPS();
                        g.setLat(lat);
                        g.setLon(lon);
                        database.gpsDao().insert(g);
                        Toast.makeText(context,lat+"  "+lon,Toast.LENGTH_SHORT).show();
                    }
                    else{
                                locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        //Initialise Location callBack
                        LocationCallback locationCallback = new LocationCallback(){
                            @Override
                            public void onLocationResult(LocationResult result){
                                location = result.getLastLocation();
                                //get longitude and latitude
                                String lat = String.valueOf(location.getLatitude());
                                String lon = String.valueOf(location.getLongitude());
                                GPS g = new GPS();
                                g.setLat(lat);
                                g.setLon(lon);
                                database.gpsDao().insert(g);
                                Toast.makeText(context,lat+"  "+lon,Toast.LENGTH_SHORT).show();
                            }
                        };

                        //Request location updates
                        client.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper());
                    }

                }
            });
        }
        else{

            //When locationservice is not enables
            //Open settings
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] g = new float[3];
        float alpha = 0;

        try {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    x = event.values[0];
                    y = event.values[1];
                    z = event.values[2];

                    Accelerator l = new Accelerator();
                    l.setID(System.currentTimeMillis());
                    l.setX(x);
                    l.setY(y);
                    l.setZ(z);
                    database.accDao().insert(l);

                    d1 = new Date();
                    time = d1.getTime();
                    date = d1.getDate();

                    Log.i(TAG,time+" "+date);
                    Log.i(TAG,String.valueOf(x)+" "+String.valueOf(y)+" "+String.valueOf(z));
                    mGravity = event.values.clone();
                    // Shake detection
                    float x = mGravity[0];
                    float y = mGravity[1];
                    float z = mGravity[2];
                    mAccelLast = mAccelCurrent;
                    mAccelCurrent = (float)Math.sqrt(x*x + y*y + z*z);
                    float delta = mAccelCurrent - mAccelLast;
                    mAccel = mAccel * 0.9f + delta;
                    if(mAccel > 4){
                        Toast.makeText(MainActivity.this,"Phone is in motion",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Sensor.TYPE_GRAVITY:
                    g[0] = alpha*g[0] + (1-alpha)*event.values[0];
                    g[1] = alpha*g[1] + (1-alpha)*event.values[1];
                    g[2] = alpha*g[2] + (1-alpha)*event.values[2];
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    lx = event.values[0]-g[0];
                    ly = event.values[1]-g[1];
                    lz = event.values[2]-g[2];
                    Log.i(TAG,String.valueOf(lx)+" "+String.valueOf(ly)+" "+String.valueOf(lz));
                    LinearAcc la = new LinearAcc();
                    la.setX(lx);
                    la.setY(ly);
                    la.setZ(lz);
                    database.linAccDao().insert(la);
                    break;
                case Sensor.TYPE_LIGHT:
                    ill = event.values[0];
                    Log.i(TAG,String.valueOf(ill));
                    Light li = new Light();
                    li.setIll(ill);
                    database.lightDao().insert(li);
                    break;
                case Sensor.TYPE_PROXIMITY:
                    pro = event.values[0];
                    Log.i(TAG,String.valueOf(pro));
                    Proximity p = new Proximity();
                    p.setProx(pro);
                    database.proxDao().insert(p);
                    break;
                case Sensor.TYPE_AMBIENT_TEMPERATURE:
                    temp = event.values[0];
                    //Log.i(TAG,String.valueOf(temp));
                    Temperature t = new Temperature();
                    t.setId(System.currentTimeMillis());
                    t.setTemp(temp);
                    System.out.println(t.getTemp());
                    database.tempDao().insert(t);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}