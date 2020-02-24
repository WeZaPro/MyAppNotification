package com.example.myappnotification.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myappnotification.MyAdapter.MyRecyclerViewAdapter;
import com.example.myappnotification.MyModel.MyUserRegister;
import com.example.myappnotification.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RVFragment extends Fragment {

    View v;
    RecyclerView _myRecyclerView;
    MyRecyclerViewAdapter myAdapter;
    DatabaseReference databaseReference;

    ArrayList<MyUserRegister> listData = new ArrayList<>();

    public RVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (v == null){
            v = inflater.inflate(R.layout.fragment_rv, container, false);

            _myRecyclerView = v.findViewById(R.id.myRecyclerView);
            _myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren() ){
                        MyUserRegister myUserRegister = dataSnapshot1.getValue(MyUserRegister.class);
                        listData.add(myUserRegister);
                    }

                    myAdapter = new MyRecyclerViewAdapter(getActivity(),listData);
                    _myRecyclerView.setAdapter(myAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(),"error "+databaseError,Toast.LENGTH_SHORT).show();
                }
            });

        }

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // go to RecyclerView Fragment
        LoginFragment loginFragment = new LoginFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentContainer,loginFragment)
                .addToBackStack("")
                .commit();
    }
}
