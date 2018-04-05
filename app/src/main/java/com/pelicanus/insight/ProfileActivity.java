package com.pelicanus.insight;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.User;

public class ProfileActivity extends AppBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loadHeaderUserInfo();
    }

    protected void loadHeaderUserInfo() {

        User current = (User) DataHolder.getInstance().retrieve("CURR_USER");
        TextView username_label = findViewById(R.id.user_name);
        TextView in_profile_rating_string_label = findViewById(R.id.in_profile_rating_string);
        current.setFieldName(username_label);
        current.setFieldRating(in_profile_rating_string_label);

        current.getAvatar().setImageView((ImageView) findViewById(R.id.user_photo));
    }
}