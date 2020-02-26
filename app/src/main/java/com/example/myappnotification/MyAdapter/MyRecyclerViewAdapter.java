package com.example.myappnotification.MyAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappnotification.MyInterface.MyListener;
import com.example.myappnotification.MyModel.MyUserLogin;
import com.example.myappnotification.MyModel.MyUserRegister;
import com.example.myappnotification.R;
import com.example.myappnotification.ui.NotiPreviewFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.VH>{


    Context context;
    ArrayList<MyUserLogin> listData;

    //test ****************
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    MyListener myListener;



    public MyRecyclerViewAdapter(Context context, ArrayList<MyUserLogin> listData,MyListener myListener) {
        this.context = context;
        this.listData = listData;
        this.myListener = myListener;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        //test ****************
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        holder.tvName.setText(listData.get(position).getName());
        holder.tvEmail.setText(listData.get(position).getEmail());
        holder.tvToken.setText(listData.get(position).getToken());

        holder.itemView.setTag(position);

        //Show Current User Login
        holder.tvCurrUser.setText("user : "+userId);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        TextView tvName,tvEmail,tvToken,tvCurrUser;


        public VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvToken = itemView.findViewById(R.id.tvToken);
            tvCurrUser = itemView.findViewById(R.id.tvCurrUser);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int item = (int) v.getTag();
                    //Toast.makeText(context,"item is "+listData.get(item).getToken(),Toast.LENGTH_SHORT).show();
                    MyUserLogin myUserLogin = listData.get(item);
                    myListener.myCallBack(myUserLogin);

                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

        //TODO ซ่อน Current User****************************

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(context instanceof MyListener){
            myListener = (MyListener) context;
        }
    }


    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        myListener = null;
    }
}