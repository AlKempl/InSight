package com.pelicanus.insight.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.pelicanus.insight.model.Trip;

import java.util.ArrayList;

/**
 * Created by Luckynatrium on 09.03.2018.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Trip> trips;

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

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null)
            view= LayoutInflater.from(c).inflate(android.support.v4.R.layout.notification_action,viewGroup,false);//Поменять android.support.v4.R.layout.notification_action на свое

        //Здесь должна быть связка переменных и TextView, где будет информация
        //TextView name =view.findViewById(R.id.<id поля>);

        final Trip t=this.getItem(position);
        //Здесь инициализируем переменные полями Tripa

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Здесь должен быть переход на активити просмотра экскурсии
                Toast.makeText(c,t.getName(),Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }
}
