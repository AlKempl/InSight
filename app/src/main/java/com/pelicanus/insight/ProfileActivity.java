package com.pelicanus.insight;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.TripsList;
import com.pelicanus.insight.model.User;

public class ProfileActivity extends AppBaseActivity {
    private TripsList tripsListOrg;
    private TripsList tripsListPar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loadHeaderUserInfo();
    }

    protected void loadHeaderUserInfo() {

        User current = (User) DataHolder.getInstance().retrieve("PROFILE_USER");
        TextView username_label = findViewById(R.id.user_name);
        //TextView in_profile_rating_string_label = findViewById(R.id.in_profile_rating_string);
        current.setFieldName(username_label);
        //current.setFieldRating(in_profile_rating_string_label);
        current.getAvatar().setImageView((ImageView) findViewById(R.id.user_photo));

        RecyclerView recyclerViewGuide = findViewById(R.id.organize_trip_list);
        LinearLayoutManager llm = new LinearLayoutManager(recyclerViewGuide.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewGuide.setHasFixedSize(true);
        recyclerViewGuide.setLayoutManager(llm);
        tripsListOrg = new TripsList("Guide", current.getId(), this, recyclerViewGuide);
        tripsListOrg.setCountView((TextView)findViewById(R.id.profile_count_created));
        tripsListPar = new TripsList("Participant", current.getId(), this, null);
        tripsListPar.setCountView((TextView)findViewById(R.id.profile_count_par));
    }
    protected  void onDestroy() {
        super.onDestroy();
        DataHolder.getInstance().remove("PROFILE_USER");
        finish();
        tripsListOrg.removeReader();
        tripsListPar.removeReader();
    }
}