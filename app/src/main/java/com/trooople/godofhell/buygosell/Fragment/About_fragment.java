package com.trooople.godofhell.buygosell.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;

public class About_fragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about, null);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("About");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).fregment(new Home_fragment(),false);
            }
        });

        return rootView;
    }


}
