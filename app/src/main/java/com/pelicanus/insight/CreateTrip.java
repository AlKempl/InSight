package com.pelicanus.insight;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.FirebaseStorage;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;

public class CreateTrip extends AppCompatActivity {


    private EditText nameField;
    //private EditText addressField;
    private EditText descriptionField;
    private Button mCreateTrip;
    private DatabaseReference myRef;
    private DatePicker datePicker;
    private Picture trip_avatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        nameField = findViewById(R.id.text_nametrip);
        //addressField = findViewById(R.id.address_field);
        descriptionField = findViewById(R.id.text_description);
        mCreateTrip = findViewById(R.id.btn_create);
        datePicker=findViewById(R.id.DatePicker);
        trip_avatar = new Picture((ImageView) findViewById(R.id.trip_avatar), Picture.Type.Trip_avatar);
        myRef = FirebaseDatabase.getInstance().getReference();

        mCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = nameField.getText().toString().trim();

                String date = datePicker.getDayOfMonth()+"."+datePicker.getMonth()+"."+datePicker.getYear();
                String address = "Заглушка, потому что в дезигне отсутствует поле";//addressField.getText().toString().trim();
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
                tripCreator(name,description,date,address, FirebaseAuth.getInstance().getCurrentUser().getUid(), trip_avatar);
                nameField.setText("");
                descriptionField.setText("");
                //Для тестирования
                new Picture((ImageView) findViewById(R.id.trip_avatar), Picture.Type.Trip_avatar).Load("NeEV6LBLiqo.jpg");
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
    public void tripCreator(String name, String description, String date, String address, String id, Picture avatar){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Trips").push();
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
       avatar.Upload(myRef.getKey());
    }
    public void setTrip_avatar(View view) {
        trip_avatar.Set();
        Toast.makeText(CreateTrip.this, "вы попытались сменить аватарку",Toast.LENGTH_LONG).show();
    }
}
