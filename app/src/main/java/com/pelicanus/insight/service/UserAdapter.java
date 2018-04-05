package com.pelicanus.insight.service;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pelicanus.insight.ProfileActivity;
import com.pelicanus.insight.R;
import com.pelicanus.insight.VisitorsListActivity;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>  {

    private List<User> list;
    private Context c;

    public UserAdapter(Context c,List<User> list) {
        this.list = list;
        this.c=c;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserAdapter.UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.visitors_item,parent,false));
    }

    @Override
    public void onBindViewHolder(UserAdapter.UserViewHolder holder, int position) {

        final User user = list.get(position);
        user.setFieldName(holder.vis_name);
        user.setFieldEmail(holder.vis_email);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataHolder.getInstance().save("PROFILE_USER", user);
                Intent intent = new Intent(c, ProfileActivity.class);
                c.startActivity(intent);
            }
        });

        user.getAvatar().setImageView(holder.vis_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView vis_name, vis_email;
        ImageView vis_image;
        public UserViewHolder(View itemView) {
            super(itemView);
            vis_name = itemView.findViewById(R.id.vis_name);
            vis_email = itemView.findViewById(R.id.vis_email);
            vis_image = itemView.findViewById(R.id.vis_image);
        }
    }
}
