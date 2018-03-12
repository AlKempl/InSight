package com.pelicanus.insight.service;

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
        Trip t = dataSnapshot.getValue(Trip.class);
        t.setTrip_id(dataSnapshot.getKey());
        trips.add(t);
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
