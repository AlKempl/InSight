package com.pelicanus.insight;

import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.User;

public class EditProfileActivity extends AppBaseActivity {
    EditText ed_name;
    EditText ed_email;
    boolean avatar_edited = false;

    DialogFragment dlg1;

    User user;
    TextView ch_em_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_activity);
        user = (User) DataHolder.getInstance().retrieve("CURR_USER");
        user.getAvatar().setImageView((ImageView) findViewById(R.id.user_photo));
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ch_em_label = findViewById(R.id.change_email_label);


        Button btnChangePass = findViewById(R.id.change_pass);


        //Log.i("CURR_INFO", user.getFbProvider());

        if (!user.getFbProvider().trim().equals("google.com")) {
            btnChangePass.setVisibility(View.VISIBLE);
            ed_email.setVisibility(View.VISIBLE);
            ch_em_label.setVisibility(View.VISIBLE);
        } else {
            btnChangePass.setVisibility(View.GONE);
            ed_email.setVisibility(View.GONE);
            ch_em_label.setVisibility(View.GONE);
        }


        dlg1 = new Dialog1();
    }


    public void onConfrim(final View view) {
        boolean accepted = false;
        if (ed_name.getText().length() > 0) {
            user.setName(ed_name.getText().toString());
            ed_name.setText("");
            accepted = true;
        }

        if (ed_email.getText().length() > 0) {
            if (!user.getEmail().equals(ed_email.getText().toString())) {
                FirebaseAuth.getInstance().getCurrentUser().updateEmail(ed_email.getText().toString());
                user.setEmail(ed_email.getText().toString());
                FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "E-mail успешно изменен. Вам отправлено письмо для подтверждения адреса.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
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
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        String json = gson.toJson(current);
        Log.d("CURR_USER_DATA", json);
        ClipData clip = ClipData.newPlainText("Current user data", json);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Data was copied to the clipboard", Toast.LENGTH_LONG).show();
    }

    public void setAvatar(View view) {
        user.getAvatar().Set(this);
        avatar_edited = true;
    }
    protected void onResume() {
        super.onResume();
        user.getAvatar().setImageView((ImageView) findViewById(R.id.user_photo));
    }
}
