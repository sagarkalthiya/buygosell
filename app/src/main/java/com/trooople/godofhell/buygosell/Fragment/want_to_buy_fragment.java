package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_light_adaptor;
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
 * Created by God of hell on 2018-04-16.
 */

public class want_to_buy_fragment  extends Fragment {

    public want_to_buy_fragment() {
        // Required empty public constructor
    }

    private static FragmentManager fragmentManager;

    Component component = new Component();
    ProgressDialog myDialog;

    String country_id,state_id,city_id,country_name,to_country_name,state_name,city_name;
    Spinner from_country_sp, to_country, city_sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_want_to_buy, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("I want to buy");

        myDialog = component.showProgressDialog(getActivity(),"Please wait...");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).fregment(new Home_fragment(),false);
            }
        });
        from_country_sp= (Spinner) view.findViewById(R.id.spinner5);

       country();
        return view;
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
                        myDialog.dismiss();
                        // findViewById(R.id.material_design_ball_spin_fade_loader).setVisibility(View.VISIBLE);
                    }

                   if (null != heroList) {
                        //  spinner = (Spinner) findViewById(R.id.spinner);
                        // assert spinner != null;
                        from_country_sp.setVisibility(View.VISIBLE);

                        Dropdown_light_adaptor spinnerAdapter = new Dropdown_light_adaptor(getContext(), heroList);
                        from_country_sp.setAdapter(spinnerAdapter);
                        // Listener called when spinner item selected
                        from_country_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                String Company = ((TextView) v.findViewById(R.id.lightdroptextv)).getText().toString();
                                country_id = ((TextView) v.findViewById(R.id.country_id_light)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code_light)).getText().toString();
                                String short_name = ((TextView) v.findViewById(R.id.shortname_light)).getText().toString();

                                String OutputMsg = "Selected Company : \n\n" + Company + country_id + phone_no + short_name;
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


}