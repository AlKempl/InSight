package com.pelicanus.insight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button buttonSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSkip = findViewById(R.id.buttonSkip);
    }
    @Override
    public void onClick(View view) {
            startActivity(new Intent(this, EmailPassActivityLogin.class));
    }
}
