package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    public boolean status = false;
    public String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        Toast.makeText(this,""+getLifecycle().getCurrentState(), Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        String enterName = intent.getStringExtra("enterName");
        Log.i("INFO", "fetched name from Main Activity Successfully");
        TextView textField = (TextView) findViewById(R.id.textView);

        boolean[] checkedName = this.getIntent().getBooleanArrayExtra("checkedValues");

        String st = "";
            if (checkedName[0]) {
                st += "\t Wearing mask when going outside.\n";
            }
            if (checkedName[1]) {
                st += "\t Wash your hands Regularly.\n";
            }
            if (checkedName[2]) {
                st += "\t Maintain social distancing.\n";
            }
            if (checkedName[3]) {
                st += "\t Cover your nose and mouth while sneezing and coughing.\n";
            }
            if (checkedName[4]) {
                st += "\t Take healthy diet.\n";
            }
        textField.setText("  Hi "+enterName+" you have selected.\n"+st);

        Button checkMe = findViewById(R.id.checkMe);

        checkMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity2.this,""+getLifecycle().getCurrentState(), Toast.LENGTH_SHORT).show();
                Log.i("INFO", "Check button successfully clicked !!!");
                if( checkedName[0] && checkedName[1] && checkedName[2] && checkedName[3] && checkedName[4]){
                    Toast.makeText(MainActivity2.this, "Congratulation! You are in SAFE State.", Toast.LENGTH_SHORT).show();
                    status = true;
                }else{
                    Toast.makeText(MainActivity2.this, "You are at risk...Take Precautions!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button goBack = findViewById(R.id.goBack);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity2.this,""+getLifecycle().getCurrentState(), Toast.LENGTH_SHORT).show();
                Log.i("INFO", "Back button successfully clicked !!!");
                Intent statusIntent = new Intent();
                statusIntent.putExtra("showStatus", status);
                setResult(RESULT_OK, statusIntent);
                finish();

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "State of MainActivity2 has been changed from Create to Start", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Create to Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "State of MainActivity2 has been changed from Pause to Stop", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Pause to Stop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "State of MainActivity2 has been changed from Pause to Resume", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Pause to Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Toast.makeText(this, "State of MainActivity2 has been changed to Pause", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed to Pause");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Toast.makeText(this, "State of MainActivity2 has been changed from Stop to Restart", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Stop to Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "State of MainActivity2 has been changed from Stop to Destroy", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Stop to Destroy");
    }
}
