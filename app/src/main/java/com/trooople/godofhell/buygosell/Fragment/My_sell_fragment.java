package com.trooople.godofhell.buygosell.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trooople.godofhell.buygosell.Adaptor.my_order_adaptor;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.My_order;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;
import com.trooople.godofhell.buygosell.Tools.RecyclerItemClickListener;

import java.util.ArrayList;

/**
 * Created by God of hell on 2018-04-28.
 */

public class My_sell_fragment extends Fragment {

    Context context;
    private final String android_version_names[] = {
            "Dell",
            "Apple",
            "HP laptop",
            "Mac Book Pro",
            "Lenovo Laptop",
            "Airpod",
            "Pixle 2",
            "Mac book air",
            "Nexus 5x",
            "Dell",
            "Apple",
            "HP laptop",
            "Mac Book Pro",
            "Lenovo Laptop",
            "Airpod",
            "Pixle 2",
            "Mac book air",
            "Nexus 5x",
            "Iphone x",
            "ipod"
    };
    private final String price[] = {
            "$ 250",
            "$ 2506",
            "$ 1250",
            "$ 1250",
            "$ 1119",
            "$ 1250",
            "$ 699",
            "$ 1250",
            "$ 1250",
            "$ 299",
            "$ 1111",
            "$ 1299",
            "$ 999",
            "$ 1250",
            "$ 2999",
            "$ 1250",
            "$ 1999",
            "$ 1690",
            "$ 150",
            "$ 2222"
    };

    private final int android_image_urls[] = {
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,
            R.drawable.bottomslids,
            R.drawable.bottomslide,

    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my_order, null);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("My sell");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).fregment(new Home_fragment(),false);
            }
        });

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_order_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever

                        // ((MainActivity)getActivity()).back_fregment(new Description_fragment());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );


        ArrayList list = prepareData();
        my_order_adaptor adapter = new my_order_adaptor(getActivity(), list);
        recyclerView.setAdapter(adapter);

        return rootView;
    }


    private ArrayList prepareData() {

        ArrayList data_list = new ArrayList<>();
        for (int i = 0; i < android_version_names.length; i++) {
            My_order list = new My_order();
            list.setP_name(android_version_names[i]);
            list.setprice(price[i]);
            list.setimage(android_image_urls[i]);
            data_list.add(list);
        }
        return data_list;
    }


}