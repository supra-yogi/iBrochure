package com.group.ibrochure.i_brochure.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Domain.UserAccount.UserAccount;
import com.group.ibrochure.i_brochure.Infrastructure.CategoryAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ConverterImage;
import com.group.ibrochure.i_brochure.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Yogi on 07/11/2017.
 */

public class BrochureAdapter extends RecyclerView.Adapter<BrochureAdapter.MyHolder> {
    private Context context;
    private ArrayList<ListBrochure> listBrochureArrayList;


    /*
    CONSTRUCTOR
     */
    public BrochureAdapter(Context context, ArrayList<ListBrochure> listBrochureArrayList) {
        this.context = context;
        this.listBrochureArrayList = listBrochureArrayList;
    }

    //INITIALIZE VIEW HOLDER
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_brochure_model, parent, false);
        return new MyHolder(v);
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final int id = listBrochureArrayList.get(position).getId();
        holder.title.setText(listBrochureArrayList.get(position).getTitle());
        holder.telephone.setText(listBrochureArrayList.get(position).getTelephone());
        holder.user.setText(listBrochureArrayList.get(position).getUserAccount().getName());
        holder.category.setText(listBrochureArrayList.get(position).getCategory().getName());

        String imageByteFront = listBrochureArrayList.get(position).getPictureFront();
        String imageByteBack = listBrochureArrayList.get(position).getPictureBack();

        ArrayList<Bitmap> images = new ArrayList<>();
        if (!imageByteFront.equals("")) {
            Bitmap pictureFront = ConverterImage.decodeBase64(imageByteFront);
            images.add(pictureFront);
            pictureFront = null;
            System.gc();
        }

        if (!imageByteBack.equals("")) {
            Bitmap pictureBack = ConverterImage.decodeBase64(imageByteBack);
            images.add(pictureBack);
            pictureBack = null;
            System.gc();
        }

        //Add Slider Image
        if (images.size() != 0) {
            ViewPagerAdapterSlider viewPagerAdapter = new ViewPagerAdapterSlider(context, images);
            holder.viewPager.setAdapter(viewPagerAdapter);

            final int dotscount = viewPagerAdapter.getCount();
            if (holder.sliderDotspanel.getChildCount() != dotscount) {
                final ImageView[] dots = new ImageView[dotscount];
                for (int i = 0; i < dotscount; i++) {
                    dots[i] = new ImageView(context);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    holder.sliderDotspanel.addView(dots[i], params);
                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));

                holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        for (int i = 0; i < dotscount; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.nonactive_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(context, R.drawable.active_dot));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }
        }

        holder.brochure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailBrochure = new Intent(context, DetailBrochureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", id);
                detailBrochure.putExtras(bundle);
                context.startActivity(detailBrochure);
            }
        });
    }

    /*
    TOTAL ITEMS
     */
    @Override
    public int getItemCount() {
        return listBrochureArrayList.size();
    }

    /*
    ADD DATA TO ADAPTER
     */
    public void add(ListBrochure listBrochure) {
        listBrochureArrayList.add(listBrochure);
        notifyDataSetChanged();
    }

    /*
    CLEAR DATA FROM ADAPTER
     */
    public void clear() {
        listBrochureArrayList.clear();
        notifyDataSetChanged();
    }

    /*
    VIEW HOLDER CLASS
     */
    class MyHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView telephone;
        TextView user;
        TextView category;
        CardView brochure;
        ViewPager viewPager;
        LinearLayout sliderDotspanel;

        public MyHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.title);
            this.user = (TextView) itemView.findViewById(R.id.user);
            this.telephone = (TextView) itemView.findViewById(R.id.telephone);
            this.brochure = (CardView) itemView.findViewById(R.id.cardViewModelBrochure);
            this.viewPager = (ViewPager) itemView.findViewById(R.id.viewPager);
            this.sliderDotspanel = (LinearLayout) itemView.findViewById(R.id.SliderDots);
            this.category = (TextView) itemView.findViewById(R.id.cat);
        }
    }
}
