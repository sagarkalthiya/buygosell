package com.trooople.godofhell.buygosell.Dropdown_Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.trooople.godofhell.buygosell.Fragment.Product_fragment;
import com.trooople.godofhell.buygosell.Model.Dropdown_model;
import com.trooople.godofhell.buygosell.Model.catagory_dropdown_model;
import com.trooople.godofhell.buygosell.R;

import java.util.ArrayList;
import java.util.List;

public class Dropdown_catagory extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<catagory_dropdown_model> listData;
    Boolean defalt = true;
    Product_fragment fragment;
    private Context context;
    String cat_string;

    public Dropdown_catagory(Context context, List<catagory_dropdown_model> listData, Product_fragment fragment) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = listData;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return (catagory_dropdown_model) listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.drop_list_tv, parent, false);
        TextView label = (TextView) row.findViewById(R.id.item_id);
        label.setText(listData.get(position).getName());
        return row;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Dropdown_catagory.ViewHolder spinnerHolder;
        if (convertView == null) {
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.rv_selected_catagory_layout, parent, false);


            spinnerHolder.chkbox = (ImageView) convertView.findViewById(R.id.icon_img_dropdown);
            convertView.setTag(spinnerHolder);
        } else {
            spinnerHolder = (ViewHolder) convertView.getTag();
        }

        spinnerHolder.tv = (TextView) convertView.findViewById(R.id.SelectOption);
        spinnerHolder.tv.setText(listData.get(position).getEn_name());


        spinnerHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.cat_id(listData.get(position).getId().toString());
            }
        });

        //   spinnerHolder.chkbox.setText(listData.get(position).getCat_icon());

        Glide.with(context)
                .load(listData.get(position).getCat_icon())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(spinnerHolder.chkbox);
        return convertView;
    }

    class ViewHolder {
        TextView tv,cat_id;
        ImageView chkbox;
    }
}