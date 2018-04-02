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
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;
import com.pelicanus.insight.service.TripAdapter;

import java.util.ArrayList;


public class TripList extends AppBaseActivity {

    ArrayList<Trip> listofTrips;
    private DatabaseReference myRef;
    private RecyclerView recyclerView;
    private TripAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        listofTrips = new ArrayList<>();

        myRef = FirebaseDatabase.getInstance().getReference().child("Trips");
        recyclerView = findViewById(R.id.trip_list);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new TripAdapter(this,listofTrips);
        updateList();
        recyclerView.setAdapter(adapter);

    }


    private void updateList(){
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                trip.setTrip_id(dataSnapshot.getKey());
                trip.avatar = new Picture(Picture.Type.Trip_avatar, dataSnapshot.getKey());
                listofTrips.add(trip);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                trip.setTrip_id(dataSnapshot.getKey());
                trip.avatar = new Picture(Picture.Type.Trip_avatar, dataSnapshot.getKey());
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
        });
    }
    private int getItemIndex(Trip trip){
        int index =-1;
        for(int i=0;i<listofTrips.size();i++){
            if(listofTrips.get(i).getTrip_id().equals(trip.getTrip_id())){
                index =i;
                break;
            }
        }
        return index;
    }
    public void review_Trip(View view){
        int position = recyclerView.getChildLayoutPosition(view);
        Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();
    }


}

