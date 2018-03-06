package com.pelicanus.insight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pelicanus.insight.model.Trip;

import java.util.ArrayList;


public class TripList extends AppCompatActivity {

    private DatabaseReference myRef;
    private ListView TripsList;

    private ArrayList<String> arrayList =new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        myRef = FirebaseDatabase.getInstance().getReference().child("Trips");
        TripsList = findViewById(R.id.trip_list);

       adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
       TripsList.setAdapter(adapter);


           myRef.addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                   String key = dataSnapshot.getKey();
                   String value = dataSnapshot.child("description").getValue(String.class);

                   arrayList.add(value);
                   adapter.notifyDataSetChanged();
               }

               @Override
               public void onChildChanged(DataSnapshot dataSnapshot, String s) {

               }

               @Override
               public void onChildRemoved(DataSnapshot dataSnapshot) {

               }

               @Override
               public void onChildMoved(DataSnapshot dataSnapshot, String s) {

               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });





        }
    }


