package com.group.ibrochure.i_brochure.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.R;

import java.util.ArrayList;

/**
 * Created by Yogi on 13/11/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Bitmap> images;// = {R.drawable.slide1, R.drawable.slide2, R.drawable.slide3};

    public ViewPagerAdapter(Context context, ArrayList<Bitmap> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageBitmap(images.get(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    Toast.makeText(context, "Slide 1 Clicked", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
