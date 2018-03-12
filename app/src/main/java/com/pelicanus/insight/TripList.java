package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

//import com.firebase.ui.database.FirebaseListAdapter;

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

        final ArrayList<Trip> listofTrips= helper.retrieve();

        final TripListAdapter arrayAdapter = new TripListAdapter(this,listofTrips);


        TripsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(TripList.this,ExcursionViewActivity.class);
                Trip selectedTrip =listofTrips.get(position);
                intent.putExtra("name",selectedTrip.getName());
                intent.putExtra("date",selectedTrip.getDate());
                intent.putExtra("address",selectedTrip.getAddress());
                intent.putExtra("description",selectedTrip.getDescription());
                intent.putExtra("guide_id",selectedTrip.getGuide_id());
                intent.putExtra("Trip_id",selectedTrip.getTrip_id());
                startActivity(intent);
            }
        });

        TripsList.setAdapter(arrayAdapter);


    }
}

