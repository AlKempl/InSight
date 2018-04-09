package com.pelicanus.insight.model;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.service.TripAdapter;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Slavik on 09.04.2018.
 */

public class TripsList {
    private HashMap<String, Integer> ids = new HashMap<>();
    private ArrayList<Trip> trips = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("TripLists");
    private String parent;
    private String tag;
    private TripAdapter adapter;
    private TextView countView;

    public TripsList(String parent, String tag, Activity activity, RecyclerView recyclerView) {
        this.parent = parent;
        this.tag = tag;
        RecyclerView recyclerView1 = recyclerView;
        if (recyclerView != null) {
            adapter = new TripAdapter(activity, trips);
            recyclerView.setAdapter(adapter);
        }
        download();
    }

    private DatabaseReference getReference() {
        if (parent != null)
            return root.child(parent).child(tag);
        else
            return null;
    }
    public void changeTag(String parent, String tag) {
        removeReader();
        this.parent = parent;
        this.tag = tag;
        download();
    }

    public void removeReader() {
        if (getReference()!=null)
            getReference().removeEventListener(reader);
    }

    private void download() {
        getReference().addValueEventListener(reader);
    }

    public void setCountView(TextView countView) {
        this.countView = countView;
        loadCountView();
    }

    private void loadCountView() {
        if(countView!=null)
            countView.setText(trips.size()+"");
    }
    private ValueEventListener  reader = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            HashMap _ids = new HashMap<String, String>();
            for ( DataSnapshot v:dataSnapshot.getChildren()) {
                _ids.put(v.getKey(), v.getValue().toString());
            }
            Set<String> past =  new HashSet<String>(ids.keySet());
            Set<String> now = _ids.keySet();

            past.removeAll(now);
            for (String k:past) {
                int p = ids.get(k);
                trips.remove(p);
                ids.remove(k);
                if (adapter!=null)
                    adapter.notifyItemRemoved(p);
            }
            for (int i = 0; i<trips.size(); i++)
                ids.put(trips.get(i).getTrip_id(), i);
            now.removeAll(ids.keySet());
            for (String id:now) {
                ids.put(id, trips.size());
                trips.add(new Trip(id));
            }
            if (adapter!=null)
                adapter.notifyDataSetChanged();
            loadCountView();
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}
