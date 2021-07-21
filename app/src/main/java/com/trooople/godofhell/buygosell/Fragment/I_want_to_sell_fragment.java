package com.trooople.godofhell.buygosell.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
 * Created by God of hell on 2018-03-10.
 */

public class I_want_to_sell_fragment extends Fragment {


    Spinner spinnerColorChange, spinnerColorChange2, spinnerColorChange3, spinnerColorChange4, spinnerColorChange5;
    protected static EditText displayCurrentTime, et_end_date;
    DatePicker datePicker;
    public String day, month, year;
    Component component = new Component();
    ProgressDialog myDialog;
    //ProgressDialog progressDialog;
    String country_id,state_id,city_id,country_name,to_country_name,state_name,city_name;
    Spinner from_country_sp, to_country, city_sp;

    public I_want_to_sell_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_i_want_to_sell, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("I want to sell");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity) getActivity()).fregment(new Home_fragment(),false);
            }
        });

        myDialog = component.showProgressDialog(getActivity(),"Please wait...");



        from_country_sp = (Spinner) view.findViewById(R.id.from_country_spinner);
        to_country = (Spinner) view.findViewById(R.id.to_country_spinner);
        spinnerColorChange3 = (Spinner) view.findViewById(R.id.spinner3);
        spinnerColorChange4 = (Spinner) view.findViewById(R.id.spinner4);
        spinnerColorChange5 = (Spinner) view.findViewById(R.id.spinner5);

       // litdropdown(spinnerColorChange);
     //   litdropdown(spinnerColorChange2);
    /*    litdropdown(spinnerColorChange3);
        litdropdown(spinnerColorChange4);
        litdropdown(spinnerColorChange5);*/

        country();

        displayCurrentTime = (EditText) view.findViewById(R.id.et_start_date);
        ImageView displayTimeButton = (ImageView) view.findViewById(R.id.img_start_date);
        assert displayTimeButton != null;
        displayTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_dialog();
            }
        });

        et_end_date = (EditText) view.findViewById(R.id.et_end_date);
        ImageView entTimeButton = (ImageView) view.findViewById(R.id.img_end_date);
        assert entTimeButton != null;
        entTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end_date_dialog();
            }
        });
        return view;
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
                day = "" + datePicker.getDayOfMonth();
                month = "" + (datePicker.getMonth() + 1);
                year = "" + datePicker.getYear();
                displayCurrentTime.setText(day + " / " + month + " / " + year);
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
                day = "" + datePicker.getDayOfMonth();
                month = "" + (datePicker.getMonth() + 1);
                year = "" + datePicker.getYear();
                et_end_date.setText(day + " / " + month + " / " + year);
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
                        myDialog.dismiss();
                        // findViewById(R.id.material_design_ball_spin_fade_loader).setVisibility(View.VISIBLE);
                    }

                    if (null != heroList) {
                        from_country_sp.setVisibility(View.VISIBLE);

                        Dropdown_light_adaptor spinnerAdapter = new Dropdown_light_adaptor(getActivity(), heroList);
                        from_country_sp.setAdapter(spinnerAdapter);
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

                        Dropdown_light_adaptor spinnerAdapterr = new Dropdown_light_adaptor(getActivity(), heroList);
                        to_country.setAdapter(spinnerAdapterr);
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

                                String OutputMsg = "Selected Company : \n\n" + country_name + country_id + phone_no + short_name;
                                //state(country_id);
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


 /*   public void litdropdown(Spinner spinner) {
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Country One");
        languages.add("Country Two");
        languages.add("Country Three");
        languages.add("Country Four");
        languages.add("Country Five");
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
    }*/
}