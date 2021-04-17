package com.example.localisation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.localisation.MainActivity.wifiManager;

public class FindRoom extends AppCompatActivity {

    RoomDB database;
    Context context;
    List<AccessPoints> data;
    WifiManager manager;
    List<ScanResult> ls;
    int bhavi,vivo,shivay;
    TextView minR,knnResult;
    ArrayList<Float> eDis;
    ArrayList<String> room;
    ArrayList<Room> listRoom;
    Button knn;
    EditText k;
    int kInt;
    HashMap<String, Integer> hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_room);

        manager = wifiManager;
        ls = manager.getScanResults();
        context = this;
        database = RoomDB.getInstance(context);
        minR = findViewById(R.id.minR);
        listRoom = new ArrayList<>();
        knn = findViewById(R.id.knn);
        k = findViewById(R.id.k);
        knnResult = findViewById(R.id.knnResult);
        hm = new HashMap<>();



        for(int i=0;i<ls.size();i++){
            String ssid = ls.get(i).SSID;
            int rssi = WifiManager.calculateSignalLevel(ls.get(i).level, 10);
            if(ssid.equals("bhavi")){
                bhavi = rssi;
            }else if(ssid.equals("vivo 1907")){
                vivo = rssi;
            }else if(ssid.equals("Shivaay(Prerna)")){
                shivay = rssi;
            }
        }

        data = database.apDao().getData();
        eDis = new ArrayList<Float>();
        room = new ArrayList<String>();

        predictRoom();

        knn.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                String kVal = k.getText().toString();

                if(kVal != null) {

                    kInt = Integer.parseInt(kVal);

                    Collections.sort(listRoom, new ObjectComparator());
                    String[] arr = new String[kInt];

                    for (int i = 0; i < kInt; i++) {
                        arr[i] = listRoom.get(i).getrN();
                    }

                    String result = "";

                    for (int i = 0; i < kInt; i++) {
                        result += arr[i] + "\n";
                    }

                    for (int i = 0; i < kInt; i++)
                        hm.put(arr[i],hm.getOrDefault(arr[i], 0) + 1);

                    int max = Integer.MIN_VALUE;
                    String res = "";

                    for(Map.Entry<String, Integer> entry : hm.entrySet()){

                        if(entry.getValue()>max){
                            max = entry.getValue();
                            res = entry.getKey();
                        }
                    }

                    result += "Predicted Room is : "+ res;

                    knnResult.setText(result);

                }

            }

        });


    }

    private void predictRoom() {


        for(AccessPoints ap : data){

            int b = ap.getBhavi();
            int v = ap.getVivo_1907();
            int s = ap.getShivaay_Prerna();
            String rno = ap.getRoomno();

            float eucDist = (float) Math.sqrt(Math.pow((b-bhavi),2)+Math.pow((v-vivo),2)+Math.pow((s-shivay),2));

            Room tempRoom = new Room(eucDist,rno);
            listRoom.add(tempRoom);

            eDis.add(eucDist);
            room.add(rno);
        }

        System.out.println(eDis.size()+" "+room.size());

        float minVal = eDis.get(0);
        String minRoom = room.get(0);

        for(int i=1;i<eDis.size();i++){

            if(minVal>eDis.get(i)){

                minVal = eDis.get(i);
                minRoom = room.get(i);
            }
        }

        minR.setText(minRoom);
    }

    class Room{

        float dist;
        String rN;

        Room(float dist,String rN){
            this.dist = dist;
            this.rN = rN;
        }

        public float getDist() {
            return dist;
        }

        public String getrN() {
            return rN;
        }
    }

    class ObjectComparator implements Comparator<Room> {

        @Override
        public int compare(Room o1, Room o2) {
            return o1.dist < o2.dist ? -1 : o1.dist == o2.dist ? 0 : 1;
        }
    }

}