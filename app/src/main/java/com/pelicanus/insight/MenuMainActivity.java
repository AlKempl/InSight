package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pelicanus.insight.model.TripsList;

public class MenuMainActivity extends AppBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }
        String user_id = firebaseAuth.getCurrentUser().getUid();
        TripsList tripsListOrg = new TripsList("Guide", user_id, this, null);
        tripsListOrg.setCountView((TextView)findViewById(R.id.excursions_organize_stat));
        TripsList tripsListPar = new TripsList("Participant", user_id, this, null);
        tripsListPar.setCountView((TextView)findViewById(R.id.excursions_participate_stat));
    }

}


