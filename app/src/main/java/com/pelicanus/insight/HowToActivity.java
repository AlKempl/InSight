package com.pelicanus.insight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HowToActivity extends AppCompatActivity {

    ExpandableListView expListView;
    ExpandableListAdapter expListAdapter;
    List<String> expListTitle;
    HashMap<String, List<Integer>> expListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_main);
        expListView = (ExpandableListView) findViewById(R.id.expListView);
        expListDetail = HowToData.loadData();

        expListTitle = new ArrayList<>(expListDetail.keySet());
        expListAdapter = new HowToAdapter(this, expListTitle, expListDetail);

        expListView.setAdapter(expListAdapter);
    }
}

class HowToData {
    static HashMap<String, List<Integer>> loadData() {
        HashMap<String, List<Integer>> expDetails = new HashMap<>();

        List<Integer> how_to_create = new ArrayList<>();
        how_to_create.add(R.layout.activity_how_to_create);

        List<Integer> how_to_search = new ArrayList<>();
        how_to_search.add(R.layout.activity_how_to_search);

        List<Integer> after_trip = new ArrayList<>();
//        after_trip.add(R.layout.activity_how_to_after_trip); // TODO ЗАМЕНИТЬ на activity_how_to_after_trip!!!!!!!!!!!

        expDetails.put("How to create", how_to_create);
        expDetails.put("How to search", how_to_search);
//        expDetails.put("What to do after", after_trip);

        return expDetails;
    }
}