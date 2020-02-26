package com.example.myappnotification.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myappnotification.MyAdapter.MyRecyclerViewAdapter;
import com.example.myappnotification.MyInterface.MyListener;
import com.example.myappnotification.MyModel.MyUserLogin;
import com.example.myappnotification.MyModel.MyUserRegister;
import com.example.myappnotification.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class RVFragment extends Fragment implements OnBackPressedListener{

    View v;
    RecyclerView _myRecyclerView;
    MyRecyclerViewAdapter myAdapter;
    DatabaseReference databaseReference;

    ArrayList<MyUserLogin> listData = new ArrayList<>();
    MyListener myListener;

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


                databaseReference = FirebaseDatabase.getInstance().getReference().child("login");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // ใส่เพราะ เมื่อ Insert ข้อมูลแล้ว RecyclerView Dupplicate Data View
                        listData.clear();

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren() ){
                            MyUserLogin myUserLogin = dataSnapshot1.getValue(MyUserLogin.class);
                            listData.add(myUserLogin);
                        }

                        myAdapter = new MyRecyclerViewAdapter(getActivity(),listData,myListener);
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
    public void doBack() {
        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


    // ใส่แล้วเวลาหมุนจอ Error
    /*@Override
    public void onDetach() {
        super.onDetach();

        // go to RecyclerView Fragment
        LoginFragment loginFragment = new LoginFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentContainer,loginFragment)
                .addToBackStack("")
                .commit();
    }*/


}
