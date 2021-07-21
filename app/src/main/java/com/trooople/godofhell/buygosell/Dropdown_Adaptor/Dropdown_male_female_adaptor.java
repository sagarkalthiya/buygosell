package com.trooople.godofhell.buygosell.Dropdown_Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.trooople.godofhell.buygosell.R;

import java.util.ArrayList;

public class Dropdown_male_female_adaptor extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private ArrayList<String> asr;

    public Dropdown_male_female_adaptor(Context context, ArrayList<String> asr) {
        this.asr = asr;
        activity = context;
    }


    public int getCount() {
        return asr.size();
    }

    public Object getItem(int i) {
        return asr.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }


    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.drop_list_tv, parent, false);
        TextView label=(TextView)row.findViewById(R.id.item_id);
        label.setText(asr.get(position));
        return row;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {

        View row = LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.dropdown_male_female, viewgroup, false);
        TextView label = (TextView) row.findViewById(R.id.male_female_dropdown_text);
        label.setText(asr.get(i));
        return row;
    }

}