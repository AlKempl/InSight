package com.pelicanus.insight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Trip;

public class EditExcursionActivity extends AppCompatActivity {

    DatabaseReference reference;
    Trip trip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_excursion);

        trip = (Trip)DataHolder.getInstance().retrieve("REQUESTED_TRIP");
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        EditText exc_name = findViewById(R.id.exc_name);
        EditText exc_description = findViewById(R.id.exc_description);
        EditText exc_place = findViewById(R.id.exc_place);
        EditText exc_visitors = findViewById(R.id.exc_visitors);
        DatePicker exc_date = findViewById(R.id.exc_date);
        Spinner language = findViewById(R.id.select_language);

        exc_name.setText(trip.getName());
        exc_description.setText(trip.getDescription());
        exc_place.setText(trip.getAddress());
        String date = trip.getDate();
        String[] tokens = date.split("[,]");
        //exc_date.init(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), null);
    }
}
