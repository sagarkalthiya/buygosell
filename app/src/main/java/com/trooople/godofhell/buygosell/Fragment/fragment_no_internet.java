package com.trooople.godofhell.buygosell.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Provider.NetworkChangeReceiver;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;

public class fragment_no_internet extends Fragment {


    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;
    boolean status_boolean=false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_no_internet, null);

        getActivity().registerReceiver(new NetworkChangeReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra("status", 0);

                Log.w("key", "------------"+status+"--------------");

             /*   if (status_boolean){
                    fregment(new Home_fragment());
                }

                if (status == 1) {

                    if(!status_boolean){
                        status_boolean = true;
                    }


                  //  fregment(new Home_fragment());
                }*/


            }
        };

        //registering the broadcast receiver to update sync status
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("BUY GO SELL");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity) getActivity()).fregment(new Home_fragment(),false);
            }
        });


       // checkConnection();
        return rootView;
    }





}
