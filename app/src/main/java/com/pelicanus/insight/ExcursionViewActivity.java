package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.pelicanus.insight.model.Picture;

public class ExcursionViewActivity extends AppBaseActivity {

    DatabaseReference reference;
    String author_id;
    Button multi_btn;
    String userid;
    ButtonMode buttonMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_view);
        reference = FirebaseDatabase.getInstance().getReference();

        multi_btn =findViewById(R.id.im_in_btn);

        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();

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




        Toast.makeText(this,R.string.Author_name_not_found,Toast.LENGTH_LONG).show();
        new Picture((ImageView) findViewById(R.id.view_author_image), Picture.Type.User_avatar, author_id).Download();
        new Picture((ImageView) findViewById(R.id.view_trip_image), Picture.Type.Trip_avatar, trip_id).Download();

        buttonMode=ButtonMode.Im_in;


        if(userid==author_id) {
            multi_btn.setText("Edit");
            buttonMode=ButtonMode.Edit;

        }
        else{
            reference.child("Visitors").child(trip_id).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if(dataSnapshot.hasChild(userid)) {
                        multi_btn.setText("I'm out");
                        buttonMode = ButtonMode.Im_out;
                    }
                    reference.child("Visitors").child(trip_id).removeEventListener(this);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

        multi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (buttonMode){
                    case Im_in:{
                        if(buttonMode==ButtonMode.Im_in){
                            reference.child("Visitors").child(trip_id).child(userid).setValue(false).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                        Toast.makeText(getApplicationContext(),"Вы записаны на экскурсию",Toast.LENGTH_LONG).show();
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
                        reference.child("Visitors").child(trip_id).child(userid).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    Toast.makeText(getApplicationContext(),"Вы отписаны на экскурсию",Toast.LENGTH_LONG).show();
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
