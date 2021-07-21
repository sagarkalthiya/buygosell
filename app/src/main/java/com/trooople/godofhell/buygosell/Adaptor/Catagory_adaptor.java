package com.trooople.godofhell.buygosell.Adaptor;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.trooople.godofhell.buygosell.Model.catagory_dropdown_model;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.ResizableImageView;

import java.util.ArrayList;

public class Catagory_adaptor extends RecyclerView.Adapter<Catagory_adaptor.ExampleViewHolder> {
    private ArrayList<catagory_dropdown_model> mExampleList;
    public Context context;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView title,id;
        RelativeLayout relativeLayout;
        ImageView img_android,icom_img;
        ResizableImageView imageView , img2;
        CardView cardView;


        public ExampleViewHolder(View itemView) {
            super(itemView);
            //img_android = itemView.findViewById(R.id.rv_image_home_cat);
            img_android = (ImageView) itemView.findViewById(R.id.rv_image_home_cat);
            icom_img = (ImageView) itemView.findViewById(R.id.product_icon);
            title = itemView.findViewById(R.id.title);
            id = itemView.findViewById(R.id.price_text);

        }
    }


    public Catagory_adaptor(Context context,ArrayList<catagory_dropdown_model> exampleList) {
        mExampleList = exampleList;
        this.context = context;
    }

    @Override
    public Catagory_adaptor.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_catagory_layout,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, int position) {
        catagory_dropdown_model currentItem = mExampleList.get(position);

        //  holder.img_android.setImageResource(currentItem.getCat_img());
        holder.title.setText(currentItem.getEn_name());
        holder.id.setText(currentItem.getId());



        Glide.with(context)
                .load(currentItem.getCat_img())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_android);

        Glide.with(context)
                .load(currentItem.getCat_icon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.icom_img);
     /*   Picasso.with(context)
                .load("https://i.stack.imgur.com/aeY45.jpg")
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(context)
                                .load(R.drawable.dummypic)
                                .placeholder(R.drawable.profileppic)
                                .error(R.drawable.ic_action_camera)
                                .into(holder.imageView);
                    }
                });*/
      /*  Picasso.with(context)
                .load("https://i.stack.imgur.com/aeY45.jpg")
                .into(holder.img_android);*/


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterList(ArrayList<catagory_dropdown_model> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}