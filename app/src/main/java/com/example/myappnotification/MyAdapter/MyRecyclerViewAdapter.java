package com.example.myappnotification.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappnotification.MyModel.MyUserRegister;
import com.example.myappnotification.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.VH>{

    Context context;
    ArrayList<MyUserRegister> listData;

    public MyRecyclerViewAdapter(Context context, ArrayList<MyUserRegister> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        holder.tv.setText(listData.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        TextView tv;


        public VH(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}