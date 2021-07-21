package com.trooople.godofhell.buygosell.Adaptor;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.trooople.godofhell.buygosell.Fragment.Shopping_cart_fragment;
import com.trooople.godofhell.buygosell.Model.My_Basket_model;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.ResizableImageView;

import java.util.ArrayList;

public class My_Basket_Adaptor extends RecyclerView.Adapter<My_Basket_Adaptor.ExampleViewHolder> {
    private ArrayList<My_Basket_model> mExampleList;
    public Context context;


   public interface quntity_update{
       void sendvalue(String product_id,String item_count);
   }

   private quntity_update callback;

   Fragment fragment;


    public class ExampleViewHolder extends RecyclerView.ViewHolder {
        TextView title,basket_price;
        RelativeLayout relativeLayout;
        ImageView img_android,icom_img;
        TextView product_name, highprice,quntity_txt;
        ImageView removebtn;
        Button increment, decrement;


        public ExampleViewHolder(View itemView) {
            super(itemView);
            //img_android = itemView.findViewById(R.id.rv_image_home_cat);
            img_android = (ImageView) itemView.findViewById(R.id.basket_product_img);
            title = itemView.findViewById(R.id.basket_product_name);
            basket_price = itemView.findViewById(R.id.basket_product_price_txt);
            highprice = (TextView) itemView.findViewById(R.id.highprice);
            removebtn = (ImageView) itemView.findViewById(R.id.removebtn);


            increment = (Button) itemView.findViewById(R.id.increment_btn);
            quntity_txt = (TextView) itemView.findViewById(R.id.quantity_text_view);
            decrement = (Button) itemView.findViewById(R.id.decrement_btn);

            removebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Shopping_cart_fragment) fragment).remove_product();
                    removeAt(getAdapterPosition());
                }
            });

        }
    }


    public My_Basket_Adaptor(Context context,ArrayList<My_Basket_model> exampleList,quntity_update listener,Fragment fragment) {
        mExampleList = exampleList;
        this.context = context;
        this.callback=listener;
        this.fragment=fragment;
    }

    @Override
    public My_Basket_Adaptor.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cart_layout,
                parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {
        final My_Basket_model currentItem = mExampleList.get(position);


        //  holder.img_android.setImageResource(currentItem.getCat_img());
        holder.title.setText(currentItem.getBasket_Product_Title());
        holder.basket_price.setText(String.valueOf(currentItem.getBasket_Product_Price()));
        holder.quntity_txt.setText(currentItem.getUser_Basket_Amount());



        holder.highprice.setPaintFlags(holder.highprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int count = Integer.parseInt(holder.quntity_txt.getText().toString());
                count++;
                holder.quntity_txt.setText(String.valueOf(count));
                callback.sendvalue(currentItem.getProduct_Publication_Id(),String.valueOf(count));

                // positiveNumbers.put(holder.uniqueKey,count); //Key -> String.valueOf(position) and Value -> int count
            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int count = Integer.parseInt(holder.quntity_txt.getText().toString());

                if(count > 1){

                    count=count-1;
                    holder.quntity_txt.setText(String.valueOf(count));
                    callback.sendvalue(currentItem.getProduct_Publication_Id(),String.valueOf(count));
                }

                // positiveNumbers.put(holder.uniqueKey,count);   //Key -> String.valueOf(position) and Value -> int count
            }
        });


        Glide.with(context)
                .load(currentItem.getBasket_Photo_Path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.img_android);


    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void removeAt(int position) {
        mExampleList.remove(position);
        notifyItemRemoved(position);
    }

    public void filterList(ArrayList<My_Basket_model> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}