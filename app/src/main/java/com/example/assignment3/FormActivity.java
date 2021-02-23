package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {

    EditText name,rollNo,email,phoneNo,department,branch;
    Button submitButton,goTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Intent i = getIntent();

        String[] data = i.getStringArrayExtra("data");
        int position = i.getIntExtra("position",0);

        name = (EditText) findViewById(R.id.printName);
        rollNo = (EditText)findViewById(R.id.printRollNo);
        phoneNo = (EditText)findViewById(R.id.printPhoneNo);
        email = (EditText)findViewById(R.id.printEmail);
        department = (EditText)findViewById(R.id.printDepartment);
        branch = (EditText)findViewById(R.id.printBranch);

        name.setText(data[0]);
        rollNo.setText(data[1]);
        email.setText(data[2]);
        phoneNo.setText(data[3]);
        department.setText(data[4]);
        branch.setText(data[5]);

        submitButton = (Button) findViewById(R.id.submitButton);
        goTo = (Button) findViewById(R.id.goTo);

        submitButton.setOnClickListener(v -> {
            StudentsData.name[position] = name.getText().toString();
            StudentsData.rollNo[position] = rollNo.getText().toString();
            StudentsData.email[position] = email.getText().toString();
            StudentsData.phoneNo[position] = phoneNo.getText().toString();
            StudentsData.department[position] = department.getText().toString();
            StudentsData.branch[position] = department.getText().toString();
            Toast.makeText(FormActivity.this,"Updated Successfully!!!",Toast.LENGTH_SHORT).show();
        });

        goTo.setOnClickListener(v -> {
            Toast.makeText(FormActivity.this,"GOTO Button Clicked",Toast.LENGTH_SHORT).show();
            Intent mIntent = new Intent(FormActivity.this,MainActivity.class);
            startActivity(mIntent);
        });


    }
}