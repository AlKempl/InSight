package com.pelicanus.insight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.model.User;

import java.util.HashMap;

public class ExcursionViewActivity extends AppCompatActivity {

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_view);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        TextView ex_name = findViewById(R.id.view_excursion_name);
        TextView ex_description = findViewById(R.id.view_description);
        TextView ex_date = findViewById(R.id.view_date_time);
        TextView ex_address = findViewById(R.id.view_adress);
        TextView ex_language = findViewById(R.id.view_language);
        TextView ex_author = findViewById(R.id.view_author_name);
        ex_name.setText(getIntent().getExtras().getString("name"));
        ex_description.setText(getIntent().getExtras().getString("description"));
        ex_date.setText(getIntent().getExtras().getString("date"));
        ex_address.setText(getIntent().getExtras().getString("address"));
        String author_id = getIntent().getExtras().getString("guide_id");

        //ex_author.setText(getUserData(author_id).get("name"));


    }
    private HashMap<String,String> getUserData(String id){

        final HashMap<String,String> userdata=new HashMap<>();

        reference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userdata.put("name",user.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
        return  userdata;
    }


}
