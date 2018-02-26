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

        nameField = findViewById(R.id.text_nametrip);
        dataField = findViewById(R.id.date_field);
        addressField = findViewById(R.id.address_field);
        descriptionField = findViewById(R.id.description_fileld);
        mCreateTrip = findViewById(R.id.btn_create);

        myRef = FirebaseDatabase.getInstance().getReference();

        mCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameField.getText().toString().trim();
                String date = dataField.getText().toString().trim();
                String address = addressField.getText().toString().trim();
                String description = descriptionField.getText().toString().trim();
                //Проверка данных на пустоту
                //При добавлении новых полей нужно не забыть добавить сюда!
                if (name.length() == 0||
                    date.length() == 0||
                    address.length() == 0||
                    description.length() == 0) {
                    Toast.makeText(CreateTrip.this, R.string.trip_create_emptydata,Toast.LENGTH_LONG).show();
                    return;
                }
                //Тут должно быть преобразование строк и проверка на корректность ввода
                //Но нужно знать, какие типы должны получиться в итоге
                //Ещё можно будет "на уровне" xml запретить вводить не цифры, например
                tripCreator(name,description,date,address, FirebaseAuth.getInstance().getCurrentUser().getUid());


               /* HashMap<String,String> datamap = new HashMap<String, String>();
                datamap.put("Name",name);
                datamap.put("Time",date);




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
    public void tripCreator(String name,String description,String date, String address,String id){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Trips").child(name);
        myRef.setValue(new Trip(name,description,date,address,id)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete())
                    Toast.makeText(CreateTrip.this, R.string.trip_create_success,Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(CreateTrip.this, R.string.trip_create_error,Toast.LENGTH_LONG).show();
            }
            }
        );
    }
}
