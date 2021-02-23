package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class InformationFragment extends Fragment {

    TextView name,rollNo,email,phoneNo,department,branch;
    ImageButton imgButton;
    String TAG = "InformationFragment";
    Intent intent;
    public InformationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        name = (TextView) view.findViewById(R.id.printName);
        rollNo = (TextView) view.findViewById(R.id.printRollNo);
        phoneNo = (TextView) view.findViewById(R.id.printPhoneNo);
        email = (TextView) view.findViewById(R.id.printEmail);
        department = (TextView) view.findViewById(R.id.printDepartment);
        branch = (TextView) view.findViewById(R.id.printBranch);
        String[] data = this.getArguments().getStringArray("data");
        int position = this.getArguments().getInt("position");
        name.setText(data[0]);
        rollNo.setText(data[1]);
        email.setText(data[2]);
        phoneNo.setText(data[3]);
        department.setText(data[4]);
        branch.setText(data[5]);

        imgButton = (ImageButton) view.findViewById(R.id.editButton);

        imgButton.setOnClickListener(v -> {
            Log.d(TAG,"Edit Button Clicked");
            intent = new Intent(getActivity(), FormActivity.class);
            intent.putExtra("data",data);
            intent.putExtra("position",position);
            startActivity(intent);

        });


        return view;
    }
}