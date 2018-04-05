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


    private EditText nameField;
    private EditText addressField;
    private EditText descriptionField;
    private Button mCreateTrip;
    private DatabaseReference myRef;
    private DatePicker datePicker;
    private Picture trip_avatar;
    private Spinner lang_spn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        nameField = findViewById(R.id.text_nametrip);
        addressField = findViewById(R.id.text_location);
        descriptionField = findViewById(R.id.text_description);
        mCreateTrip = findViewById(R.id.btn_create);
        datePicker=findViewById(R.id.DatePicker);
        lang_spn=findViewById(R.id.select_language);
        trip_avatar = new Picture((ImageView) findViewById(R.id.trip_avatar), Picture.Type.Trip_avatar);
        myRef = FirebaseDatabase.getInstance().getReference();
        final TextView max_visistorsFiled = findViewById(R.id.text_hCount);

        mCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameField.getText().toString().trim();
                String language = lang_spn.getSelectedItem().toString();
                String date = datePicker.getDayOfMonth()+"."+datePicker.getMonth()+"."+datePicker.getYear();
                String address = addressField.getText().toString().trim();
                String description = descriptionField.getText().toString().trim();
                String max_visitors = max_visistorsFiled.getText().toString();
                //Проверка данных на пустоту
                //При добавлении новых полей нужно не забыть добавить сюда!
                if (name.length() == 0||
                    date.length() == 0||
                    address.length() == 0||
                    description.length() == 0 ||
                    max_visitors.length() == 0) {
                    Toast.makeText(CreateTrip.this, R.string.trip_create_emptydata,Toast.LENGTH_LONG).show();
                    return;
                }

                //Тут должно быть преобразование строк и проверка на корректность ввода
                //Но нужно знать, какие типы должны получиться в итоге
                //Ещё можно будет "на уровне" xml запретить вводить не цифры, например
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                tripCreator(name,description,date,address, userid, trip_avatar,language,Math.max(2, Long.parseLong(max_visitors)));
                nameField.setText("");


                descriptionField.setText("");
            }
        });




    }
    public void tripCreator(String name, String description, String date, String address, String id, Picture avatar,String language, long max_visitors){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Trips").push();
        avatar.Upload(myRef.getKey());
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
