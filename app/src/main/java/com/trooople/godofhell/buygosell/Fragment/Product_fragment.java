package com.trooople.godofhell.buygosell.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.trooople.godofhell.buygosell.Adaptor.Product_adaptor;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_catagory;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_dark_adaptor;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_light_adaptor;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.All_Product_Model;
import com.trooople.godofhell.buygosell.Model.Dropdown_model;
import com.trooople.godofhell.buygosell.Model.Product_model;
import com.trooople.godofhell.buygosell.Model.catagory_dropdown_model;
import com.trooople.godofhell.buygosell.Provider.Get_Data;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;
import com.trooople.godofhell.buygosell.Tools.RangeSeekBar;
import com.trooople.godofhell.buygosell.Tools.RecyclerItemClickListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by God of hell on 2018-04-30.
 */

public class Product_fragment extends Fragment implements Product_adaptor.OnItemClick {

    //private ArrayList<All_Product_Model> mExampleList;
    ArrayList<catagory_dropdown_model> cat;

    Component component = new Component();
    ProgressDialog myDialog;

    RequestQueue requestQueue;


    private RecyclerView mRecyclerView;
    private Product_adaptor mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    protected static EditText displayCurrentTime, et_end_date;
    DatePicker datePicker;

    public String start_day, start_month, start_year;
    public String end_day, end_month, end_year;

    DrawerLayout drawer;
    Context context;
    TextView bottom_arrow, selectCat, sidebtn, mintxt, maxtxt, navi_close_btn;
    LinearLayout bottombtn;
    Spinner from_country_spinner, to_country_spinner, from_date_spinner, to_date_spinner;
    int count = 99;


    String from_country_id, to_country_id, country_name, to_country_name;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private PopupWindow pw;

