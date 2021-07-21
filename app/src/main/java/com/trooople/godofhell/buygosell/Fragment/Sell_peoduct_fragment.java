package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_light_adaptor;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Light_dropdown;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.Dropdown_model;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by God of hell on 2018-04-17.
 */

public class Sell_peoduct_fragment extends Fragment {

    public Sell_peoduct_fragment() {
        // Required empty public constructor
    }

    Component component = new Component();
    ProgressDialog myDialog;

    private static FragmentManager fragmentManager;
    int count = 99;

    String country_id,state_id,city_id,country_name,to_country_name,CATAGORY_NAME,CATAGORY_id,state_name,city_name;
    Spinner from_country_sp, to_country, sell_catadory_sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sell_product, container, false);


        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("Sell");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_nav_menu));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).fregment(new Home_fragment(),false);
            }
        });
        myDialog = component.showProgressDialog(getActivity(),"Please wait...");

        from_country_sp = (Spinner) view.findViewById(R.id.from_country_sp);
        to_country = (Spinner) view.findViewById(R.id.to_country_sp);
        sell_catadory_sp= (Spinner) view.findViewById(R.id.catagory_product_spinner);
        Spinner sell_spinner4 = (Spinner) view.findViewById(R.id.sell_spinner4);

        country();

        user_drop(sell_spinner4);

        return view;
    }

    private void country() {
        //getting the progressbar
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


                    }

                    if (null != heroList) {
                        from_country_sp.setVisibility(View.VISIBLE);

                        Dropdown_light_adaptor from_country_adaptor = new Dropdown_light_adaptor(getActivity(), heroList);
                        from_country_sp.setAdapter(from_country_adaptor);
                        // Listener called when spinner item selected
                        from_country_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                country_name= ((TextView) v.findViewById(R.id.lightdroptextv)).getText().toString();
                                country_id = ((TextView) v.findViewById(R.id.country_id_light)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code_light)).getText().toString();
                                String short_name = ((TextView) v.findViewById(R.id.shortname_light)).getText().toString();

                                String OutputMsg = "Selected Company : \n\n" + country_name + country_id + phone_no + short_name;
                                //state(country_id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });

                        to_country.setVisibility(View.VISIBLE);

                        Dropdown_light_adaptor to_country_adaptor = new Dropdown_light_adaptor(getActivity(), heroList);
                        to_country.setAdapter(to_country_adaptor);
                        // Listener called when spinner item selected
                        to_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                to_country_name= ((TextView) v.findViewById(R.id.lightdroptextv)).getText().toString();
                                country_id = ((TextView) v.findViewById(R.id.country_id_light)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code_light)).getText().toString();
                                String short_name = ((TextView) v.findViewById(R.id.shortname_light)).getText().toString();
                                cat();
                                String OutputMsg = "Selected Company : \n\n" + country_name + country_id + phone_no + short_name;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // your code here
                            }

                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.COUNTRY);
        sendPostReqAsyncTask.execute();


    }


    private void cat() {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray ja = new JSONArray(output);
                    JSONObject jo;
                    ArrayList<Dropdown_model> cat = new ArrayList<>();
                    JSONArray participants = ja;
                    for (int i = 0; i < participants.length(); i++) {
                        jo = participants.getJSONObject(i);
                        Dropdown_model personUtils = new Dropdown_model();
                        personUtils.setId(jo.getString("category_id"));
                        personUtils.setshort_name(jo.getString("category_name"));
                        personUtils.setName(jo.getString("category_name_en"));
                        cat.add(personUtils);
                        myDialog.dismiss();
                    }

                    if (null != cat) {

                        sell_catadory_sp.setVisibility(View.VISIBLE);

                        Dropdown_light_adaptor spinnerAdapterrr = new Dropdown_light_adaptor(getActivity(), cat);
                        sell_catadory_sp.setAdapter(spinnerAdapterrr);
                        // Listener called when spinner item selected
                        sell_catadory_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {

                                // Get selected row data to show on screen
                                CATAGORY_NAME= ((TextView) v.findViewById(R.id.lightdroptextv)).getText().toString();
                                CATAGORY_id = ((TextView) v.findViewById(R.id.country_id_light)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code_light)).getText().toString();
                                String short_name = ((TextView) v.findViewById(R.id.shortname_light)).getText().toString();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                // nothingg selected
                            }

                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.TOP_CATAGORY);
        sendPostReqAsyncTask.execute();


    }

    public void user_drop(Spinner spinner) {
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("USD");
        languages.add("USD");
        languages.add("USD");
        languages.add("USD");
        languages.add("USD");
        languages.add("USD");
        Light_dropdown light_dropdown = new Light_dropdown(getActivity(), languages);
        spinner.setAdapter(light_dropdown);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void doIncrease() {
        count++;
        getActivity().invalidateOptionsMenu();
    }

    private void doremove() {

        if (count <= 0) {
        }else {
            count--;
            getActivity().invalidateOptionsMenu();
        }
    }

}