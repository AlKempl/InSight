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

    public void setTrip_id(String id) {
        this.trip_id = id;
        avatar.setName(id);
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
