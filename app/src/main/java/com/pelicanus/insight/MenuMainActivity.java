package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuMainActivity extends AppBaseActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //Views for filling recently added part
        //TODO Add function to get names and images of 3 most recent excursions and assign them to views
        //TODO Add links to 3 most recent excursions
        ImageView ra_excursions_1_img = findViewById(R.id.ra_excursion_1_img);
        ImageView ra_excursions_2_img = findViewById(R.id.ra_excursion_2_img);
        ImageView ra_excursions_3_img = findViewById(R.id.ra_excursion_3_img);

        TextView ra_excursions_1_name = findViewById(R.id.ra_excursion_1_name);
        TextView ra_excursions_2_name = findViewById(R.id.ra_excursion_2_name);
        TextView ra_excursions_3_name = findViewById(R.id.ra_excursion_3_name);
    }


}


