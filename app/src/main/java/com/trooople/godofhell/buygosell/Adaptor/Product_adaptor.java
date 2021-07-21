package com.trooople.godofhell.buygosell.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.trooople.godofhell.buygosell.Fragment.Description_fragment;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.All_Product_Model;
import com.trooople.godofhell.buygosell.R;

import java.util.ArrayList;

/**
 * Created by God of hell on 2018-04-16.
 */

public class Product_adaptor extends RecyclerView.Adapter<Product_adaptor.ExampleViewHolder> {
    private ArrayList<All_Product_Model> mExampleList;
    Context context;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    Fragment fg;

    public interface product_id_interface{
        void sendvalue(String product_id);
    }
    private product_id_interface callback;

    private OnItemClick mCallback;

    public interface OnItemClick {
        void onClick(String value);
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView title,price,pro_id_tv;
        RelativeLayout relativeLayout;
        LinearLayout main_liner_layout;
        ImageView img_android;
        CardView cardView;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            img_android = itemView.findViewById(R.id.rv_Product_image);
            cardView = itemView.findViewById(R.id.card_view);
            main_liner_layout = itemView.findViewById(R.id.main_liner_layout);
            title = itemView.findViewById(R.id.product_titel_text);
            price = itemView.findViewById(R.id.product_price_text);
            pro_id_tv = itemView.findViewById(R.id.product_id_txt);

        }
    }

    public Product_adaptor( Context context,ArrayList<All_Product_Model> exampleList,OnItemClick listener) {
        mExampleList = exampleList;
        this.context = context;
        this.mCallback = listener;

    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_layout,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        final All_Product_Model currentItem = mExampleList.get(position);

      //  holder.img_android.setImageResource(currentItem.getProduct_Photo_Path());
        holder.title.setText(currentItem.getProduct_Title());
        holder.price.setText(currentItem.getProduct_Price());
        holder.pro_id_tv.setText(currentItem.getProduct_Id());

        final String proId =currentItem.getProduct_Id();

        Glide.with(context)
                .load(currentItem.getProduct_Photo_Path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_android);


        holder.main_liner_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // get position
                // check if item still exists
                Description_fragment fragmentB=new Description_fragment();
                Bundle bundle=new Bundle();
                bundle.putString("PRODUCT_ID",currentItem.getProduct_Id());
                fragmentB.setArguments(bundle);
                mFragmentManager = ((AppCompatActivity) context).getSupportFragmentManager() ;
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.MainFrame, fragmentB);
                mFragmentTransaction.addToBackStack(null).commit();
                    String clickedDataItem =currentItem.getProduct_Id();
                  //  Toast.makeText(v.getContext(), "You clicked " + clickedDataItem, Toast.LENGTH_SHORT).show();
                    mCallback.onClick(clickedDataItem);

            }
        });

      /*  holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              callback.sendvalue(currentItem.getProduct_Id());

            }
        });*/
    }




    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterList(ArrayList<All_Product_Model> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }




}