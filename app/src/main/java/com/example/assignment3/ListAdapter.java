package com.example.assignment3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    String TAG = "RecycleView";
    String[] names;
    String[] rollNos;
    String[] phoneNo;
    String[] emails;
    String[] departments;
    String[] branch;
    public static final int id1 = R.id.placeholder;

    int[] ids;
    Context mContext;
    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public ListAdapter(Context mContext,String[] names, String[] rollNos, String[] phoneNo, String[] emails, String[] departments, String[] branch, int[] ids) {
        this.names = names;
        this.rollNos = rollNos;
        this.phoneNo = phoneNo;
        this.emails = emails;
        this.departments = departments;
        this.branch = branch;
        this.ids = ids;
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder Called");
        Glide.with(mContext)
                .asBitmap()
                .load(ids[position])
                .into(holder.mImageView);

        holder.mRollNo.setText(rollNos[position]+"\n"+names[position]);

        holder.linearLayout.setOnClickListener(v -> {

            String[] data = new String[6];
            data[0] = names[position];
            data[1] = rollNos[position];
            data[2] = emails[position];
            data[3] = phoneNo[position];
            data[4] = departments[position];
            data[5] = branch[position];

        Bundle bundle = new Bundle();
        bundle.putStringArray("data",data);
        bundle.putInt("position",position);
        InformationFragment informationFragment = new InformationFragment();
        informationFragment.setArguments(bundle);

            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.placeholder,informationFragment).addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mRollNo;
        private CircleImageView mImageView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mRollNo = itemView.findViewById(R.id.rollNo);
            mImageView = itemView.findViewById(R.id.image);
            linearLayout = itemView.findViewById(R.id.parentLayout);
        }

        public void bindView(int position){
            mRollNo.setText(StudentsData.rollNo[position]+"\n"+StudentsData.name[position]);
            mImageView.setImageResource(StudentsData.id[position]);
        }
    }
}
