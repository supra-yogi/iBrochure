package com.group.ibrochure.i_brochure.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.Infrastructure.ConverterImage;
import com.group.ibrochure.i_brochure.Infrastructure.ListBrochureAPI;
import com.group.ibrochure.i_brochure.Infrastructure.ResponseCallBack;
import com.group.ibrochure.i_brochure.R;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by KinKin on 11/12/2017.
 */

public class MyBrochureAdapter extends RecyclerView.Adapter<MyBrochureAdapter.MyHolder> {
    private Context context;
    private ArrayList<ListBrochure> listBrochureArrayList;
    private ListBrochureAPI repo;

    /*
    CONSTRUCTOR
     */
    public MyBrochureAdapter(Context context, ArrayList<ListBrochure> listBrochureArrayList) {
        this.context = context;
        this.listBrochureArrayList = listBrochureArrayList;
        repo = new ListBrochureAPI(context);
    }

    //INITIALIZE VIEW HOLDER
    @Override
    public MyBrochureAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_brochure_model, parent, false);
        return new MyBrochureAdapter.MyHolder(v);
    }

    //BIND DATA
    @Override
    public void onBindViewHolder(MyBrochureAdapter.MyHolder holder, int position) {
        final int id = listBrochureArrayList.get(position).getId();
        holder.title.setText(listBrochureArrayList.get(position).getTitle());
        holder.telephone.setText(listBrochureArrayList.get(position).getTelephone());
        holder.user.setText(listBrochureArrayList.get(position).getUserAccount().getName());

        String imageByteFront = listBrochureArrayList.get(position).getPictureFront();
        String imageByteBack = listBrochureArrayList.get(position).getPictureBack();
//        String imageAvatar = listBrochureArrayList.get(position).getUserAccount().getPicture();

        if (!imageByteFront.equals("")) {
            Bitmap pictureFront = ConverterImage.decodeBase64(imageByteFront);
            holder.pictureFront.setImageBitmap(pictureFront);
        }

        if (!imageByteBack.equals("")) {
            Bitmap pictureBack = ConverterImage.decodeBase64(imageByteBack);
            holder.pictureBack.setImageBitmap(pictureBack);
        }

//        if (!imageAvatar.equals("")) {
//            Bitmap avatar = ConverterImage.decodeBase64(imageAvatar);
//            holder.avatar.setImageBitmap(avatar);
//        }

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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editBrochure = new Intent(context, EditBrochureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", id);
                editBrochure.putExtras(bundle);
                context.startActivity(editBrochure);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repo.Delete(new ResponseCallBack() {
                    @Override
                    public void onResponse(JSONArray response) {
                    }

                    @Override
                    public void onResponse(String response) {
                        Intent myBrochure = new Intent(context, ListMyBrochureActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isUpdated", true);
                        myBrochure.putExtras(bundle);
                        context.startActivity(myBrochure);
                        ListMyBrochureActivity.getInstance().finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                }, id);
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
        ImageView pictureFront;
        ImageView pictureBack;
        TextView user;
        CardView brochure;
        ImageView avatar;
        ImageButton edit;
        ImageButton delete;

        public MyHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.title);
            this.user = (TextView) itemView.findViewById(R.id.user);
            this.telephone = (TextView) itemView.findViewById(R.id.telephone);
            this.pictureFront = (ImageView) itemView.findViewById(R.id.pictureFront);
            this.pictureBack = (ImageView) itemView.findViewById(R.id.pictureBack);
            this.brochure = (CardView) itemView.findViewById(R.id.cardViewModelBrochure);
            this.avatar = (ImageView) itemView.findViewById(R.id.avatar2);
            this.edit = (ImageButton) itemView.findViewById(R.id.edit);
            this.delete = (ImageButton) itemView.findViewById(R.id.delete);
        }
    }
}
