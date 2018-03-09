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
import com.pelicanus.insight.service.TripListAdapter;

import java.util.ArrayList;

//import com.firebase.ui.database.FirebaseListAdapter;

public class TripList extends AppCompatActivity {

    private DatabaseReference myRef;
    private ArrayList<Trip> Triplst = new ArrayList<>();
    private ListView TripsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        myRef = FirebaseDatabase.getInstance().getReference();
        TripsList = findViewById(R.id.trip_list);


        final TripListAdapter arrayAdapter = new TripListAdapter(this, R.layout.list_item, Triplst);

        TripsList.setAdapter(arrayAdapter);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String value = dataSnapshot.getValue().toString();
                //TODO Do smth with adding trip and viewing fields
                //Triplst.add(value);
                arrayAdapter.notifyDataSetChanged();
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






//        firebaseListAdapter = new FirebaseListAdapter<String>(this,String.class,android.R.layout.simple_list_item_1) {
//            @Override
//            protected void populateView(View view, String com.pelicanus.insight.model, int position) {
//                TextView textView = (TextView) view.findViewById(R.id.text1);
//                textView.setText(com.pelicanus.insight.model);
//            }
//        }
    }
}

