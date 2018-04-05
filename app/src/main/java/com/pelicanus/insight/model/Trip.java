package com.pelicanus.insight.model;



import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by Olga on 10.02.2018.
 */
@NoArgsConstructor
@Setter
@Getter
@Data
public class Trip {

    @Getter
    @NonNull
    String name;

    @Getter
    @NonNull
    String description;

    @Getter
    @NonNull
    String date;

    @Getter
    @NonNull
    String address;

    @NonNull
    String guide_id;

    @NonNull
    @Setter
    @Getter
    String trip_id;

    @Setter
    @Getter
    String language;

    long max_visitors;

    public Picture avatar;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    HashMap<String, String> visitors;

    public Trip(String name, String description, String date, String address, String guide_id, String language, long max_visitors) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.guide_id = guide_id;
        this.date = date;
        this.language=language;
        this.max_visitors = max_visitors;
    }

    public long getMax_visitors() {
        return Math.max(2, max_visitors);
    }
    public void downloadVisitors() {
        database.child("Visitors").child(this.getTrip_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                visitors = new HashMap<String, String>();
                for ( DataSnapshot v:dataSnapshot.getChildren()) {
                    visitors.put(v.getKey(), v.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
