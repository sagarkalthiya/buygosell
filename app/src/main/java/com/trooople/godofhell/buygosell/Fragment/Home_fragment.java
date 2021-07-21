package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trooople.godofhell.buygosell.Activity.Splesh_activity;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.All_Product_Model;
import com.trooople.godofhell.buygosell.Model.Best_sellers_model;
import com.trooople.godofhell.buygosell.Provider.NetworkChangeReceiver;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Slider_Adaptor.Best_seller_slider_adaptor;
import com.trooople.godofhell.buygosell.Slider_Adaptor.Profile_slider;
import com.trooople.godofhell.buygosell.Slider_Adaptor.SliderTwo;
import com.trooople.godofhell.buygosell.Slider_Adaptor.ViewPagerAdapter;
import com.trooople.godofhell.buygosell.Tools.Component;
import com.trooople.godofhell.buygosell.UserSessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home_fragment extends Fragment {

    public Home_fragment() {
        // Required empty public constructor
    }
    ViewPager best_sellery_vp, new_product_slider, profileviewPagertwo;
    TabLayout tabLayout, tabLayouttwo;
    int count = 0;
    Button i_want_sell,i_want_buy;
    private static FragmentManager fragmentManager;

    Component component = new Component();
    ProgressDialog myDialog;


    UserSessionManager session;
    String userid;

    RequestQueue requestQueue;


    public static final String DATA_SAVED_BROADCAST = "net.simplifiedcoding.datasaved";

    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;
    boolean status_boolean=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);


      setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_nav_menu));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).publicNav();
            }
        });

        requestQueue = Volley.newRequestQueue(getActivity()); //

          i_want_sell = (Button) view.findViewById(R.id.i_want_sell) ;
        i_want_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).back_fregment(new I_want_to_sell_fragment());
            }
        });

        i_want_buy = (Button) view.findViewById(R.id.i_want_buy) ;
        i_want_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).back_fregment(new want_to_buy_fragment());
            }
        });


        best_sellery_vp = (ViewPager) view.findViewById(R.id.viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabDots);

        profileviewPagertwo = (ViewPager) view.findViewById(R.id.profileviewPagertwo);
        Profile_slider profile_slider = new Profile_slider(getActivity());
        profileviewPagertwo.setAdapter(profile_slider);

        ImageView leftNav = (ImageView) view.findViewById(R.id.button);
        ImageView rightNav = (ImageView) view.findViewById(R.id.button2);

// Images left navigation
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = profileviewPagertwo.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    profileviewPagertwo.setCurrentItem(tab);
                } else if (tab == 0) {
                    profileviewPagertwo.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = profileviewPagertwo.getCurrentItem();
                tab++;
                profileviewPagertwo.setCurrentItem(tab);
            }
        });

        new_product_slider = (ViewPager) view.findViewById(R.id.viewPagertwo);
        tabLayouttwo = (TabLayout) view.findViewById(R.id.tabDotstwo);
       /* tabLayouttwo.setupWithViewPager(new_product_slider, true);
        SliderTwo sliderTwo = new SliderTwo(getActivity());
        new_product_slider.setAdapter(sliderTwo);
*/
        session = new UserSessionManager(getActivity());
        myDialog = component.showProgressDialog(getActivity(), "Please wait...");
        myDialog.setCancelable(true);

        HashMap<String, String> user = session.getUserDetails();

        userid = user.get(UserSessionManager.KEY_USER_ID);

        if(session.checking_looging()){

        }else {
            My_basket(userid);
        }

        best_seller_slider();
        new_product_slider();
        return view;
    }




    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_count);
        menuItem.setIcon(buildCounterDrawable(count, R.drawable.ic_cart_shopping));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_count) {
         ((MainActivity)getActivity()).back_fregment(new Shopping_cart_fragment());

        }
        return super.onOptionsItemSelected(item);
    }


    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.shopping_cart_toolbar, null);
        view.setBackgroundResource(backgroundImageId);

        if (count <= 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }


    public void updaetcard(){

        getActivity().invalidateOptionsMenu();
    }


    public void My_basket(String user_id) {
        myDialog.show();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        }

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    JSONArray publicationOption_array = new JSONArray(new JSONObject(output).getString("publication_list"));
                    JSONObject publicationOption_object;

                    count = publicationOption_array.length();

                    updaetcard();


                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                  ///  Snackbar.make(getView(),"Internet Connection Is To Week" , Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }



            }
        }, nameValuePairs, Url_Endpoint.MY_BASKET);
        sendPostReqAsyncTask.execute();
    }


    public void best_seller_slider(){
        myDialog.show();
        String url = Url_Endpoint.BEST_SELLER;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray ja = new JSONArray(new JSONObject(response).getString("data"));
                            JSONObject jo;
                            ArrayList<Best_sellers_model> list = new ArrayList<>();
                            for (int i = 0; i < ja.length(); i++) {
                                jo = ja.getJSONObject(i);
                               Best_sellers_model personUtils = new Best_sellers_model();
                                personUtils.setProduct_Id(jo.getString("publication_id"));
                                personUtils.setProduct_Price(jo.getString("publication_price"));
                                personUtils.setProduct_Title(jo.getString("publication_title"));
                                personUtils.setProduct_Photo_Path(Url_Endpoint.CATAGORY_PRODUCT_IMAGE_URL + jo.getString("publication_photo_path") + "." + jo.getString("publication_photo_extension"));
                                // personUtils.setphone_code(jo.getString("phonecode"));

                                list.add(personUtils);
                                myDialog.dismiss();
                            }

                            if (null != list) {


                                tabLayout.setupWithViewPager(best_sellery_vp, true);
                                Best_seller_slider_adaptor viewPagerAdapter = new Best_seller_slider_adaptor(getActivity(),list);
                                best_sellery_vp.setAdapter(viewPagerAdapter);

                            }

                        } catch (JSONException e) {
                            myDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //add request to queue
        requestQueue.add(stringRequest);
    }

    public void new_product_slider(){
        myDialog.show();
        String url = Url_Endpoint.NEW_PRODUCT_ARRAIVR;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray ja = new JSONArray(new JSONObject(response).getString("data"));
                            JSONObject jo;
                            ArrayList<Best_sellers_model> new_product_list = new ArrayList<>();
                            for (int i = 0; i < ja.length(); i++) {
                                jo = ja.getJSONObject(i);
                                Best_sellers_model personUtils = new Best_sellers_model();
                                personUtils.setProduct_Id(jo.getString("publication_id"));
                                personUtils.setProduct_Price(jo.getString("publication_price"));
                                personUtils.setProduct_Title(jo.getString("publication_title"));
                                personUtils.setProduct_Photo_Path(Url_Endpoint.CATAGORY_PRODUCT_IMAGE_URL + jo.getString("publication_photo_path") + "." + jo.getString("publication_photo_extension"));
                                // personUtils.setphone_code(jo.getString("phonecode"));

                                new_product_list.add(personUtils);
                                myDialog.dismiss();
                            }

                            if (null != new_product_list) {


                                tabLayouttwo.setupWithViewPager(new_product_slider, true);
                                Best_seller_slider_adaptor viewPagerAdapter = new Best_seller_slider_adaptor(getActivity(),new_product_list);
                                new_product_slider.setAdapter(viewPagerAdapter);

                            }

                        } catch (JSONException e) {
                            myDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //add request to queue
        requestQueue.add(stringRequest);
    }


}