    String product_idd;
    String minprice, maxprice;
    String catagory_id;
    Button clear_filter_btn, apply_filter_btn;

    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_product_navigation, null);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("Product");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity) getActivity()).fregment(new Home_fragment(),false);
            }
        });

        myDialog = component.showProgressDialog(getActivity(), "Please wait...");
        myDialog.setCancelable(true);

        requestQueue = Volley.newRequestQueue(getActivity()); // 'this' is the Context

        drawer = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        NavigationView navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);

        sidebtn = (TextView) rootView.findViewById(R.id.sidebtn);
        navi_close_btn = (TextView) rootView.findViewById(R.id.navi_close_btn);
        bottombtn = (LinearLayout) rootView.findViewById(R.id.bottombtn);
        bottom_arrow = (TextView) rootView.findViewById(R.id.bottom_arrow);
        from_country_spinner = (Spinner) rootView.findViewById(R.id.from_country_spinner_navi);
        to_country_spinner = (Spinner) rootView.findViewById(R.id.to_country_spinner_navi);
        // from_date_spinner = (Spinner) rootView.findViewById(R.id.from_date_sp);
        //to_date_spinner = (Spinner) rootView.findViewById(R.id.to_date_sp);

        sidebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // RadioGroup rg = (RadioGroup) sheetView.findViewById(R.id.radio_group);
                drawer.openDrawer(GravityCompat.END);
            }
        });
        navi_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // RadioGroup rg = (RadioGroup) sheetView.findViewById(R.id.radio_group);
                drawer.closeDrawer(GravityCompat.END);
            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        // createExampleList();
        Product_Catagory("");
        //  buildRecyclerView();
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        Product_Catagory("");
                    }
                }
        );

        EditText editText = (EditText) rootView.findViewById(R.id.serch_et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //filter(s.toString());
                Search_data(s.toString());
            }
        });


        mintxt = (TextView) rootView.findViewById(R.id.min_text);
        maxtxt = (TextView) rootView.findViewById(R.id.mix_txet);

        RangeSeekBar rangeSeekbar = (RangeSeekBar) rootView.findViewById(R.id.rangeSeekbar);
        rangeSeekbar.setRangeValues(1, 10000);

        rangeSeekbar.setNotifyWhileDragging(true);
        rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                mintxt.setText(String.valueOf(minValue));
                minprice = String.valueOf(minValue);
                maxprice = String.valueOf(maxValue);
                maxtxt.setText(String.valueOf(maxValue));
            }
        });

        displayCurrentTime = (EditText) rootView.findViewById(R.id.et_start_date);
        ImageView displayTimeButton = (ImageView) rootView.findViewById(R.id.img_start_date);
        assert displayTimeButton != null;
        displayTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_dialog();
            }
        });

        et_end_date = (EditText) rootView.findViewById(R.id.et_end_date);
        ImageView entTimeButton = (ImageView) rootView.findViewById(R.id.img_end_date);
        assert entTimeButton != null;
        entTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end_date_dialog();
            }
        });

        country();
        clear_filter_btn = (Button) rootView.findViewById(R.id.clear_filter_btn);
        clear_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCurrentTime.setText("");
                et_end_date.setText("");
                Product_Catagory("");
                drawer.closeDrawer(GravityCompat.END);
            }
        });


        apply_filter_btn = (Button) rootView.findViewById(R.id.apply_filter_btn);
        apply_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter_data();
            }
        });
        // myDialog.dismiss();

        //  this.mAdapter.setproductid(Product_fragment.this);

        return rootView;
    }


    @Override
    public void onDetach() {
        super.onDetach();
       Log.w("key","destroy");
    }

    public void date_dialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.date_picker);

        datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.close_date_picker);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        TextView date_selected = (TextView) dialog.findViewById(R.id.select_date_picker);
        // if button is clicked, close the custom dialog
        date_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_day = "" + datePicker.getDayOfMonth();
                start_month = "" + (datePicker.getMonth() + 1);
                start_year = "" + datePicker.getYear();
                displayCurrentTime.setText(start_day + " / " + start_month + " / " + start_year);
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public void end_date_dialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.date_picker);

        datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.close_date_picker);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        TextView date_selected = (TextView) dialog.findViewById(R.id.select_date_picker);
        // if button is clicked, close the custom dialog
        date_selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end_day = "" + datePicker.getDayOfMonth();
                end_month = "" + (datePicker.getMonth() + 1);
                end_year = "" + datePicker.getYear();
                et_end_date.setText(end_day + " / " + end_month + " / " + end_year);
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    private void country() {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray ja = new JSONArray(output);
                    JSONObject jo;
                    ArrayList<Dropdown_model> heroList = new ArrayList<>();

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        Dropdown_model personUtils = new Dropdown_model();
                        personUtils.setId(jo.getString("id"));
                        personUtils.setshort_name(jo.getString("sortname"));
                        personUtils.setName(jo.getString("name"));
                        personUtils.setphone_code(jo.getString("phonecode"));

                        heroList.add(personUtils);
                        // findViewById(R.id.material_design_ball_spin_fade_loader).setVisibility(View.VISIBLE);
                    }

                    if (null != heroList) {
                        cat();
                        from_country_spinner.setVisibility(View.VISIBLE);

                        Dropdown_dark_adaptor spinnerAdapter = new Dropdown_dark_adaptor(getActivity(), heroList);
                        from_country_spinner.setAdapter(spinnerAdapter);
                        // Listener called when spinner item selected
                        from_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                country_name = ((TextView) v.findViewById(R.id.darkdroptextv)).getText().toString();
                                from_country_id = ((TextView) v.findViewById(R.id.country_id)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code)).getText().toString();
                                String short_name = ((TextView) v.findViewById(R.id.shortname)).getText().toString();

                                String OutputMsg = "Selected Company : \n\n" + country_name + from_country_id + phone_no + short_name;
                                //state(country_id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });

                        to_country_spinner.setVisibility(View.VISIBLE);

                        Dropdown_dark_adaptor spinnerAdapterr = new Dropdown_dark_adaptor(getActivity(), heroList);
                        to_country_spinner.setAdapter(spinnerAdapterr);
                        // Listener called when spinner item selected
                        to_country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                to_country_name = ((TextView) v.findViewById(R.id.darkdroptextv)).getText().toString();
                                to_country_id = ((TextView) v.findViewById(R.id.country_id)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code)).getText().toString();
                                String short_name = ((TextView) v.findViewById(R.id.shortname)).getText().toString();

                                String OutputMsg = "Selected Company : \n\n" + country_name + to_country_id + phone_no + short_name;
                                //state(country_id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });
                    }

                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.COUNTRY);
        sendPostReqAsyncTask.execute();

    }



   /* private void filter(String text) {
        ArrayList<All_Product_Model> filteredList = new ArrayList<>();

        for (All_Product_Model item : MainActivity.mExampleList) {
            if (item.getProduct_Title().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }*/


    public void Search_data(final String text) {

        MainActivity.mExampleList.clear();

        //  search?q=0&t=&range_min=1&range_max=1000&start_date=2018-2-16&end_date=2018-2-16&from_country=1&to_country=1

        String url = Url_Endpoint.SERCH + "?q=" + "0" + "&t=" + text;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray ja = new JSONArray(new JSONObject(response).getString("search"));
                            JSONObject jo;
                            MainActivity.mExampleList = new ArrayList<>();
                            MainActivity.mExampleList.clear();
                            for (int i = 0; i < ja.length(); i++) {
                                jo = ja.getJSONObject(i);
                                All_Product_Model personUtils = new All_Product_Model();
                                personUtils.setProduct_Id(jo.getString("publication_id"));
                                personUtils.setProduct_Price(jo.getString("publication_price"));
                                personUtils.setProduct_Title(jo.getString("publication_title"));
                                personUtils.setProduct_Photo_Path(Url_Endpoint.CATAGORY_PRODUCT_IMAGE_URL + jo.getString("publication_photo_path") + "." + jo.getString("publication_photo_extension"));
                                // personUtils.setphone_code(jo.getString("phonecode"));

                                MainActivity.mExampleList.add(personUtils);
                                mSwipeRefreshLayout.setRefreshing(false);
                                myDialog.dismiss();
                            }

                            if (null != MainActivity.mExampleList) {

                                mRecyclerView.setHasFixedSize(true);
                                int mNoOfColumns = Utility.calculateNoOfColumns(getActivity());
                                GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), mNoOfColumns);
                                mRecyclerView.setLayoutManager(mGridLayoutManager);
                                mAdapter = new Product_adaptor(getActivity(), MainActivity.mExampleList, Product_fragment.this);
                                mAdapter.notifyDataSetChanged();

                                mRecyclerView.setAdapter(mAdapter);
                            }

                        } catch (JSONException e) {
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


    public void Product_Catagory(final String categry_id) {

        final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!categry_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("cat_id", categry_id));
        }

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    JSONArray ja = new JSONArray(new JSONObject(output).getString("publicationList"));
                    JSONObject jo;

                    MainActivity.mExampleList = new ArrayList<>();

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        All_Product_Model personUtils = new All_Product_Model();

                        personUtils.setProduct_Id(jo.getString("publication_id"));
                        personUtils.setProduct_Price(jo.getString("publication_price"));
                        personUtils.setProduct_Title(jo.getString("publication_title"));
                        personUtils.setProduct_Photo_Path(Url_Endpoint.CATAGORY_PRODUCT_IMAGE_URL + jo.getString("publication_photo_path") + "." + jo.getString("publication_photo_extension"));
                        // personUtils.setphone_code(jo.getString("phonecode"));

                        MainActivity.mExampleList.add(personUtils);
                        // findViewById(R.id.material_design_ball_spin_fade_loader).setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        // myDialog.dismiss();
                    }

                    if (null != MainActivity.mExampleList) {


                        mRecyclerView.setHasFixedSize(true);
                        int numberOfColumns = 2;
                        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
                        mAdapter = new Product_adaptor(getActivity(), MainActivity.mExampleList, Product_fragment.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    if (!categry_id.equals("")) {
                        myDialog.dismiss();
                    }

                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.ALL_CATAGORY_DETAIL);
        sendPostReqAsyncTask.execute();
    }

    @Override
    public void onClick(String value) {
        product_idd = value;
    }


    public static class Utility {
        public static int calculateNoOfColumns(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (dpWidth / 180);
            return noOfColumns;
        }
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

    private void doIncrease() {
        count++;
        getActivity().invalidateOptionsMenu();
    }

    private void doremove() {

        if (count <= 0) {
        } else {
            count--;
            getActivity().invalidateOptionsMenu();
        }
    }

    private void cat() {
        //  myDialog.show();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray ja = new JSONArray(output);
                    JSONObject jo;
                    cat = new ArrayList<>();
                    //  final ArrayList<String> items = new ArrayList<String>();

                    JSONArray participants = ja;
                    for (int i = 0; i < participants.length(); i++) {
                        jo = participants.getJSONObject(i);
                        catagory_dropdown_model personUtils = new catagory_dropdown_model();
                        personUtils.setId(jo.getString("category_id"));
                        // personUtils.setEn_name(jo.getString("category_name_en"));

                        personUtils.setEn_name(jo.getString("category_name_en"));
                        personUtils.setCat_icon(Url_Endpoint.PRODUCT_IMAGE_URL + jo.getString("category_icon"));
                        cat.add(personUtils);


                    }

                    if (null != cat) {

                        LinearLayout createButton = (LinearLayout) rootView.findViewById(R.id.bottombtn);
                        createButton.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                initiatePopUp(cat);
                                bottom_arrow.setRotation(90);
                            }
                        });

                    }
                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.TOP_CATAGORY);
        sendPostReqAsyncTask.execute();


    }


    private void initiatePopUp(ArrayList<catagory_dropdown_model> items) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get the pop-up window i.e.  drop-down layout
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dropdown_catagory_layout, (ViewGroup) rootView.findViewById(R.id.PopUpView));

        //get the view to which drop-down layout is to be anchored
        RelativeLayout layout1 = (RelativeLayout) rootView.findViewById(R.id.relativeLayout1);
        pw = new PopupWindow(layout, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //Pop-up window background cannot be null if we want the pop-up to listen touch events outside its window
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setTouchable(true);

        //let pop-up be informed about touch events outside its window. This  should be done before setting the content of pop-up
        pw.setOutsideTouchable(true);
        pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //dismiss the pop-up i.e. drop-down when touched anywhere outside the pop-up
        pw.setTouchInterceptor(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                View view = (View) layout.findViewById(R.id.view_catagory_popup_dropdown);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pw.dismiss();
                        bottom_arrow.setRotation(0);
                    }
                });
              /*  if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {

                    return true;
                }*/
                bottom_arrow.setRotation(0);
                //  bottom_arrow.setRotation(0);
                return false;
            }
        });

        //provide the source layout for drop-down
        pw.setContentView(layout);

        //anchor the drop-down to bottom-left corner of 'layout1'
        pw.showAsDropDown(layout1);

        //populate the drop-down list
        final ListView list = (ListView) layout.findViewById(R.id.dropDownList);
        Dropdown_catagory spinnerAdapter = new Dropdown_catagory(getContext(), cat, Product_fragment.this);
        list.setAdapter(spinnerAdapter);
       /* Dropdown_catagory adapter = new Dropdown_catagory(getActivity(), items);
        list.setAdapter(adapter);*/
    }


    public void cat_id(String cat_idd) {
        pw.dismiss();
        bottom_arrow.setRotation(0);
        myDialog.show();
        Product_Catagory(cat_idd);

        catagory_id = cat_idd;
    }


    public void Filter_data() {
        MainActivity.mExampleList.clear();
        myDialog.show();
        //  search?q=0&t=&range_min=1&range_max=1000&start_date=2018-2-16&end_date=2018-2-16&from_country=1&to_country=1

        String url = Url_Endpoint.SERCH + "?q=" + catagory_id + "&t=&range_min=" + minprice + "&range_max=" + maxprice + "&start_date="
                + start_year + "-" + start_month + "-" + start_day + "&end_date=" + end_year + "-" + end_month + "-" + end_day + "&from_country=" + from_country_id + "&to_country=" + to_country_id;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray ja = new JSONArray(new JSONObject(response).getString("search"));
                            JSONObject jo;
                            MainActivity.mExampleList = new ArrayList<>();
                            MainActivity.mExampleList.clear();
                            for (int i = 0; i < ja.length(); i++) {
                                jo = ja.getJSONObject(i);
                                All_Product_Model personUtils = new All_Product_Model();
                                personUtils.setProduct_Id(jo.getString("publication_id"));
                                personUtils.setProduct_Price(jo.getString("publication_price"));
                                personUtils.setProduct_Title(jo.getString("publication_title"));
                                personUtils.setProduct_Photo_Path(Url_Endpoint.CATAGORY_PRODUCT_IMAGE_URL + jo.getString("publication_photo_path") + "." + jo.getString("publication_photo_extension"));
                                // personUtils.setphone_code(jo.getString("phonecode"));

                                MainActivity.mExampleList.add(personUtils);
                                mSwipeRefreshLayout.setRefreshing(false);
                            }

                            if (null != MainActivity.mExampleList) {

                                mRecyclerView.setHasFixedSize(true);
                                int mNoOfColumns = Utility.calculateNoOfColumns(getActivity());
                                GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), mNoOfColumns);
                                mRecyclerView.setLayoutManager(mGridLayoutManager);
                                mAdapter = new Product_adaptor(getActivity(), MainActivity.mExampleList, Product_fragment.this);
                                mAdapter.notifyDataSetChanged();

                                mRecyclerView.setAdapter(mAdapter);
                                drawer.closeDrawer(GravityCompat.END);
                            }

                            myDialog.dismiss();

                        } catch (JSONException e) {
                            myDialog.dismiss();
                            Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
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