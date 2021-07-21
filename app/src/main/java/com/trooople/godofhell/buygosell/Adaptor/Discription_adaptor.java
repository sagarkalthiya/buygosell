package com.trooople.godofhell.buygosell.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trooople.godofhell.buygosell.Model.Product_Discription_model;
import com.trooople.godofhell.buygosell.R;

import java.util.ArrayList;

public class Discription_adaptor extends RecyclerView.Adapter<Discription_adaptor.ExampleViewHolder> {
    private ArrayList<Product_Discription_model> mExampleList;
    public Context context;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView option_value,option_key;



        public ExampleViewHolder(View itemView) {
            super(itemView);
            option_key = itemView.findViewById(R.id.option_key_txt);
            option_value = itemView.findViewById(R.id.option_value_txt);

        }
    }


    public Discription_adaptor(Context context,ArrayList<Product_Discription_model> exampleList) {
        mExampleList = exampleList;
        this.context = context;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_discription_layout,
                parent, false);
        Discription_adaptor.ExampleViewHolder evh = new Discription_adaptor.ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final Discription_adaptor.ExampleViewHolder holder, int position) {
        Product_Discription_model currentItem = mExampleList.get(position);

        //  holder.img_android.setImageResource(currentItem.getCat_img());
        holder.option_key.setText(currentItem.getRV_Option_Key());
        holder.option_value.setText(currentItem.getRV_Option_Value());


/*
        Glide.with(context)
                .load(currentItem.getCat_img())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_android);

        Glide.with(context)
                .load(currentItem.getCat_icon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.icom_img);*/


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterList(ArrayList<Product_Discription_model> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}