package com.pelicanus.insight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.User;

import java.util.HashMap;

public class ExcursionViewActivity extends AppCompatActivity {

    DatabaseReference reference;
    String author_id;
    HashMap<String,User> userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_view);
        reference = FirebaseDatabase.getInstance().getReference();

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
        ex_language.setText(getIntent().getExtras().getString("language"));
        author_id = getIntent().getExtras().getString("guide_id");
        final String trip_id = getIntent().getStringExtra("Trip_id");

        userdata=new HashMap<>();
        getUserData();
        if(userdata.size()==0)
            Toast.makeText(this,"fuck again",Toast.LENGTH_LONG).show();

        if(userdata.containsKey(author_id)) {
            ex_author.setText(userdata.get(author_id).getName());
        }
        else
            Toast.makeText(this,R.string.Author_name_not_found,Toast.LENGTH_LONG).show();
        new Picture((ImageView) findViewById(R.id.view_author_image), Picture.Type.User_avatar, author_id).Download();
        new Picture((ImageView) findViewById(R.id.view_trip_image), Picture.Type.Trip_avatar, trip_id).Download();


        Button im_in=findViewById(R.id.im_in);
        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        im_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("Visitors").child(trip_id).child(userid).setValue("kakashka");
            }
        });



    }
    public void getUserData(){

            reference.child("Users").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    User user = dataSnapshot.getValue(User.class);
                    String userid = dataSnapshot.getKey();
                    userdata.put(userid,user);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    User user = dataSnapshot.getValue(User.class);
                    String userid = dataSnapshot.getKey();
                    if(!userdata.containsKey(userid))
                        userdata.put(userid,user);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });
    }
    public void OpenProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("User_id", author_id);
        startActivity(intent);
    }





}
