package com.pelicanus.insight;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.User;

import java.io.IOException;

public class EditProfileActiity extends AppCompatActivity {
    Picture avatar;
    EditText ed_name;
    EditText ed_email;
    boolean avatar_edited = false;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_actiity);
        String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user = (User)DataHolder.getInstance().retrieve("CURR_USER");
        avatar = new Picture((ImageView)findViewById(R.id.user_photo), Picture.Type.User_avatar, user_id);
        avatar.Download();
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
    }
    public void onConfrim(View view) {
        boolean accepted = false;
        if (ed_name.getText().length()>0) {
            user.setName(ed_name.getText().toString());
            accepted = true;
        }
        if (ed_email.getText().length()>0) {
            //TODO смена почты и отправка письма(?)
            accepted = true;
        }
        if (avatar_edited) {
            avatar.Upload();
            accepted = true;
            avatar_edited = false;
        }
        if (accepted) {
            user.writeUserData();
            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_LONG).show();
        }
    }
    public void setAvatar(View view) {avatar.Set(this); avatar_edited = true;}
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
