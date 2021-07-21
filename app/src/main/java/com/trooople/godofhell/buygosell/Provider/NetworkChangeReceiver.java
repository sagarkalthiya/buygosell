package com.trooople.godofhell.buygosell.Provider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.trooople.godofhell.buygosell.Fragment.fragment_no_internet;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.R;


public class NetworkChangeReceiver extends BroadcastReceiver
{

    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    private  Context context;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (isConnectingToInternet(context)) {

                //sending the broadcast to refresh the list
                Intent i = new Intent(MainActivity.DATA_SAVED_BROADCAST);
                i.putExtra("status", 1);
                context.sendBroadcast(i);

            } else {

                Intent i = new Intent(MainActivity.DATA_SAVED_BROADCAST);
                i.putExtra("status", 0);
                context.sendBroadcast(i);

                Log.e("keshav", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnectingToInternet(Context context)
    {
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}

