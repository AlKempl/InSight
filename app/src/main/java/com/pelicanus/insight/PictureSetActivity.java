package com.pelicanus.insight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;

import java.io.IOException;

public class PictureSetActivity extends AppCompatActivity {
    Picture picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_set);
        picture = (Picture) DataHolder.getInstance().retrieve("PICTURE_SET");
        Intent intent = new Intent(Intent.ACTION_PICK).setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent ReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, ReturnedIntent);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    try {
                        picture.Set(ReturnedIntent.getData(), this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
        PictureSetActivity.this.finish();
    }
}
