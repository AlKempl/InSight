package com.pelicanus.insight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.Trip;

import java.util.ArrayList;

//import com.firebase.ui.database.FirebaseListAdapter;

public class TripList extends AppCompatActivity {

    private DatabaseReference myRef;
    private ArrayList<String> tripName = new ArrayList<>();
    private ListView TripsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        myRef = FirebaseDatabase.getInstance().getReference().child("Trips");
        TripsList = findViewById(R.id.trip_list);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tripName);



        TripsList.setAdapter(arrayAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String value =dataSnapshot.child("description").getValue().toString();
                tripName.add(value);
                arrayAdapter.notifyDataSetChanged();
                /**/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String value =dataSnapshot.child("description").getValue().toString();
                tripName.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value =dataSnapshot.child("description").getValue().toString();
                tripName.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                String value =dataSnapshot.child("description").getValue().toString();
                tripName.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }

}

