package com.pelicanus.insight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pelicanus.insight.model.Picture;

import static android.os.SystemClock.sleep;

public class TestActivity extends AppCompatActivity {
    TextView message;
    ImageView imageView;
    Button button_view;
    Button button_bitmap;
    Picture pic;
    @Override
    protected  synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        message = findViewById(R.id.textView);
        button_view = findViewById((R.id.load_view));
        button_bitmap = findViewById(R.id.load_bimap);
        imageView = findViewById(R.id.imageView);
        pic = new Picture(imageView, Picture.Type.Test);
        pic.Download("avatar_default.jpg");
        /*try {
            wait();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        sleep(5000);
        pic.LoadToImageView();
    }
    public void onClick(View view) {
        if (button_bitmap == view) {
            pic.Download("test1.jpg", true);
            pic.LoadToImageView();
        } else if(button_view == view)
            pic.LoadToImageView();
    }

}
