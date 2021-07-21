package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trooople.godofhell.buygosell.Adaptor.Discription_adaptor;
import com.trooople.godofhell.buygosell.Adaptor.Product_adaptor;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.All_Product_Model;
import com.trooople.godofhell.buygosell.Model.Product_Discription_model;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Slider_Adaptor.discription_adaptor;
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


/**
 * Created by God of hell on 2018-04-17.
 */

public class Description_fragment extends Fragment {

    public Description_fragment() {
        // Required empty public constructor
    }
    UserSessionManager session;
    String userid;

    View view;

    Component component = new Component();
    ProgressDialog myDialog;

    ViewPager viewPager, viewPagertwo, profileviewPagertwo;
    TabLayout tabLayout, tabLayouttwo;
    int count = 0;
    Button buy_now_btn,add_to_cart_btn;
    String pro_id;
    TextView price_tv, from_date_tv, to_date_tv, seller_tv, from_country_tv, to_country_tv, note_tv, product_discription_title_tv, product_discription_note_tv;
    private RecyclerView DIS_RecyclerView;
    private Discription_adaptor DIS_Adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_description, container, false);

        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        toolbar.setTitle("Product discription");
        activity.setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).fregment(new Home_fragment(),false);
            }
        });
        init();
        session = new UserSessionManager(getActivity());
        myDialog = component.showProgressDialog(getActivity(), "Please wait...");

        HashMap<String, String> user = session.getUserDetails();

        userid = user.get(UserSessionManager.KEY_USER_ID);

        pro_id = getArguments().getString("PRODUCT_ID");
        if (pro_id == null) {
            //  Snackbar.make(getView(),"pid_disfragemtn null--->"+pro_id , Snackbar.LENGTH_LONG).show();
            Toast.makeText(getActivity(), "Internet Connection Is To Week" , Toast.LENGTH_SHORT).show();
            if(session.checking_looging()){

            }else {
                My_basket(userid);
            }
        }else {
            if(session.checking_looging()){

            }else {
                My_basket(userid);
                Product_detail(pro_id);
            }

        }

        Product_detail(pro_id);
        return view;
    }


    public void init(){

        DIS_RecyclerView = (RecyclerView) view.findViewById(R.id.discription_rv);

        product_discription_title_tv = (TextView) view.findViewById(R.id.product_discription_title_tv);
        product_discription_note_tv = (TextView) view.findViewById(R.id.product_discription_note_tv);
        price_tv = (TextView) view.findViewById(R.id.option_value_price);
        from_date_tv = (TextView) view.findViewById(R.id.optio_value_from_date);
        to_date_tv = (TextView) view.findViewById(R.id.option_value_to_date);
        seller_tv = (TextView) view.findViewById(R.id.option_value_seller);
        from_country_tv = (TextView) view.findViewById(R.id.option_value_from_country);
        to_country_tv = (TextView) view.findViewById(R.id.option_value_to_country);
        note_tv = (TextView) view.findViewById(R.id.option_value_note);


        add_to_cart_btn = (Button) view.findViewById(R.id.add_to_cart_btn);
        add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pro_id == null) {
                    //  Snackbar.make(getView(),"pid_disfragemtn null--->"+pro_id , Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Internet Connection Is To Week" , Toast.LENGTH_SHORT).show();
                }else {
                    if(session.checkLogin()){
                        getActivity().finish();
                    }else {
                        Add_to_cart(userid,pro_id);
                    }


                }

            }
        });


        viewPager = (ViewPager) view.findViewById(R.id.discription_viewPager);
        tabLayout = (TabLayout) view.findViewById(R.id.discription_tabDots);

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



    public void Product_detail(String product_id) {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!product_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("id", product_id));
        }

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    JSONObject jo = new JSONObject(output);
                    JSONObject publication = new JSONObject(jo.getString("publication"));


                    product_discription_title_tv.setText(publication.getString("publication_title"));
                    product_discription_note_tv.setText(publication.getString("publication_description"));
                    price_tv.setText(publication.getString("publication_currency") + "  " + publication.getString("publication_price"));
                    from_date_tv.setText(publication.getString("publication_start_date"));
                    to_date_tv.setText(publication.getString("publication_end_date"));
                    seller_tv.setText(publication.getString("username"));
                    note_tv.setText(publication.getString("publication_description"));

                    JSONObject from_country = new JSONObject(jo.getString("from_country"));
                    from_country_tv.setText(from_country.getString("name"));

                    JSONObject to_country = new JSONObject(jo.getString("to_country"));
                    to_country_tv.setText(to_country.getString("name"));


                    JSONArray publicationOption_array = new JSONArray(new JSONObject(output).getString("publicationOption"));
                    JSONObject publicationOption_object;

                    ArrayList<Product_Discription_model> heroList = new ArrayList<>();

                    for (int i = 0; i < publicationOption_array.length(); i++) {
                        publicationOption_object = publicationOption_array.getJSONObject(i);
                        Product_Discription_model personUtils = new Product_Discription_model();
                        personUtils.setRV_Option_Key(publicationOption_object.getString("option_key"));
                        personUtils.setRV_Option_Value(publicationOption_object.getString("option_value"));
                        heroList.add(personUtils);
                      //  myDialog.dismiss();
                    }

                    if (null != heroList) {

                        DIS_RecyclerView.setHasFixedSize(false);
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        DIS_RecyclerView.setLayoutManager(mLayoutManager);
                        DIS_Adapter = new Discription_adaptor(getActivity(), heroList);
                        DIS_RecyclerView.setAdapter(DIS_Adapter);
                    }

                    ArrayList<Product_Discription_model> photo_list = new ArrayList<>();
                    JSONArray publication_photo_array = new JSONArray(new JSONObject(output).getString("publication_photo"));
                    JSONObject publication_photo_object;

                    for (int i = 0; i < publication_photo_array.length(); i++) {
                        publication_photo_object = publication_photo_array.getJSONObject(i);
                        Product_Discription_model photo = new Product_Discription_model();
                        photo.setDescription_Product_Photo_Path(Url_Endpoint.CATAGORY_PRODUCT_IMAGE_URL + publication_photo_object.getString("publication_photo_path") + "." + publication_photo_object.getString("publication_photo_extension"));
                       photo_list.add(photo);
                    }
                    if (null != photo_list) {

                        tabLayout.setupWithViewPager(viewPager, true);
                        discription_adaptor viewPagerAdapter = new discription_adaptor(getActivity(), photo_list);
                        viewPager.setAdapter(viewPagerAdapter);
                        myDialog.dismiss();
                    }



                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(),"Internet Connection Is To Week" , Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.PRODUCT_DETAIL);
        sendPostReqAsyncTask.execute();
    }


    public void Add_to_cart(final String useer_id, String product_id){


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!product_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("id", product_id));
            nameValuePairs.add(new BasicNameValuePair("user_id", useer_id));
            nameValuePairs.add(new BasicNameValuePair("qty", "1"));
            nameValuePairs.add(new BasicNameValuePair("sid", useer_id));
        }

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    JSONObject jo = new JSONObject(output);
                    String msg =jo.getString("res");
                    Snackbar.make(getView(),msg , Snackbar.LENGTH_LONG).show();
                    My_basket(useer_id);

                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(),"Internet Connection Is To Week" , Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.ADD_TO_BASKET);
        sendPostReqAsyncTask.execute();
    }

    public void My_basket(String user_id) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

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



                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(),"Internet Connection Is To Week" , Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.MY_BASKET);
        sendPostReqAsyncTask.execute();
    }


    public void updaetcard(){

        getActivity().invalidateOptionsMenu();
    }




}