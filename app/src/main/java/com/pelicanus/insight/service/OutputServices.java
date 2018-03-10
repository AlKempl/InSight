package com.pelicanus.insight.service;

import android.nfc.Tag;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.pelicanus.insight.model.Trip;

import java.util.ArrayList;

/**
 * Created by Luckynatrium on 09.03.2018.
 */

public class OutputServices {

    DatabaseReference reference;
    ArrayList<Trip> trips=new ArrayList<>();

    public OutputServices(DatabaseReference reference){
        this.reference=reference;
    }


    private void fetchData(DataSnapshot dataSnapshot) {

        trips.clear();
        String name = dataSnapshot.child("name").getValue().toString();
        String date = dataSnapshot.child("data").getValue().toString();
        String address = dataSnapshot.child("address").getValue().toString();
        String description = dataSnapshot.child("description").getValue().toString();
        String guide_id = dataSnapshot.child("guide_id").getValue().toString();
        trips.add(new Trip(name,description,date,address,guide_id));
    }


    public ArrayList<Trip> retrieve(){

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return trips;
    }
}
