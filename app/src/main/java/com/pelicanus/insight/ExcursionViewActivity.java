package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Trip;
import com.pelicanus.insight.model.User;
import com.volokh.danylo.hashtaghelper.HashTagHelper;


public class ExcursionViewActivity extends AppBaseActivity {

    DatabaseReference reference;
    String user_id;
    Trip trip;
    User guide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_view);
        reference = FirebaseDatabase.getInstance().getReference();
        trip = (Trip)DataHolder.getInstance().retrieve("REQUESTED_TRIP");
        CollapsingToolbarLayout m_coll = findViewById(R.id.main_collapsing);
        m_coll.setTitle(trip.getName());
        trip.setViewFields(
                null,
                (TextView) findViewById(R.id.view_adress),
                (TextView) findViewById(R.id.view_description),
                (TextView) findViewById(R.id.view_participants),
                (TextView) findViewById(R.id.view_date_time),
                (TextView) findViewById(R.id.view_language),
                (ImageView)findViewById(R.id.view_trip_image)
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
        trip.setTripButton(this, (Button)findViewById(R.id.im_in_btn), user_id);


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

    public void OpenVisitorsList(View view) {
        Intent intent = new Intent(this, VisitorsListActivity.class);
        startActivity(intent);
    }
}
