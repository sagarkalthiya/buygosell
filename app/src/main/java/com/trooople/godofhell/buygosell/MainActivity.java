package com.trooople.godofhell.buygosell;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.trooople.godofhell.buygosell.Activity.Login_activity;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_light_adaptor;
import com.trooople.godofhell.buygosell.Fragment.About_fragment;
import com.trooople.godofhell.buygosell.Fragment.Adress_info_fragment;
import com.trooople.godofhell.buygosell.Fragment.All_parmission_fragment;
import com.trooople.godofhell.buygosell.Fragment.Catagory_fragmet;
import com.trooople.godofhell.buygosell.Fragment.Contect_fragment;
import com.trooople.godofhell.buygosell.Fragment.Home_fragment;
import com.trooople.godofhell.buygosell.Fragment.My_order_fragment;
import com.trooople.godofhell.buygosell.Fragment.My_sell_fragment;
import com.trooople.godofhell.buygosell.Fragment.Payment_info_fragment;
import com.trooople.godofhell.buygosell.Fragment.Product_fragment;
import com.trooople.godofhell.buygosell.Fragment.Profile_fragment;
import com.trooople.godofhell.buygosell.Fragment.Sell_peoduct_fragment;
import com.trooople.godofhell.buygosell.Model.All_Product_Model;
import com.trooople.godofhell.buygosell.Model.Dropdown_model;
import com.trooople.godofhell.buygosell.Provider.MyApplication;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.Tools.Font_Awesome.FontAwesome;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    UserSessionManager session;
    private FragmentTransaction mFragmentTransaction;
    private FragmentManager mFragmentManager;
    DrawerLayout drawer;
    NavigationView navigationView;
    Snackbar snackbar;

    public static ArrayList<All_Product_Model> mExampleList;

    TextView home_menu_tv, product_menu_tv, contect_menu_tv, about_menu_tv, login_menu_tv;
    TextView profile_menu_tv, payment_info_menu_tv, my_order_menu_tv, my_sell_menu_tv, addres_info_menu_tv, logout_menu_tv;

    public static ArrayList<Dropdown_model> country_list;
    public static ArrayList<Dropdown_model> state_list;
    public static ArrayList<Dropdown_model> city_list;


    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int status = intent.getIntExtra("status",0);
                Log.w("key","------------"+status+"--------------");



            }
        };

        //registering the broadcast receiver to update sync status
       registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));



        session = new UserSessionManager(getApplicationContext());

        drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);

        // Shoppinglist shoppinglist = new Shoppinglist();
        FontAwesome.applyToAllViews(this, findViewById(R.id.main_drawer_layout));

        FontAwesome.applyToAllViews(this, findViewById(R.id.main_nav_view));

        home_menu_tv = (TextView) findViewById(R.id.home_menu_txt);
        product_menu_tv = (TextView) findViewById(R.id.product_menu_txt);
        contect_menu_tv = (TextView) findViewById(R.id.contect_us_menu_txt);
        about_menu_tv = (TextView) findViewById(R.id.about_menu_txt);
        login_menu_tv = (TextView) findViewById(R.id.login_menu_txt);

        home_menu_tv.setOnClickListener(this);
        product_menu_tv.setOnClickListener(this);
        contect_menu_tv.setOnClickListener(this);
        about_menu_tv.setOnClickListener(this);
        login_menu_tv.setOnClickListener(this);


        profile_menu_tv = (TextView) findViewById(R.id.profile_menu_txt);
        payment_info_menu_tv = (TextView) findViewById(R.id.payment_info_menu_txt);
        my_order_menu_tv = (TextView) findViewById(R.id.my_order_menu_txt);
        my_sell_menu_tv = (TextView) findViewById(R.id.my_sells_menu_txt);
        addres_info_menu_tv = (TextView) findViewById(R.id.addres_info_menu_txt);
        logout_menu_tv = (TextView) findViewById(R.id.logout_menu_txt);

        profile_menu_tv.setOnClickListener(this);
        payment_info_menu_tv.setOnClickListener(this);
        my_order_menu_tv.setOnClickListener(this);
        my_sell_menu_tv.setOnClickListener(this);
        addres_info_menu_tv.setOnClickListener(this);
        logout_menu_tv.setOnClickListener(this);

        if(session.checking_looging()){
            profile_menu_tv.setVisibility(View.GONE);
            payment_info_menu_tv.setVisibility(View.GONE);
            my_order_menu_tv.setVisibility(View.GONE);
            my_sell_menu_tv.setVisibility(View.GONE);
            addres_info_menu_tv.setVisibility(View.GONE);
            logout_menu_tv.setVisibility(View.GONE);
        }else {
            profile_menu_tv.setVisibility(View.VISIBLE);
            payment_info_menu_tv.setVisibility(View.VISIBLE);
            my_order_menu_tv.setVisibility(View.VISIBLE);
            my_sell_menu_tv.setVisibility(View.VISIBLE);
            addres_info_menu_tv.setVisibility(View.VISIBLE);
            logout_menu_tv.setVisibility(View.VISIBLE);
        }
        if (CheckingPermissionIsEnabledOrNot()) {
            if (savedInstanceState == null) {
                fregment(new Home_fragment(),false);
                //
            }
        } else {
            fregment(new All_parmission_fragment(),false);
        }


        //  checkConnection();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId() /*to get clicked view id**/) {
            case R.id.home_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new Home_fragment(),false);
                break;
            case R.id.product_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new Product_fragment(),true);
                break;
            case R.id.contect_us_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new Contect_fragment(),true);
                break;
            case R.id.about_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new About_fragment(),true);
                break;
            case R.id.login_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                Intent i = new Intent(getApplicationContext(), Login_activity.class);
                startActivity(i);
                break;
            case R.id.profile_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new Profile_fragment(),true);
                break;
            case R.id.payment_info_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new Payment_info_fragment(),true);
                break;
            case R.id.my_order_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new My_order_fragment(),true);
                break;
            case R.id.my_sells_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new My_sell_fragment(),true);
                break;
            case R.id.addres_info_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                fregment(new Adress_info_fragment(),true);
                break;
            case R.id.logout_menu_txt:
                drawer.closeDrawer(GravityCompat.START);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("Are you sure you want to exit");
                builder1.setCancelable(false);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                session.logoutUser();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                // Fragment fragment = new Sell_peoduct_fragment();
                break;
            default:
                break;
        }
    }


    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);


        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void setLanguageForApp(String languageToLoad) {
        Locale locale;
        if (languageToLoad.equals("not-set")) { //use any value for default
            locale = Locale.getDefault();
        } else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void publicNav() {
        drawer.openDrawer(GravityCompat.START);
    }

    public void fregment(Fragment profile, boolean back) {
        Bundle arguments = new Bundle();
        arguments.putString("key", "sagar");
        profile.setArguments(arguments);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.MainFrame, profile);
        if (back){
            mFragmentTransaction.addToBackStack(null).commit();
        }else {
            mFragmentTransaction.commit();
        }
       // mFragmentTransaction.commit();

    }


    public void back_fregment(Fragment profile) {

        Bundle arguments = new Bundle();
        arguments.putString("key", "sagar");
        profile.setArguments(arguments);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.MainFrame, profile)
                //mFragmentTransaction.commit();
                .commit();
    }

  @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // fregment(new Home_fragment());

        if (id == R.id.home) {

        }


        return true;
    }

    public  ArrayList<Dropdown_model> country(){
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray ja = new JSONArray(output);
                    JSONObject jo;
                    country_list = new ArrayList<>();
                    Dropdown_model hint = new Dropdown_model();
                    hint.setId("");
                    hint.setshort_name("");
                    hint.setName("Select Country");
                    hint.setphone_code("");

                    country_list.add(hint);

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        Dropdown_model personUtils = new Dropdown_model();
                        personUtils.setId(jo.getString("id"));
                        personUtils.setshort_name(jo.getString("sortname"));
                        personUtils.setName(jo.getString("name"));
                        personUtils.setphone_code(jo.getString("phonecode"));

                        country_list.add(personUtils);

                        // findViewById(R.id.material_design_ball_spin_fade_loader).setVisibility(View.VISIBLE);
                    }


                    if (null != country_list) {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.COUNTRY);
        sendPostReqAsyncTask.execute();

        return country_list;
    }



}
