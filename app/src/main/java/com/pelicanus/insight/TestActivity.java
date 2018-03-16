package com.pelicanus.insight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pelicanus.insight.model.Picture;

public class TestActivity extends AppCompatActivity {
    TextView message;
    ImageView imageView;
    ImageButton imageButton;
    Button button;
    Picture pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        message = findViewById(R.id.textView);
        imageButton = findViewById((R.id.load_view));
        button = findViewById(R.id.load_bimap);
        imageView = findViewById(R.id.imageView);
        pic = new Picture(imageView, Picture.Type.Test, "test1.jpg");
        pic.SetDefault();
        pic.LoadToImageView();
    }
    public void onClick(View view) {
        if (button == view) {
            pic.Download();
        } else if(imageButton == view)
            pic.LoadToImageView();
    }

}
