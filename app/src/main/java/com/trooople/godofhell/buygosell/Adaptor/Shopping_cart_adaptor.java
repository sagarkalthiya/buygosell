package com.trooople.godofhell.buygosell.Adaptor;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.trooople.godofhell.buygosell.Fragment.Shopping_cart_fragment;
import com.trooople.godofhell.buygosell.Model.Shopping_cart_model;
import com.trooople.godofhell.buygosell.R;

import java.util.ArrayList;

/**
 * Created by God of hell on 2018-04-17.
 */

public class Shopping_cart_adaptor extends RecyclerView.Adapter<Shopping_cart_adaptor.ViewHolder> {

    Context context = null;
    ArrayList<Shopping_cart_model> productList = null;
    private final int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1;
    Integer count=0;

    public Shopping_cart_adaptor(Context context, ArrayList<Shopping_cart_model> chatList) {
        this.context = context;
        this.productList = chatList;
    }

    @Override
    public int getItemViewType(int position) {
        if (productList.get(position).getType()) {
            return SENT_MESSAGE;
        } else {
            return RECEIVED_MESSAGE;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        //Based on view type decide which type of view to supply with viewHolder
        switch (viewType) {
            case SENT_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cart_layout, parent, false);
                break;

            case RECEIVED_MESSAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cart_layout, parent, false);
                break;
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Shopping_cart_model model = productList.get(position);
        holder.product_name.setText(model.getP_name());
        holder.highprice.setPaintFlags(holder.highprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                count=count+1;
                holder.quntity_txt.setText(count.toString());
               // positiveNumbers.put(holder.uniqueKey,count); //Key -> String.valueOf(position) and Value -> int count
            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(count > 0){
                    count=count-1;
                    holder.quntity_txt.setText(count.toString());
                }

               // positiveNumbers.put(holder.uniqueKey,count);   //Key -> String.valueOf(position) and Value -> int count
            }
        });
      //  holder.quntity_txt.setText("" + getQuantity(holder));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /*
     * Here we have kept ID's of all the child row elements same.
     * But we can also create to different viewHolder classes
     * for different child rows.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView product_name, highprice,quntity_txt;
        ImageView removebtn;
        Button increment, decrement;

        public ViewHolder(View itemView) {
            super(itemView);
            product_name = (TextView) itemView.findViewById(R.id.product_name);
            highprice = (TextView) itemView.findViewById(R.id.highprice);
            removebtn = (ImageView) itemView.findViewById(R.id.removebtn);


            increment = (Button) itemView.findViewById(R.id.increment_btn);
            quntity_txt = (TextView) itemView.findViewById(R.id.quantity_text_view);
            decrement = (Button) itemView.findViewById(R.id.decrement_btn);


            removebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* if(context instanceof Shopping_cart_fragment){
                        ((Shopping_cart_fragment)context).updateprice();
                        removeAt(getAdapterPosition());
                    }*/
                    removeAt(getAdapterPosition());
                }
            });



        }

    }

    public void removeAt(int position) {
        productList.remove(position);
        notifyItemRemoved(position);
    }


    public int getQuantity(final ViewHolder holder) {
        return Integer.parseInt(holder.quntity_txt.getText().toString());
    }
}
