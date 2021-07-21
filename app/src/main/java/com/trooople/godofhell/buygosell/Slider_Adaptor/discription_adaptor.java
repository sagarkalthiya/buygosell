package com.trooople.godofhell.buygosell.Slider_Adaptor;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.trooople.godofhell.buygosell.Model.All_Product_Model;
import com.trooople.godofhell.buygosell.Model.Product_Discription_model;
import com.trooople.godofhell.buygosell.R;

import java.util.ArrayList;


/**
 * Created by God of hell on 2018-04-17.
 */

public class discription_adaptor extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Product_Discription_model> mExampleList;
    private Integer[] images = {R.drawable.slider1,R.drawable.slider1,R.drawable.slider1,R.drawable.slider1, R.drawable.slider1};

    public discription_adaptor(Context context, ArrayList<Product_Discription_model> exampleList) {
        mExampleList = exampleList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mExampleList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_discripition, null);
        final Product_Discription_model currentItem = mExampleList.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.img_discription);
       // imageView.setImageResource(images[position]);

        Glide.with(context)
                .load(currentItem.getDescription_Product_Photo_Path())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

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