package com.pelicanus.insight.service;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pelicanus.insight.ExcursionViewActivity;
import com.pelicanus.insight.R;
import com.pelicanus.insight.TripList;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;

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

       final Trip trp = list.get(position);
        holder.exc_date.setText(trp.getDate());
        holder.exc_description.setText(trp.getDescription());
        holder.exc_name.setText(trp.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(c,ExcursionViewActivity.class);

                intent.putExtra("name",trp.getName());
                intent.putExtra("date",trp.getDate());
                intent.putExtra("address",trp.getAddress());
                intent.putExtra("description",trp.getDescription());
                intent.putExtra("guide_id",trp.getGuide_id());
                intent.putExtra("Trip_id",trp.getTrip_id());
                intent.putExtra("language",trp.getLanguage());
                c.startActivity(intent);
            }
        });


        //new Picture(holder.exc_image,Picture.Type.Trip_avatar, trp.getTrip_id()).Download();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder{

        TextView  exc_name,exc_date,exc_description;
        ImageView exc_image;

        public TripViewHolder(View itemView) {
            super(itemView);
             exc_name = (TextView) itemView.findViewById(R.id.exc_name);
             exc_date = (TextView) itemView.findViewById(R.id.exc_date);
             exc_description= itemView.findViewById(R.id.exc_description);
             exc_image = itemView.findViewById(R.id.exc_image);

        }
    }
}
