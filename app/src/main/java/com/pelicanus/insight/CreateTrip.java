package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;

import java.io.IOException;

public class CreateTrip extends AppCompatActivity {


    private Button mCreateTrip;
    private Picture trip_avatar;

    private Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        trip = new Trip();
        trip.setEditFields((EditText)findViewById(R.id.text_nametrip), (EditText)findViewById(R.id.text_location), (EditText)findViewById(R.id.text_description), (EditText) findViewById(R.id.text_hCount), (DatePicker)findViewById(R.id.DatePicker), (Spinner)findViewById(R.id.select_language));
        mCreateTrip = findViewById(R.id.btn_create);
        trip_avatar = new Picture((ImageView) findViewById(R.id.trip_avatar), Picture.Type.Trip_avatar);

        mCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trip.getEditFields().readData();
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //tripCreator(name,description,date,address, userid, trip_avatar,language,Math.max(2, Long.parseLong(max_visitors)));
            }
        });




    }
    public void tripCreator(String name, String description, String date, String address, String id, Picture avatar,String language, long max_visitors){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Trips").push();
        avatar.setName(myRef.getKey());
        avatar.Upload();
        String trip_id =myRef.getKey();

        FirebaseDatabase.getInstance().getReference().child("Visitors").child(trip_id).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(false);

        myRef.setValue(new Trip(name,description,date,address,id,language, max_visitors)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    Toast.makeText(CreateTrip.this, R.string.trip_create_success, Toast.LENGTH_LONG).show();
                    finish();
                }
                else
                    Toast.makeText(CreateTrip.this, R.string.trip_create_error,Toast.LENGTH_LONG).show();
            }
            }
        );
    }
    public void setTrip_avatar(View view) {
        trip_avatar.Set(this);
    }
}
