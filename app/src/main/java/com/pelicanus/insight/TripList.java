package com.pelicanus.insight;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;
import com.pelicanus.insight.model.TripsList;
import com.pelicanus.insight.service.TripAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class TripList extends AppBaseActivity {


    private RecyclerView recyclerView;
    private TripAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        recyclerView = findViewById(R.id.trip_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        TripsList tripsList = new TripsList("English", "9_3_2018", this, recyclerView);
    }


    private void updateList(){
        /*myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                trip.setTrip_id(dataSnapshot.getKey());
                trip.getVisitors().download();
                listofTrips.add(trip);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                trip.setTrip_id(dataSnapshot.getKey());
                int index =getItemIndex(trip);
                listofTrips.set(index,trip);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                Trip trip = dataSnapshot.getValue(Trip.class);
                trip.setTrip_id(dataSnapshot.getKey());
                int index =getItemIndex(trip);
                listofTrips.remove(index);
                adapter.notifyItemRemoved(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }


}

