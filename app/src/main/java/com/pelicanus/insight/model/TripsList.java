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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Slavik on 09.04.2018.
 */

public class TripsList {
    private ArrayList<String> ids = new ArrayList<>();
    private ArrayList<Trip> trips = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("TripLists");
    private String parent;
    private String tag;
    private TripAdapter adapter;
    private RecyclerView recyclerView;
    private Activity activity;

    public TripsList(String parent, String tag, Activity activity, RecyclerView recyclerView) {
        this.parent = parent;
        this.tag = tag;
        this.recyclerView = recyclerView;
        this.activity = activity;
        if (recyclerView != null) {
            adapter = new TripAdapter(activity, trips);
            recyclerView.setAdapter(adapter);
        }
        download();
    }

    private ArrayList<String> getIds() {
        return this.ids;
    }
    public ArrayList<String> get() {
        if (ids.size() == 0)
            download();
        return getIds();
    }
    public void download() {
        root.child(parent).child(tag).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap _visitors = new HashMap<String, String>();
                for ( DataSnapshot v:dataSnapshot.getChildren()) {
                    _visitors.put(v.getKey(), v.getValue().toString());
                }
                Set<String> past =  new HashSet<String>(getIds());
                Set<String> now = _visitors.keySet();
                past.removeAll(now);
                for (String k:past) {
                    ids.remove(k);
                }
                ids.addAll(_visitors.keySet());
                for (String id:ids) {
                    trips.add(new Trip(id));
                }
                if (adapter!=null)
                    adapter.notifyDataSetChanged();
                loadCountView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public boolean isVisitor(String user_id) {
        return getIds().contains(user_id);
    }

    public int size() {
        if (ids.size() == 0) {
            download();
            return 0;
        }
        return ids.size();
    }
    public Trip get(int position) {
        return trips.get(position);
    }

    public void setCountView(TextView countView) {
        this.countView = countView;
        loadCountView();
    }

    private void loadCountView() {
        if(countView!=null)
            countView.setText(ids.size()+"");
    }

    private TextView countView;
}
