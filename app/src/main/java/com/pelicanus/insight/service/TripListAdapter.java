package com.pelicanus.insight.service;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pelicanus.insight.R;
import com.pelicanus.insight.model.Trip;

import java.util.ArrayList;

/**
 * Created by acer on 09.03.2018.
 */

public class TripListAdapter extends ArrayAdapter<Trip> {

    private static final String TAG = "TripListAdapter";

    private Context mContext;

    int mResourse;

    public TripListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Trip> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Excursion information
        String e_name = getItem(position).getName();
        String e_date = getItem(position).getData().toString();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResourse, parent, false);

        TextView exc_name = (TextView) convertView.findViewById(R.id.exc_name);
        TextView exc_date = (TextView) convertView.findViewById(R.id.exc_date);

        //Putting data on textView
        exc_name.setText(e_name);
        exc_date.setText(e_date);

        return convertView;
    }
}
