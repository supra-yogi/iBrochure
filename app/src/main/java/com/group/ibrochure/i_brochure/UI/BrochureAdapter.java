package com.group.ibrochure.i_brochure.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.R;

import java.util.ArrayList;

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
        holder.title.setText(listBrochureArrayList.get(position).getTitle());
        holder.telephone.setText(listBrochureArrayList.get(position).getTelephone());
        holder.user.setText(listBrochureArrayList.get(position).getUserAccount().getName());

        String imageByteFront = listBrochureArrayList.get(position).getPictureFront();
        String imageByteBack = listBrochureArrayList.get(position).getPictureBack();

        if (!imageByteFront.equals("")) {
            byte[] decodedString = Base64.decode(imageByteFront, Base64.DEFAULT);
            Bitmap pictureFront = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.pictureFront.setImageBitmap(pictureFront);
        } else {
            holder.pictureFront.setBackgroundResource(R.drawable.brochure);
        }

        if (!imageByteBack.equals("")) {
            byte[] decodedString = Base64.decode(imageByteBack, Base64.DEFAULT);
            Bitmap pictureBack = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.pictureBack.setImageBitmap(pictureBack);
        }
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
        ImageView pictureFront;
        ImageView pictureBack;
        TextView user;

        public MyHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.title);
            this.user = (TextView) itemView.findViewById(R.id.user);
            this.telephone = (TextView) itemView.findViewById(R.id.telephone);
            this.pictureFront = (ImageView) itemView.findViewById(R.id.pictureFront);
            this.pictureBack = (ImageView) itemView.findViewById(R.id.pictureBack);
        }
    }
}
