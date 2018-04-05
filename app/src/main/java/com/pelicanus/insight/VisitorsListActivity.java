package com.pelicanus.insight;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;
import com.pelicanus.insight.model.User;
import com.pelicanus.insight.service.UserAdapter;

import java.util.ArrayList;

public class VisitorsListActivity extends AppBaseActivity {
    ArrayList<User> listofUsers;
    private DatabaseReference myRef;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    String trip_id;
    Trip trip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitors_list);

        listofUsers = new ArrayList<>();
        trip = (Trip)DataHolder.getInstance().retrieve("REQUESTED_TRIP");
        trip_id = trip.getTrip_id();
        myRef = FirebaseDatabase.getInstance().getReference().child("Visitors").child(trip_id);

        recyclerView = findViewById(R.id.visitor_list);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        adapter = new UserAdapter(this, listofUsers);
        updateList();
        recyclerView.setAdapter(adapter);

    }


    private void updateList(){
        for (String v:trip.getVisitors().keySet()) {
            User u = new User(v);
            //u.readUserDataWithID();
            listofUsers.add(u);
        }
/*        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                user.setId(dataSnapshot.getKey());
                    //    TODO user.avatar = new Picture(Picture.Type.User_avatar, dataSnapshot.getKey()); ????????????????????
                listofUsers.add(user);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                user.setId(dataSnapshot.getKey());
                //user.avatar = new Picture(Picture.Type.User_avatar, dataSnapshot.getKey()); ?????????????????????????
                int index =getItemIndex(user);
                listofUsers.set(index, user);
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                user.setId(dataSnapshot.getKey());
                int index =getItemIndex(user);
                listofUsers.remove(index);
                adapter.notifyItemRemoved(index);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
    private int getItemIndex(User user){
        int index =-1;
        for(int i=0;i<listofUsers.size();i++){
            if(listofUsers.get(i).getId().equals(user.getId())){
                index =i;
                break;
            }
        }
        return index;
    }
}
