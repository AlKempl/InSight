package com.pelicanus.insight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    String name;
    String description;
    String date;
    String adress;
    String language;
    String author_id;
    String trip_id;

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

        name = getIntent().getExtras().getString("name");
        ex_name.setText(name);
        description = getIntent().getExtras().getString("description");
        ex_description.setText(description);
        date = getIntent().getExtras().getString("date");
        ex_date.setText(date);
        adress = getIntent().getExtras().getString("address");
        ex_address.setText(adress);
        language = getIntent().getExtras().getString("language");
        ex_language.setText(language);
        author_id = getIntent().getExtras().getString("guide_id");
        trip_id = getIntent().getStringExtra("Trip_id");
        HashMap<String,User> users = getUserData();
        if(users.containsKey(author_id)) {
            ex_author.setText(users.get(author_id).getName());
        }
        else
            Toast.makeText(this,R.string.Author_name_not_found,Toast.LENGTH_LONG).show();
        new Picture((ImageView) findViewById(R.id.view_author_image), Picture.Type.User_avatar, author_id).Download();
        new Picture((ImageView) findViewById(R.id.view_trip_image), Picture.Type.Trip_avatar, trip_id).Download();


    }
    private HashMap<String,User> getUserData(){

        final HashMap<String,User> userdata=new HashMap<>();

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    userdata.put(dataSnapshot.getKey(),user);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        return  userdata;
    }

    public void OpenEdit(View view)
    {
        Intent intent = new Intent(this,EditExcursionActivity.class);

        intent.putExtra("name",name);
        intent.putExtra("date",date);
        intent.putExtra("address",adress);
        intent.putExtra("description",description);
        intent.putExtra("guide_id",author_id);
        intent.putExtra("Trip_id",trip_id);
        intent.putExtra("language",language);
        startActivity(intent);
    }
}
