package com.pelicanus.insight;

import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.User;

public class EditProfileActiity extends AppCompatActivity {
    EditText ed_name;
    EditText ed_email;
    boolean avatar_edited = false;

    private static final int MY_PASSWORD_DIALOG_ID = 4;

    DialogFragment dlg1;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_actiity);
        user = (User)DataHolder.getInstance().retrieve("CURR_USER");
        user.getAvatar().setImageView((ImageView)findViewById(R.id.user_photo));
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);


        Button btnChangePass = findViewById(R.id.change_pass);
        User curr = (User) DataHolder.getInstance().retrieve("CURR_USER");
        if (curr.getFbProvider() != "Google")
            btnChangePass.setVisibility(View.VISIBLE);
        else
            btnChangePass.setVisibility(View.GONE);

        dlg1 = new Dialog1();
    }

    public void onConfrim(View view) {
        boolean accepted = false;
        if (ed_name.getText().length()>0) {
            user.setName(ed_name.getText().toString());
            accepted = true;
        }
        if (ed_email.getText().length()>0) {
            //TODO смена почты и отправка письма(?)
            if (!user.getEmail().equals(ed_email.getText().toString())) {
                FirebaseAuth.getInstance().getCurrentUser().updateEmail(ed_email.getText().toString());
                user.setEmail(ed_email.getText().toString());
                accepted = true;
            } else {
                Toast.makeText(this, "Новый e-mail совпадает с текущим.", Toast.LENGTH_LONG).show();
            }

        }
        if (avatar_edited) {
            user.getAvatar().Upload();
            accepted = true;
            avatar_edited = false;
        }
        if (accepted) {
            user.writeUserData();
            Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_LONG).show();
        }
    }

    public void onChangePassConfirm(View view) {
        dlg1.show(getFragmentManager(), "dlg1");
    }

    public void getUserDataSerialized(View view) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        User current = (User) DataHolder.getInstance().retrieve("CURR_USER");
        Gson gson = new Gson();
        String json = gson.toJson(current);
        ClipData clip = ClipData.newPlainText("Current user data", json);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Data was copied to the clipboard", Toast.LENGTH_LONG).show();
    }

    public void setAvatar(View view) {user.getAvatar().Set(this); avatar_edited = true;}
}
