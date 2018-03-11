package com.pelicanus.insight.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pelicanus.insight.R;
import com.pelicanus.insight.TripList;
import com.pelicanus.insight.model.Picture;
import com.pelicanus.insight.model.Trip;

import java.util.ArrayList;

/**
 * Created by acer on 09.03.2018.
 */

public class TripListAdapter extends BaseAdapter {

    //private static final String TAG = "TripListAdapter";
    Context c;
    ArrayList<Trip> trips;

    public TripListAdapter(Context c, ArrayList<Trip> trips) {
        this.c=c;
        this.trips=trips;
    }


    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public Trip getItem(int position) {
        return trips.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView = LayoutInflater.from(c).inflate(R.layout.list_item,parent,false);

        TextView exc_name = (TextView) convertView.findViewById(R.id.exc_name);
        TextView exc_date = (TextView) convertView.findViewById(R.id.exc_date);
        TextView exc_description= convertView.findViewById(R.id.exc_description);

        //Excursion information
        String e_name = getItem(position).getName();
        String e_date = getItem(position).getDate();
        String e_description =getItem(position).getDescription();

        //Putting data on textView
        exc_name.setText(e_name);
        exc_date.setText(e_date);
        exc_description.setText(e_description);

        //new Picture((ImageView) convertView.findViewById(R.id.exc_image), Picture.Type.Trip_avatar, getItem(position).getTrip_id()).Download();

        return convertView;
    }
}
