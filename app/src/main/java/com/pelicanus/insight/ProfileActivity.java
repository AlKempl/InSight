package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    //private TextViewUsername textViewStatusInfo;
//    private Button buttonLogout;
//    private TextView textViewUsername;
//    private ImageView imageView;
//    private TextView textViewStatusInfo;
//    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if (firebaseAuth.getCurrentUser() == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        NavigationView navigation = findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.dm_logout:
                        logOut();
                        break;
                    case R.id.dm_settings:
                        openSettings();
                        break;
                    case R.id.dm_my_exc:
                        openTripList();
                        break;
                    case R.id.dm_create_trip:
                        openCreateTrip();
                        break;
                }
                return false;
            }
        });
    }


    public void logOut() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
    public void openCreateTrip(){startActivity(new Intent(this,CreateTrip.class));}
    public void openTripList(){startActivity(new Intent(this,TripList.class));}
    public void openSettings(){
        startActivity(new Intent(this, SettingsActivity.class));
    }
}