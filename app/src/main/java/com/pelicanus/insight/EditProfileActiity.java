package com.pelicanus.insight;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.pelicanus.insight.model.Picture;

import java.io.IOException;

public class EditProfileActiity extends AppCompatActivity {
    Picture avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_actiity);
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        avatar = new Picture((ImageView)findViewById(R.id.user_photo), Picture.Type.User_avatar, user_id);
        avatar.Download();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent ReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, ReturnedIntent);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        avatar.Set(ReturnedIntent.getData(), this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
