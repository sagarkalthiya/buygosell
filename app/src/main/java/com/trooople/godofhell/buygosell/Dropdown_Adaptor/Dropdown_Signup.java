package com.trooople.godofhell.buygosell.Dropdown_Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.trooople.godofhell.buygosell.Model.Dropdown_model;
import com.trooople.godofhell.buygosell.R;

import java.util.List;

public class Dropdown_Signup extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Dropdown_model> listData;
    private Context context;
    public Dropdown_Signup(Context context, List<Dropdown_model> listData) {
        this.context = context;
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listData = listData;
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return (Dropdown_model)listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.drop_list_tv, parent, false);
        TextView label=(TextView)row.findViewById(R.id.item_id);
        label.setText(listData.get(position).getName());
        return row;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder spinnerHolder;
        if(convertView == null){
            spinnerHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.dropdown_signup, parent, false);
            spinnerHolder.spinnerItemList = (TextView)convertView.findViewById(R.id.sign_dropdown_text);
            spinnerHolder.country_id = (TextView)convertView.findViewById(R.id.country_id);
            spinnerHolder.phonecode = (TextView)convertView.findViewById(R.id.phone_code);
            spinnerHolder.short_name = (TextView)convertView.findViewById(R.id.shortname);
            convertView.setTag(spinnerHolder);
        }else{
            spinnerHolder = (ViewHolder)convertView.getTag();
        }
        spinnerHolder.country_id.setText(listData.get(position).getId());
        spinnerHolder.short_name.setText(listData.get(position).getshort_name());
        spinnerHolder.spinnerItemList.setText(listData.get(position).getName());
        spinnerHolder.phonecode.setText(listData.get(position).getphone_code());

        return convertView;
    }
    class ViewHolder{
        TextView spinnerItemList,country_id,phonecode,short_name;
    }
}