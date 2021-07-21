package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.trooople.godofhell.buygosell.Adaptor.Discription_adaptor;
import com.trooople.godofhell.buygosell.Adaptor.My_Basket_Adaptor;
import com.trooople.godofhell.buygosell.Adaptor.Shopping_cart_adaptor;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.My_Basket_model;
import com.trooople.godofhell.buygosell.Model.Product_Discription_model;
import com.trooople.godofhell.buygosell.Model.Shopping_cart_model;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Slider_Adaptor.discription_adaptor;
import com.trooople.godofhell.buygosell.Tools.Component;
import com.trooople.godofhell.buygosell.Tools.HideShowScrollListener;
import com.trooople.godofhell.buygosell.UserSessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by God of hell on 2018-04-17.
 */

public class Shopping_cart_fragment extends Fragment implements My_Basket_Adaptor.quntity_update {

    public Shopping_cart_fragment() {
        // Required empty public constructor
    }

    View view;



    int count = 0;
    int quntity_value_multilye = 0;

    ArrayList<My_Basket_model> cart_list = new ArrayList<>();
    My_Basket_Adaptor shoppingCart = null;
    LinearLayoutManager layoutManager;
    RecyclerView product_rv;
    TextView totaoprice, clear_basket_tv;
    LinearLayout liner_total;


    Component component = new Component();
    ProgressDialog myDialog;

    Snackbar snackbar;

    UserSessionManager session;
    String userid;
    String product_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("key", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("key", "onKey Back listener is working!!!");
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                   // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
        product_rv = (RecyclerView) view.findViewById(R.id.productrv);
        totaoprice = (TextView) view.findViewById(R.id.totaoprice);

        clear_basket_tv = (TextView) view.findViewById(R.id.clear_basket_txt);

        liner_total = (LinearLayout) view.findViewById(R.id.liner_total);

        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        session = new UserSessionManager(getActivity());
        myDialog = component.showProgressDialog(getActivity(), "Please wait...");

        HashMap<String, String> user = session.getUserDetails();

        userid = user.get(UserSessionManager.KEY_USER_ID);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity) getActivity()).fregment(new Home_fragment(),false);
            }
        });


    /*    ArrayList<Shopping_cart_model> products_list = new ArrayList<>();

        for(int x=0;x<15;x++) {
            Shopping_cart_model model;
            model = new Shopping_cart_model();
            model.setP_name("Dell Laptop");
            model.setType(true);
            products_list.add(model);
            total_sum+= 1000;
        }
*/
        clear_basket_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(session.checking_looging()){

                }else {
                    Clear_Basket(userid);
                    My_basket(userid);
                }

                totaoprice.setText("TOTAL PRICE : $ " + 0 + " USD");
            }
        });


        product_rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        product_rv.setLayoutManager(layoutManager);
        //this.shoppingCart = new My_Basket_Adaptor(getActivity(),cart_list);
        if(session.checking_looging()){

        }else {
            My_basket(userid);
        }


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        final View view;

        product_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product_rv.smoothScrollToPosition(product_rv.getAdapter().getItemCount());

            }
        });

        product_rv.addOnScrollListener(
                new HideShowScrollListener() {
                    @Override
                    public void onHide() {
                        //totaoprice.setVisibility(View.GONE);
                        slideDown(liner_total);
                    }

                    @Override
                    public void onShow() {
                        slideUp(liner_total);
                    }
                });
    }

    // slide the view from below itself to the current position
    public void slideUp(View view) {

        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(200);
        animate.setFillAfter(true);
        view.startAnimation(animate);
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
            ((MainActivity) getActivity()).back_fregment(new Shopping_cart_fragment());

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

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }


    public void updaetcard() {

        getActivity().invalidateOptionsMenu();
    }



    public void My_basket(String user_id) {
        // myDialog.show();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));
        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        }

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            public My_Basket_Adaptor shoppingCart;

            @Override
            public void processFinish(String output) {
                try {

                    JSONArray publicationOption_array = new JSONArray(new JSONObject(output).getString("publication_list"));
                    JSONObject publicationOption_object;

                    int total_sum = 0;
                    count = publicationOption_array.length();
                    cart_list.clear();
                    for (int i = 0; i < publicationOption_array.length(); i++) {
                        int price_count = 0;
                        publicationOption_object = publicationOption_array.getJSONObject(i);
                        My_Basket_model personUtils = new My_Basket_model();
                        personUtils.setProduct_Publication_Id(publicationOption_object.getString("publication_id"));
                        personUtils.setBasket_Product_Title(publicationOption_object.getString("publication_title"));
                        personUtils.setUser_Basket_Amount(publicationOption_object.getString("user_basket_amount"));
                        personUtils.setBasket_Product_Price(publicationOption_object.getInt("publication_price"));

                        price_count = publicationOption_object.getInt("publication_price");
                       product_id =publicationOption_object.getString("publication_id");

                        personUtils.setBasket_Photo_Path(Url_Endpoint.CATAGORY_PRODUCT_IMAGE_URL + publicationOption_object.getString("publication_photo_path") + "." + publicationOption_object.getString("publication_photo_extension"));
                        cart_list.add(personUtils);

                        total_sum += price_count * publicationOption_object.getInt("user_basket_amount");
                        //   myDialog.dismiss();
                    }
                    totaoprice.setText("TOTAL PRICE : $ " + total_sum + " USD");
                    if (null != cart_list) {
                        shoppingCart = new My_Basket_Adaptor(getActivity(), cart_list, Shopping_cart_fragment.this,Shopping_cart_fragment.this);
                        product_rv.setAdapter(shoppingCart);


                    }
                    updaetcard();




                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Connection Failed", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.MY_BASKET);
        sendPostReqAsyncTask.execute();
    }


    public void Clear_Basket(String user_id) {
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

                    JSONObject publicationOption_object = new JSONObject(output);

                    String result = publicationOption_object.getString("res");

                    updaetcard();

                    Snackbar.make(getView(), result, Snackbar.LENGTH_LONG).show();

                    myDialog.dismiss();

                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Connection Failed", Snackbar.LENGTH_LONG).show();

                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.EMPTY_BASKET);
        sendPostReqAsyncTask.execute();
    }




    @Override
    public void sendvalue(String product_id, String item_count) {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_id", userid));
        nameValuePairs.add(new BasicNameValuePair("amount", item_count));
        nameValuePairs.add(new BasicNameValuePair("pid", product_id));


        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                My_basket(userid);
                myDialog.dismiss();
            }
        }, nameValuePairs, Url_Endpoint.UPDATE_BASKET);
        sendPostReqAsyncTask.execute();

    }

    public void remove_product() {
       myDialog.show();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("user_id", userid));
        nameValuePairs.add(new BasicNameValuePair("id", product_id));

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    JSONObject publicationOption_object = new JSONObject(output);

                    String result = publicationOption_object.getString("res");

                    My_basket(userid);

                    updaetcard();

                    Snackbar.make(getView(), result, Snackbar.LENGTH_LONG).show();

                    myDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.DELEAT_PRODUCT);
        sendPostReqAsyncTask.execute();
    }

}