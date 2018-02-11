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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateTrip extends AppCompatActivity {


    private EditText nameField;
    private EditText timeField;
    private Button mCreateTrip;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        nameField = findViewById(R.id.text_nametrip);
        timeField = findViewById(R.id.text_time);
        mCreateTrip = findViewById(R.id.btn_create);

        myRef = FirebaseDatabase.getInstance().getReference();

        mCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameField.getText().toString().trim();
                String time = timeField.getText().toString().trim();

                HashMap<String,String> datamap = new HashMap<String, String>();
                datamap.put("Name",name);
                datamap.put("Time",time);


                myRef.push().setValue(datamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete())
                            Toast.makeText(CreateTrip.this,"good",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(CreateTrip.this,"FUUUUUUUUck",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
