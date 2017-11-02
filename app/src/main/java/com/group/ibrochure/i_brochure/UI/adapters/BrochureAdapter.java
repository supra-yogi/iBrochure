package com.group.ibrochure.i_brochure.UI.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.group.ibrochure.i_brochure.R;
import com.group.ibrochure.i_brochure.UI.model.Brochure;

import java.util.ArrayList;

/**
 * Created by KinKin on 10/31/2017.
 */

public class BrochureAdapter extends RecyclerView.Adapter<BrochureAdapter.BrochureHolder> {

    private ArrayList<Brochure> mData;
    private Activity mActivity;

    public BrochureAdapter(ArrayList<Brochure> data, Activity activity) {
        this.mData = data;
        this.mActivity = activity;
    }

    @Override
    public BrochureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new BrochureHolder(view);
    }

    @Override
    public void onBindViewHolder(BrochureHolder holder, int position) {
        Brochure brochure = mData.get(position);
        holder.setName(brochure.getName());
        holder.setTelp(brochure.getTelp());
        holder.setAddress(brochure.getAddress());

    }

    @Override
    public int getItemCount() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    public class BrochureHolder extends RecyclerView.ViewHolder {

        ImageView BrochureImage;
        TextView Name;
        TextView Telp;
        TextView Address;

        public BrochureHolder(View itemView) {
            super(itemView);

            BrochureImage = (ImageView) itemView.findViewById(R.id.image_thumbnail);
            Name = (TextView) itemView.findViewById(R.id.brochure_name);
            Telp = (TextView) itemView.findViewById(R.id.brochure_telp);
            Address = (TextView) itemView.findViewById(R.id.brochure_address);

        }

        public void setName(String name) {
            this.Name.setText(name);
        }

        public void setTelp(String telp) {
            this.Telp.setText(telp);
        }

        public void setAddress(String address) {
            this.Address.setText(address);
        }


    }

}
