package com.pelicanus.insight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.TripsList;
import com.pelicanus.insight.model.User;
import com.pelicanus.insight.service.TripAdapter;

public class MyExcursionsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewGuide;
    private RecyclerView recyclerViewParc;
    TripsList tripsListOrg;
    TripsList tripsListPar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_excursions);


        recyclerViewGuide = findViewById(R.id.organize_trip_list);
        recyclerViewParc = findViewById(R.id.participate_trip_list);
        LinearLayoutManager llm = new LinearLayoutManager(recyclerViewGuide.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewGuide.setHasFixedSize(true);
        recyclerViewGuide.setLayoutManager(llm);
        LinearLayoutManager llm2 = new LinearLayoutManager(recyclerViewParc.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewParc.setHasFixedSize(true);
        recyclerViewParc.setLayoutManager(llm2);
        User user = (User) DataHolder.getInstance().retrieve("CURR_USER");
        tripsListOrg = new TripsList("Guide", user.getId(), this, recyclerViewGuide);
        tripsListPar = new TripsList("Participant", user.getId(), this, recyclerViewParc);
    }
    protected void onDestroy() {
        super.onDestroy();
        tripsListOrg.removeReader();
        tripsListPar.removeReader();
    }
}
