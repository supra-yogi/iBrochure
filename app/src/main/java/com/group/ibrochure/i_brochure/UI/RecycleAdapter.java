package com.group.ibrochure.i_brochure.UI;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.group.ibrochure.i_brochure.Domain.Category.Category;
import com.group.ibrochure.i_brochure.Domain.Customer.Customer;
import com.group.ibrochure.i_brochure.Domain.ListBrochure.ListBrochure;
import com.group.ibrochure.i_brochure.R;

import java.util.ArrayList;

/**
 * Created by KinKin on 11/4/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    ArrayList<ListBrochure> listBrochure = new ArrayList<>();

    RecycleAdapter(ArrayList<ListBrochure> listBrochure) {
        this.listBrochure = listBrochure;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(listBrochure.get(position).getTitle());
        holder.title.setText(listBrochure.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return listBrochure.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
//        TextView customer;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.brochure_name);
            description = (TextView) itemView.findViewById(R.id.brochure_address);
//            customer = (TextView) itemView.findViewById(R.id.brochure_customer);
        }
    }

    public void setFilter(ArrayList<ListBrochure> brochureArrayList) {
        listBrochure = new ArrayList<>();
        listBrochure.addAll(brochureArrayList);
        notifyDataSetChanged();
    }

}
