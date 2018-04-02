package com.pelicanus.insight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        String user_id = getIntent().getStringExtra("User_id");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        TextView view_name = (TextView)findViewById(R.id.user_name);
//        view_name.setText(user_id); //TODO Нужно будет заменить на имя/логин из базы
//        Picture avatar = new Picture((ImageView)findViewById(R.id.user_photo), Picture.Type.User_avatar, user_id);
//        avatar.Download();
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        //Голова шторки
//        String This_user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        View headerLayout = navigationView.getHeaderView(0);
//        new Picture((ImageView)headerLayout.findViewById(R.id.this_user_photo), Picture.Type.User_avatar, This_user_uid).Download();
//        TextView userName = headerLayout.findViewById(R.id.navdr_username_label);
//        TextView userEmail = headerLayout.findViewById(R.id.navdr_useremail_label);
//        userName.setText(This_user_uid);
//        userEmail.setText("TODO");//TODO e-mail и логин из базы НЕ ЗАБЫТЬ СДЕЛАТЬ И В MainMenuActivity
    }


    public void OpenProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("User_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        startActivity(intent);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        finish();
    }
}