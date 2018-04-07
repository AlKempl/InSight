package com.pelicanus.insight.service;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pelicanus.insight.ExcursionViewActivity;
import com.pelicanus.insight.R;
import com.pelicanus.insight.model.DataHolder;
import com.pelicanus.insight.model.Trip;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.util.List;



public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder>  {

    private List<Trip> list;
    private Context c;

    public TripAdapter(Context c,List<Trip> list) {
        this.list = list;
        this.c=c;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TripViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        position = list.size() - position - 1;
        final Trip trp = list.get(position);
        holder.exc_date.setText(trp.getDate());
        holder.exc_description.setText(trp.getDescription());
        holder.exc_name.setText(trp.getName());
        holder.exc_lang.setText(trp.getLanguage());
        HashTagHelper hashTagHelper = HashTagHelper.Creator.create(R.color.colorPrimary, new HashTagHelper.OnHashTagClickListener() {
            @Override
            public void onHashTagClicked(String hashTag) {
                Toast.makeText(c,"HashTag",Toast.LENGTH_LONG).show();
            }
        });
        hashTagHelper.handle(holder.exc_description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c,ExcursionViewActivity.class);
                DataHolder.getInstance().save("REQUESTED_TRIP", trp);
                c.startActivity(intent);
            }
        });

        if(trp.getAvatar()== null) {
            throw new NullPointerException("avatar у экскурсии пуст!"); //Этого вообще не должно случаться
        }
        trp.getAvatar().setImageView(holder.exc_image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder{
        TextView  exc_name,exc_date,exc_description, exc_lang;
        ImageView exc_image;
        public TripViewHolder(View itemView) {
            super(itemView);
             exc_name = itemView.findViewById(R.id.exc_name);
             exc_date = itemView.findViewById(R.id.exc_date);
             exc_description = itemView.findViewById(R.id.exc_description);
             exc_image = itemView.findViewById(R.id.exc_image);
             exc_lang = itemView.findViewById(R.id.exc_lang);
        }
    }
}
