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
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;
import com.pelicanus.insight.model.User;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import static com.pelicanus.insight.ExcursionViewActivity.ButtonMode.Im_out;

public class ExcursionViewActivity extends AppBaseActivity {

    DatabaseReference reference;
    Button multi_btn;
    String user_id;
    ButtonMode buttonMode;
    Trip trip;
    User guide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_view);
        reference = FirebaseDatabase.getInstance().getReference();
        trip = (Trip)DataHolder.getInstance().retrieve("REQUESTED_TRIP");
        multi_btn =findViewById(R.id.im_in_btn);

        CollapsingToolbarLayout m_coll = findViewById(R.id.main_collapsing);
        m_coll.setTitle(trip.getName());
        trip.setViewFields(
                null,
                (TextView) findViewById(R.id.view_adress),
                (TextView) findViewById(R.id.view_description),
                (TextView) findViewById(R.id.view_participants),
                (TextView) findViewById(R.id.view_date_time),
                (TextView) findViewById(R.id.view_language)
                );

        TextView ex_author = findViewById(R.id.view_author_name);
        HashTagHelper hashTagHelper = HashTagHelper.Creator.create(R.color.colorPrimaryDark, new HashTagHelper.OnHashTagClickListener() {
            @Override
            public void onHashTagClicked(String hashTag) {
                Toast.makeText(getApplicationContext(), hashTag,Toast.LENGTH_LONG).show();//TODO Перенаправить на активити списка экскурсии по этому хештегу

            }
        });
//        hashTagHelper.handle(ex_description);
        User usr = (User) DataHolder.getInstance().retrieve("CURR_USER");
        user_id = usr.getId();
        guide = new User(trip.getGuide_id());
        guide.getAvatar().setImageView((ImageView) findViewById(R.id.view_author_image));
        guide.setFieldName(ex_author);
        trip.getAvatar().setImageView((ImageView)findViewById(R.id.view_trip_image));
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
        DataHolder.getInstance().save("PROFILE_USER", guide);
        startActivity(intent);
    }

    public void OpenEdit() {
        Intent intent = new Intent(this, CreateTrip.class);
        startActivity(intent);
    }

    public void OpenVisitorsList(View view) {
        Intent intent = new Intent(this, VisitorsListActivity.class);
        startActivity(intent);
    }
    enum ButtonMode {Im_in, Im_out, Edit}
}
