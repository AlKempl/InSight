package com.pelicanus.insight;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.Trip;

import java.util.HashMap;

public class CreateTrip extends AppCompatActivity {


    private EditText nameField;
    private EditText dataField;
    private EditText addressField;
    private EditText descriptionField;
    private Button mCreateTrip;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        nameField = findViewById(R.id.tripname_field);
        dataField = findViewById(R.id.data_field);
        addressField = findViewById(R.id.address_field);
        descriptionField = findViewById(R.id.description_fileld);
        mCreateTrip = findViewById(R.id.btn_create);

        myRef = FirebaseDatabase.getInstance().getReference();

        mCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameField.getText().toString().trim();
                String data = dataField.getText().toString().trim();
                String address = addressField.getText().toString().trim();
                String description = descriptionField.getText().toString().trim();

                tripCreator(name,description,data,address, FirebaseAuth.getInstance().getCurrentUser().getUid());


               /* HashMap<String,String> datamap = new HashMap<String, String>();
                datamap.put("Name",name);
                datamap.put("Time",data);




                myRef.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete())
                            Toast.makeText(CreateTrip.this, R.string.trip_create_success,Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(CreateTrip.this, R.string.trip_create_error,Toast.LENGTH_LONG).show();
                    }
                });*/
            }
        });




    }
    public void tripCreator(String name,String description,String data, String address,String id){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Trips").child(name);
        myRef.setValue(new Trip(name,description,data,address,id));
    }
}
