package com.pelicanus.insight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pelicanus.insight.model.Trip;


public class CreateTrip extends AppCompatActivity {

    private Button mCreateTrip;
    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        trip = new Trip();
        trip.setEditFields((EditText)findViewById(R.id.text_nametrip), (EditText)findViewById(R.id.text_location), (EditText)findViewById(R.id.text_description), (EditText) findViewById(R.id.text_hCount), (DatePicker)findViewById(R.id.DatePicker), (Spinner)findViewById(R.id.select_language));
        mCreateTrip = findViewById(R.id.btn_create);
        trip.getAvatar().setImageView((ImageView) findViewById(R.id.trip_avatar));
        mCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trip.writeTripData(CreateTrip.this);
            }
        });
    }

    public void setTrip_avatar(View view) {
        trip.getAvatar().Set(this);
    }
}