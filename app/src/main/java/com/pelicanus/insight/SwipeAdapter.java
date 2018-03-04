package com.pelicanus.insight;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pelicanus.insight.R;

/**
 * Created by acer on 24.02.2018.
 */

public class SwipeAdapter extends PagerAdapter {

    private int [] images = {R.drawable.main_menu_excursions_search, R.drawable.main_menu_excursions_add};
    private java.lang.String [] headers = {"Explore", "Share"};
    private Context context;
    private LayoutInflater layoutInflater;

    public SwipeAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.menu_main_swipe_element, container, false);

        ImageView imageView = view.findViewById(R.id.swipe_icon);
        TextView textView1 = view.findViewById(R.id.swipe_header);
        TextView textView2 = view.findViewById(R.id.swipe_description);

        imageView.setImageResource(images[position]);
        textView1.setText(headers[position]);
        //textView2.setText();

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
