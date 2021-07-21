package com.trooople.godofhell.buygosell.Adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trooople.godofhell.buygosell.Model.My_order;
import com.trooople.godofhell.buygosell.R;

import java.util.ArrayList;

/**
 * Created by God of hell on 2018-04-27.
 */

public class my_order_adaptor extends RecyclerView.Adapter<my_order_adaptor.ViewHolder> {
    private ArrayList<My_order> home_list;
    private Context context;


    public my_order_adaptor(Context context, ArrayList<My_order> home_list) {
        this.context = context;
        this.home_list = home_list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_my_order, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.product_name.setText(home_list.get(i).getP_name());
        viewHolder.price_txt.setText(home_list.get(i).getprice());
        viewHolder.pro_img.setImageDrawable(context.getResources().getDrawable(home_list.get(i).getimage()));


        //Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).resize(120, 60).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return home_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView product_name,disc_txt,date_txt,price_txt;
        ImageView pro_img;
        public ViewHolder(View view) {
            super(view);

            product_name = (TextView) itemView.findViewById(R.id.product_name);
            disc_txt = (TextView) itemView.findViewById(R.id.discription_txt);
            date_txt = (TextView) itemView.findViewById(R.id.date_txt);
            price_txt = (TextView) itemView.findViewById(R.id.price_txt);
            pro_img = (ImageView) itemView.findViewById(R.id.my_order_image);



        }

    }

}