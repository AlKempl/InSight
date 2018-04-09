package com.pelicanus.insight;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;
import com.pelicanus.insight.model.TripsList;
import com.pelicanus.insight.service.TripAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class TripList extends AppBaseActivity {


    private RecyclerView recyclerView;
    private TripsList tripsList;
    private Spinner languageField;
    private EditText tagField;
    private String hashtag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);
        recyclerView = findViewById(R.id.trip_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        languageField = findViewById(R.id.trip_list_spinner);
        tagField = findViewById(R.id.tag);

        String language = getIntent().getStringExtra("language");
        hashtag = getIntent().getStringExtra("hashtag");

        tagField.setText(hashtag.equals("all")?"":hashtag);

        String date = getCurrentDate();
        tripsList = new TripsList(language,(hashtag==null?date:hashtag).replace('.','_') , this, recyclerView);
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat= new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(new Date());
    }

    protected void onDestroy() {
        super.onDestroy();
        tripsList.removeReader();
    }
    public void findByTag(View view) {

            String edittag = tagField.getText().toString();
            if(edittag!=null && edittag.toLowerCase().trim().matches("\\w+")) {
                tripsList.changeTag(getLanguage(), edittag.toLowerCase());
            }



    }
    private String getLanguage() {
        return languageField.getSelectedItem().toString();
    }
}

