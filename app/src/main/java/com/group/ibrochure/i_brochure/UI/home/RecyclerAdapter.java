package com.group.ibrochure.i_brochure.UI.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.R;

import java.util.ArrayList;

/**
 * Created by KinKin on 11/4/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<Friend> arrayList = new ArrayList<>();

    RecyclerAdapter(ArrayList<Friend> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.desc.setText(arrayList.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView desc;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.brochure_name);
            desc = (TextView) itemView.findViewById(R.id.brochure_address);
        }
    }

    public void setFilter(ArrayList<Friend> friendArrayList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(friendArrayList);
        notifyDataSetChanged();
    }

}
