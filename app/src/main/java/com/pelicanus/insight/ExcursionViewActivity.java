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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;
import com.pelicanus.insight.model.User;

public class ExcursionViewActivity extends AppBaseActivity {

    DatabaseReference reference;
    Button multi_btn;
    String user_id;
    ButtonMode buttonMode;
    TextView participants;
    long count_vis = -1;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_view);
        reference = FirebaseDatabase.getInstance().getReference();
        trip = (Trip)DataHolder.getInstance().retrieve("REQUESTED_TRIP");
        multi_btn =findViewById(R.id.im_in_btn);

        //TextView ex_name = findViewById(R.id.view_excursion_name);
        TextView ex_description = findViewById(R.id.view_description);
        TextView ex_date = findViewById(R.id.view_date_time);
        TextView ex_address = findViewById(R.id.view_adress);
        TextView ex_language = findViewById(R.id.view_language);
        TextView ex_author = findViewById(R.id.view_author_name);
        participants = findViewById(R.id.view_participants);
        CollapsingToolbarLayout m_coll = findViewById(R.id.main_collapsing);

        //ex_name.setText(getIntent().getExtras().getString("name"));
        m_coll.setTitle(trip.getName());
//        getActionBar().setTitle(name);
//        getSupportActionBar().setTitle(name);
        ex_description.setText(trip.getDescription());
        ex_date.setText(trip.getDate());
        ex_address.setText(trip.getAddress());
        ex_language.setText(trip.getLanguage());

        User usr = (User) DataHolder.getInstance().retrieve("CURR_USER");
        user_id = usr.getId();


        Toast.makeText(this,R.string.Author_name_not_found,Toast.LENGTH_LONG).show();
        new Picture((ImageView) findViewById(R.id.view_author_image), Picture.Type.User_avatar, trip.getGuide_id()).Download();
        trip.avatar.setImageView((ImageView)findViewById(R.id.view_trip_image));
        trip.avatar.LoadToImageView();
        setCount_participants();
        buttonMode=ButtonMode.Im_in;


        if(user_id.contentEquals(trip.getGuide_id())) {
            multi_btn.setText("Edit");
            buttonMode=ButtonMode.Edit;

        }
        else{
            if(trip.isVisitor(user_id)) {
                multi_btn.setText("I'm out");
                buttonMode = ButtonMode.Im_out;
            }
        }

        multi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (buttonMode){
                    case Im_in:{
                        if(buttonMode==ButtonMode.Im_in){
                            trip.addVisitor(user_id, ExcursionViewActivity.this);
                            buttonMode=ButtonMode.Im_out;
                            multi_btn.setText("I'm out");
                    }

                    }
                    break;
                    case Im_out:{
                        trip.delVisitor(user_id, ExcursionViewActivity.this);
                        multi_btn.setText("I'm in");
                        buttonMode=ButtonMode.Im_in;
                    }
                    break;
                    case Edit:{
                        OpenEdit();
                    }
                    break;


            }}
        });



    }
    public void onDestroy () {
    // тут удалить данные из хранилища, например
        DataHolder.getInstance().remove("REQUESTED_TRIP");
        super.onDestroy();
    }
    public void OpenProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("User_id", trip.getGuide_id());
        startActivity(intent);
    }

    public void OpenEdit() {
        Intent intent = new Intent(this, EditExcursionActivity.class);
        startActivity(intent);
    }
    public void setCount_participants() {
        participants.setText(trip.getVisitors().size()+"/"+trip.getMax_visitors()+" participants");
    }
    public void OpenVisitorsList(View view) {
        Intent intent = new Intent(this, VisitorsListActivity.class);
        //intent.putExtra("trip_id", )
        startActivity(intent);
    }
    enum ButtonMode {Im_in, Im_out, Edit}
}
