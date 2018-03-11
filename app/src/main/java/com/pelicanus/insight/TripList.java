package com.pelicanus.insight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.Trip;
import com.pelicanus.insight.service.OutputServices;
import com.pelicanus.insight.service.TripListAdapter;

import java.util.ArrayList;

public class TripList extends AppCompatActivity {

    private DatabaseReference myRef;
    private ListView TripsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        myRef = FirebaseDatabase.getInstance().getReference().child("Trips");
        TripsList = findViewById(R.id.trip_list);

        OutputServices helper =new OutputServices(myRef);
        TripListAdapter arrayAdapter = new TripListAdapter(this,helper.retrieve());
        TripsList.setAdapter(arrayAdapter);
    }
}

