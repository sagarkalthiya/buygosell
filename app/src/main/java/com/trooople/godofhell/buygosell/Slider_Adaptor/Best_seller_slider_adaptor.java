package com.trooople.godofhell.buygosell.Slider_Adaptor;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.trooople.godofhell.buygosell.Fragment.Description_fragment;
import com.trooople.godofhell.buygosell.Model.All_Product_Model;
import com.trooople.godofhell.buygosell.Model.Best_sellers_model;
import com.trooople.godofhell.buygosell.R;

import java.util.ArrayList;

public class Best_seller_slider_adaptor  extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    ArrayList<Best_sellers_model> arrayList;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private Integer[] images = {R.drawable.newslideone,R.drawable.bottomslide,R.drawable.newslideone,R.drawable.bottomslide, R.drawable.newslideone};

    public Best_seller_slider_adaptor(Context context , ArrayList<Best_sellers_model> list) {
        this.context = context;
        this.arrayList = list;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, null);
        final Best_sellers_model currentItem = arrayList.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.slider_product_img);
        TextView product_title =(TextView) view.findViewById(R.id.slider_product_title);
        TextView product_price =(TextView) view.findViewById(R.id.slider_product_price);
        product_title.setText(currentItem.getProduct_Title());
        product_price.setText(currentItem.getProduct_Price());

        Glide.with(context)
                .load(currentItem.getProduct_Photo_Path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener(){
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
               // mCallback.onClick(clickedDataItem);

            }
        });

       // imageView.setImageResource(currentItem.getProduct_Photo_Path()));

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}