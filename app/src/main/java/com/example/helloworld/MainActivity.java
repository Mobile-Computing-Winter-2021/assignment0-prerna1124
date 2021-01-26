package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView returnStatus;
    private EditText enterName;
    public String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        Toast.makeText(MainActivity.this,""+getLifecycle().getCurrentState(), Toast.LENGTH_SHORT).show();

        enterName = (EditText) findViewById(R.id.textViewId);
        Log.i("INFO", "Name Fetched Successfully!");
        returnStatus = (TextView) findViewById(R.id.returnTextView);

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,""+getLifecycle().getCurrentState(), Toast.LENGTH_SHORT).show();
                Log.i("INFO", "submit successfully!!!");
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                String name = enterName.getText().toString();
                intent.putExtra("enterName", name);


                boolean[] isChecked = new boolean[5];
                CheckBox checkBox1 = (CheckBox) findViewById(R.id.cb1);
                isChecked[0] = checkBox1.isChecked();
                CheckBox checkBox2 = (CheckBox) findViewById(R.id.cb2);
                isChecked[1] = checkBox2.isChecked();
                CheckBox checkBox3 = (CheckBox) findViewById(R.id.cb3);
                isChecked[2] = checkBox3.isChecked();
                CheckBox checkBox4 = (CheckBox) findViewById(R.id.cb4);
                isChecked[3] = checkBox4.isChecked();
                CheckBox checkBox5 = (CheckBox) findViewById(R.id.cb5);
                isChecked[4] = checkBox5.isChecked();

                intent.putExtra("checkedValues",isChecked);

                startActivityForResult(intent, 2);
            }
        });

        Button clear = findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,""+getLifecycle().getCurrentState(), Toast.LENGTH_SHORT).show();
                Log.i("INFO", "Cleared Successfully");
                returnStatus = findViewById(R.id.returnTextView);
                returnStatus.setText("");
                enterName.setText("");
                CheckBox cb1 = (CheckBox) findViewById(R.id.cb1);
                cb1.setChecked(false);

                CheckBox cb2 = (CheckBox) findViewById(R.id.cb2);
                cb2.setChecked(false);

                CheckBox cb3 = (CheckBox) findViewById(R.id.cb3);
                cb3.setChecked(false);

                CheckBox cb4 = (CheckBox) findViewById(R.id.cb4);
                cb4.setChecked(false);

                CheckBox cb5 = (CheckBox) findViewById(R.id.cb5);
                cb5.setChecked(false);
            }
        });

        if(savedInstanceState != null) {
            String val = savedInstanceState.getString("value");
            returnStatus = findViewById(R.id.returnTextView);
            returnStatus.setText(val);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "State of MainActivity has been changed from Create to Start", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Create to Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "State of MainActivity has been changed from Pause to Stop", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Pause to Stop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "State of MainActivity has been changed from Pause to Resume", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Pause to Resume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Toast.makeText(this, "State of MainActivity has been changed to Pause", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed to Pause");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Toast.makeText(this, "State of MainActivity has been changed from Stop to Restart", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Stop to Restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "State of MainActivity has been changed from Stop to Destroy", Toast.LENGTH_SHORT).show();
        Log.i(msg, "State of MainActivity has been changed from Stop to Destroy");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(MainActivity.this,""+getLifecycle().getCurrentState(), Toast.LENGTH_SHORT).show();
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                boolean flag = data.getBooleanExtra("showStatus",false);
                if(flag == false){
                    returnStatus = findViewById(R.id.returnTextView);
                    returnStatus.setText("Unsafe");
                    returnStatus.setTextColor(Color.RED);
                }else{
                    returnStatus = findViewById(R.id.returnTextView);
                    returnStatus.setText("Safe");
                    returnStatus.setTextColor(Color.GREEN);
                }

            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        returnStatus = findViewById(R.id.returnTextView);
        String val = returnStatus.getText().toString();
        outState.putString("value",val);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
