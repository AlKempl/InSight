package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.User;

public class ExcursionViewActivity extends AppBaseActivity {

    DatabaseReference reference;
    String author_id;
    Button multi_btn;
    String user_id;
    ButtonMode buttonMode;
    TextView vis;
    long count_vis = -1;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_view);
        reference = FirebaseDatabase.getInstance().getReference();

        multi_btn =findViewById(R.id.im_in_btn);

        //TextView ex_name = findViewById(R.id.view_excursion_name);
        TextView ex_description = findViewById(R.id.view_description);
        TextView ex_date = findViewById(R.id.view_date_time);
        TextView ex_address = findViewById(R.id.view_adress);
        TextView ex_language = findViewById(R.id.view_language);
        TextView ex_author = findViewById(R.id.view_author_name);
        vis  = findViewById(R.id.view_participants);
        CollapsingToolbarLayout m_coll = findViewById(R.id.main_collapsing);

        //ex_name.setText(getIntent().getExtras().getString("name"));
        name = getIntent().getExtras().getString("name");
        m_coll.setTitle(name);
//        getActionBar().setTitle(name);
//        getSupportActionBar().setTitle(name);
        ex_description.setText(getIntent().getExtras().getString("description"));
        ex_date.setText(getIntent().getExtras().getString("date"));
        ex_address.setText(getIntent().getExtras().getString("address"));
        ex_language.setText(getIntent().getExtras().getString("language"));
        author_id = getIntent().getExtras().getString("guide_id");
        final String trip_id = getIntent().getStringExtra("Trip_id");

        User usr = (User) DataHolder.getInstance().retrieve("CURR_USER");
        user_id = usr.getId();


        Toast.makeText(this,R.string.Author_name_not_found,Toast.LENGTH_LONG).show();
        new Picture((ImageView) findViewById(R.id.view_author_image), Picture.Type.User_avatar, author_id).Download();
        new Picture((ImageView) findViewById(R.id.view_trip_image), Picture.Type.Trip_avatar, trip_id).Download();

        buttonMode=ButtonMode.Im_in;


        if(user_id.contentEquals(author_id)) {
            multi_btn.setText("Edit");
            buttonMode=ButtonMode.Edit;

        }
        else{
            reference.child("Visitors").child(trip_id).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(user_id)) {
                            multi_btn.setText("I'm out");
                            buttonMode = ButtonMode.Im_out;
                    }
                    count_vis = dataSnapshot.getChildrenCount();
                    vis.setText(count_vis+"/10");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    vis.setText("1/10");
                }
            });

        }

        multi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (buttonMode){
                    case Im_in:{
                        if(buttonMode==ButtonMode.Im_in){
                            reference.child("Visitors").child(trip_id).child(user_id).setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                        Toast.makeText(getApplicationContext(),"Вы записаны на экскурсию",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_LONG).show();
                                }
                            });
                            buttonMode=ButtonMode.Im_out;
                            multi_btn.setText("I'm out");
                    }

                    }
                    break;
                    case Im_out:{
                        reference.child("Visitors").child(trip_id).child(user_id).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    Toast.makeText(getApplicationContext(),"Вы отписаны на экскурсию",Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_LONG).show();
                            }
                        });
                        multi_btn.setText("I'm in");
                        buttonMode=ButtonMode.Im_in;
                    }
                    break;
                    case Edit:{
                        //TODO Переход на активити редактирования экскурсии
                    }
                    break;


            }}
        });



    }

    public void OpenProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("User_id", author_id);
        startActivity(intent);
    }

    public void OpenEdit(View view) {
//        Intent intent = new Intent(this, EditExcursionActivity.class);
//
//        intent.putExtra("name", name);
//        intent.putExtra("date", date);
//        intent.putExtra("address", adress);
//        intent.putExtra("description", description);
//        intent.putExtra("guide_id", author_id);
//        intent.putExtra("Trip_id", trip_id);
//        intent.putExtra("language", language);
//        startActivity(intent);
    }


    enum ButtonMode {Im_in, Im_out, Edit}
}
