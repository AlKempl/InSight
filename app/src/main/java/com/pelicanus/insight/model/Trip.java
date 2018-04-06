package com.pelicanus.insight.model;



import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.ExcursionViewActivity;

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

    Picture avatar = new Picture(Picture.Type.Trip_avatar, "0");
    HashMap<String, String> visitors = new HashMap<String, String>();

    public Trip(String name, String description, String date, String address, String guide_id, String language, long max_visitors) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.guide_id = guide_id;
        this.date = date;
        this.language=language;
        this.max_visitors = max_visitors;
    }
    public Trip(String id) {
        setTrip_id(id);
        readTripData();
    }
    public void setTrip_id(String id) {
        this.trip_id = id;
        avatar.setName(id);
    }
    public void writeTripData() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Trips").child(getTrip_id());
        reference.setValue(new Soul(this));
    }
    public void readTripData() {
        FirebaseDatabase.getInstance().getReference().child("Trips").child(trip_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                installSoul(new Soul(dataSnapshot));
                //loadToAllField();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Getter
    @Setter
    public class Soul {
        private String name;
        private String description;
        private String address;
        private String guide_id;
        private String date;
        private String language;
        private Long max_visitors;
        public Soul(DataSnapshot dataSnapshot) {
            setName(dataSnapshot.child("name").getValue(String.class));
            setDate(dataSnapshot.child("date").getValue(String.class));
            setAddress(dataSnapshot.child("address").getValue(String.class));
            setGuide_id(dataSnapshot.child("guide_id").getValue(String.class));
            setDescription(dataSnapshot.child("description").getValue(String.class));
            setLanguage(dataSnapshot.child("language").getValue(String.class));
            setMax_visitors(dataSnapshot.child("max_visitors").getValue(Long.class));
        }
        public Soul(Trip trip) {
            setName(trip.getName());
            setDate(trip.getDate());
            setAddress(trip.getAddress());
            setGuide_id(trip.getGuide_id());
            setDescription(trip.getDescription());
            setLanguage(trip.getLanguage());
            setMax_visitors(trip.getMax_visitors());
        }
        public void setMax_visitors(Long count) {
            if (count == null)
                this.max_visitors = (long)2;
            else
                this.max_visitors = Math.max(2, count);
        }
    }
    public void installSoul(Soul soul) {
        setAddress(soul.getAddress());
        setDate(soul.getDate());
        setDescription(soul.getDescription());
        setGuide_id(soul.getGuide_id());
        setName(soul.getName());
        setLanguage(soul.getLanguage());
        setMax_visitors(soul.getMax_visitors());
    }
    public long getMax_visitors() {
        return Math.max(2, max_visitors);
    }
    public void downloadVisitors() {
        FirebaseDatabase.getInstance().getReference().child("Visitors").child(this.getTrip_id()).addValueEventListener(new ValueEventListener() {
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
    public boolean isVisitor(String user_id) {
        return getVisitors().containsKey(user_id);
    }
    public void addVisitor(String user_id, final Context context) {
        FirebaseDatabase.getInstance().getReference().child("Visitors").child(getTrip_id()).child(user_id).setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(context,"Вы записаны на экскурсию",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context,"FAIL",Toast.LENGTH_LONG).show();
                ((ExcursionViewActivity)context).setCount_participants();
            }
        });
    }
    public void delVisitor(String user_id, final Context context) {
        FirebaseDatabase.getInstance().getReference().child("Visitors").child(getTrip_id()).child(user_id).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@android.support.annotation.NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(context,"Вы отписаны на экскурсию",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context,"FAIL",Toast.LENGTH_LONG).show();
                ((ExcursionViewActivity)context).setCount_participants();
            }
        });
    }

}
