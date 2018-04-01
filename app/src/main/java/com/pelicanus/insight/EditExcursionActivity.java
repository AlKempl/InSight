package com.pelicanus.insight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditExcursionActivity extends AppCompatActivity {

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_excursion);

        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        EditText exc_name = findViewById(R.id.exc_name);
        EditText exc_description = findViewById(R.id.exc_description);
        EditText exc_place = findViewById(R.id.exc_place);
        EditText exc_visitors = findViewById(R.id.exc_visitors);
        DatePicker exc_date = findViewById(R.id.exc_date);
        Spinner language = findViewById(R.id.select_language);

        exc_name.setText(getIntent().getExtras().getString("name"));
        exc_description.setText(getIntent().getExtras().getString("description"));
        exc_place.setText(getIntent().getExtras().getString("adress"));
        String date = getIntent().getExtras().getString("date");
        String[] tokens = date.split("[,]");
        //exc_date.init(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), null);
    }
}
