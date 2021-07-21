package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.trooople.godofhell.buygosell.Activity.Sign_up_activity;
import com.trooople.godofhell.buygosell.Adaptor.Address_adaptor;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_Signup;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_light_adaptor;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.Address_model;
import com.trooople.godofhell.buygosell.Model.All_Product_Model;
import com.trooople.godofhell.buygosell.Model.Dropdown_model;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
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

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by God of hell on 2018-04-30.
 */

public class Adress_info_fragment extends Fragment {


    RecyclerView recyclerView;
    Address_adaptor adapter;
    EditText title_address, address, post_code, tlephone;
    Component component = new Component();
    ProgressDialog myDialog;
    String country_id, state_id, city_id, country_name, state_name, city_name;
    Spinner country_sp, state_sp, city_sp;

    UserSessionManager session;
    String userid;

    ArrayList<Dropdown_model> country_list;
    ArrayList<Dropdown_model> state_list;
    ArrayList<Dropdown_model> city_list;
    AlertDialog.Builder alertDialog;;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_adress_info, null);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("Address info");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity) getActivity()).fregment(new Home_fragment(), false);
            }
        });
        session = new UserSessionManager(getActivity());
        myDialog = component.showProgressDialog(getActivity(), "Please wait...");

        HashMap<String, String> user = session.getUserDetails();

        userid = user.get(UserSessionManager.KEY_USER_ID);

        country_sp = (Spinner) rootView.findViewById(R.id.spinner);
        state_sp = (Spinner) rootView.findViewById(R.id.spinner2);
        city_sp = (Spinner) rootView.findViewById(R.id.spinner3);

        title_address = (EditText) rootView.findViewById(R.id.et_address_title);
        address = (EditText) rootView.findViewById(R.id.et_address);
        post_code = (EditText) rootView.findViewById(R.id.et_post_code);
        tlephone = (EditText) rootView.findViewById(R.id.et_telephone);

        country();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_address);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //  createExampleList();

        Button btnadd = (Button) rootView.findViewById(R.id.add_address_btn);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater layout = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layout.inflate(R.layout.dailog_address, null);
                alertDialog.setView(popupView);
                country();
                alertDialog.setCancelable(false);

                country_sp = (Spinner) popupView.findViewById(R.id.spinner);
                state_sp = (Spinner) popupView.findViewById(R.id.spinner2);
                city_sp = (Spinner) popupView.findViewById(R.id.spinner3);

                title_address =(EditText) popupView.findViewById(R.id.et_address_title);
                address =(EditText) popupView.findViewById(R.id.et_address);
                post_code =(EditText) popupView.findViewById(R.id.et_post_code);
                tlephone =(EditText) popupView.findViewById(R.id.et_telephone);


                alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title_addres =title_address.getText().toString();
                        String addres =address.getText().toString();
                        String p_code = post_code.getText().toString();
                        String telephone =tlephone.getText().toString();
                        if (title_addres.trim().equals("")|| addres.trim().equals("")|| p_code.trim().equals("") || telephone.trim().equals("")){
                            Toast.makeText(getActivity(), "enter all filed", Toast.LENGTH_SHORT).show();
                        }else {
                            add_addres(userid, title_addres, addres, p_code, telephone, country_id, state_id, city_id);

                        }

                    }
                });

                alertDialog.setNegativeButton("Cancle",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );

                alertDialog.show();
                //   Toast.makeText(v.getContext(), "Deleted " + holder.textViewName.getText().toString(), Toast.LENGTH_SHORT).show();

               /* String title_addres = title_address.getText().toString();
                String addres = address.getText().toString();
                String p_code = post_code.getText().toString();
                String telephone = tlephone.getText().toString();

                if (title_addres.trim().equals("") || addres.trim().equals("") || p_code.trim().equals("") || telephone.trim().equals("")) {
                    Snackbar.make(getView(), "enter all filed", Snackbar.LENGTH_LONG).show();
                } else {
                    List<Address_model> add_list = new ArrayList<>();
                    add_addres(userid, title_addres, addres, p_code, telephone, country_id, state_id, city_id);
                    //  heroList.add(new Address_model(title_addres, addres, p_code, telephone, country_name, state_name, city_name));
                    adapter = new Address_adaptor(add_list, getContext(), Adress_info_fragment.this);
                    recyclerView.setAdapter(adapter);
                    title_address.setText("");
                    address.setText("");
                    post_code.setText("");
                    tlephone.setText("");
                    Component component = new Component();
                    component.closeKeyboard(getActivity());*/

            }
        });

        get_addres(userid);
        return rootView;
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
                    country_list = new ArrayList<>();

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
                        //  spinner = (Spinner) findViewById(R.id.spinner);
                        // assert spinner != null;
                        country_sp.setVisibility(View.VISIBLE);

                        Dropdown_light_adaptor spinnerAdapter = new Dropdown_light_adaptor(getActivity(), country_list);
                        country_sp.setAdapter(spinnerAdapter);
                        // Listener called when spinner item selected
                        country_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                country_name = ((TextView) v.findViewById(R.id.lightdroptextv)).getText().toString();
                                country_id = ((TextView) v.findViewById(R.id.country_id_light)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code_light)).getText().toString();
                                String short_name = ((TextView) v.findViewById(R.id.shortname_light)).getText().toString();
                                myDialog.show();
                                //   String OutputMsg = "Selected Company : \n\n" + Company + country_id + phone_no + short_name;
                                state(country_id);
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

    private void state(final String id) {


        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("country_id", id));
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray ja = new JSONArray(output);
                    JSONObject jo;
                    state_list = new ArrayList<>();
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        Dropdown_model personUtils = new Dropdown_model();
                        personUtils.setId(jo.getString("id"));
                        personUtils.setName(jo.getString("name"));
                        personUtils.setphone_code(jo.getString("country_id"));
                        state_list.add(personUtils);

                    }

                    if (null != state_list) {
                        //  spinner = (Spinner) findViewById(R.id.spinner);
                        // assert spinner != null;
                        state_sp.setVisibility(View.VISIBLE);

                        Dropdown_light_adaptor spinnerAdapter = new Dropdown_light_adaptor(getActivity(), state_list);
                        state_sp.setAdapter(spinnerAdapter);
                        // Listener called when spinner item selected
                        state_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                state_name = ((TextView) v.findViewById(R.id.lightdroptextv)).getText().toString();
                                state_id = ((TextView) v.findViewById(R.id.country_id_light)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code_light)).getText().toString();

                                city(state_id);
                                myDialog.dismiss();
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
        }, nameValuePairs, Url_Endpoint.STATE);
        sendPostReqAsyncTask.execute();

    }


    private void city(final String state_id) {

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("state_id", state_id));
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray ja = new JSONArray(output);
                    JSONObject jo;
                    city_list = new ArrayList<>();
                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);

                        Dropdown_model personUtils = new Dropdown_model();
                        personUtils.setId(jo.getString("id"));
                        personUtils.setName(jo.getString("name"));
                        personUtils.setphone_code(jo.getString("state_id"));

                        city_list.add(personUtils);
                        myDialog.dismiss();
                    }


                    if (null != city_list) {
                        city_sp.setVisibility(View.VISIBLE);
                        Dropdown_light_adaptor spinnerAdapter = new Dropdown_light_adaptor(getActivity(), city_list);
                        city_sp.setAdapter(spinnerAdapter);
                        city_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                city_name = ((TextView) v.findViewById(R.id.lightdroptextv)).getText().toString();
                                city_id = ((TextView) v.findViewById(R.id.country_id_light)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code_light)).getText().toString();
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
        }, nameValuePairs, Url_Endpoint.CITY);
        sendPostReqAsyncTask.execute();

    }

    public void add_item() {
        String title_addres = title_address.getText().toString();
        String addres = address.getText().toString();
        String p_code = post_code.getText().toString();
        String telephone = tlephone.getText().toString();

        if (title_addres.trim().equals("") || addres.trim().equals("") || p_code.trim().equals("") || telephone.trim().equals("")) {
            Snackbar.make(getView(), "enter all filed", Snackbar.LENGTH_LONG).show();
        } else {
            // heroList.add(new Address_model(title_addres, addres, p_code, telephone, country_name, state_name, city_name));
            List<Address_model> add_list = new ArrayList<>();
            adapter = new Address_adaptor(add_list, getContext(), Adress_info_fragment.this);
            recyclerView.setAdapter(adapter);
            title_address.setText("");
            address.setText("");
            post_code.setText("");
            tlephone.setText("");

        }
    }

    public void add_addres(String user_id, String addres_title, String address, String post_code, String telephoe, String coutry_id, String stateid, String cityid) {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("address_title", addres_title));
            nameValuePairs.add(new BasicNameValuePair("address", address));
            nameValuePairs.add(new BasicNameValuePair("postal_code", post_code));
            nameValuePairs.add(new BasicNameValuePair("telephone", telephoe));
            nameValuePairs.add(new BasicNameValuePair("user_country", coutry_id));
            nameValuePairs.add(new BasicNameValuePair("state", stateid));
            nameValuePairs.add(new BasicNameValuePair("city", cityid));
        }

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    JSONArray publicationOption_array = new JSONArray(new JSONObject(output).getString("data"));
                    JSONObject publicationOption_object;
                    String status = new JSONObject(output).getString("status");
                    if (status.equals("true")) {
                        Snackbar.make(getView(), "Addres added sucsessfully", Snackbar.LENGTH_LONG).show();

                        get_addres(userid);
                    } else {
                        Snackbar.make(getView(), "Addres added unsucsessfully", Snackbar.LENGTH_LONG).show();
                    }

                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.ADD_ADDRES);
        sendPostReqAsyncTask.execute();
    }


    public void get_addres(String user_id) {
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

                    JSONArray publicationOption_array = new JSONArray(new JSONObject(output).getString("data"));
                    JSONObject publicationOption_object;
                    List<Address_model> get_list = new ArrayList<>();
                    for (int i = 0; i < publicationOption_array.length(); i++) {
                        Address_model model = new Address_model();
                        publicationOption_object = publicationOption_array.getJSONObject(i);
                        model.setaddress_id(publicationOption_object.getString("user_address_id"));
                        model.setaddress_title(publicationOption_object.getString("user_address_title"));
                        model.setaddress(publicationOption_object.getString("user_address_text"));
                        model.setpost_code(publicationOption_object.getString("user_address_post_code"));
                        model.settelephone(publicationOption_object.getString("user_address_phone"));
                        model.setcountry(publicationOption_object.getString("user_address_country_name"));
                        model.setstate(publicationOption_object.getString("user_address_state_name"));
                        model.setcity(publicationOption_object.getString("user_address_city_name"));
                        get_list.add(model);

                    }

                    if (null != get_list) {
                        adapter = new Address_adaptor(get_list, getContext(), Adress_info_fragment.this);
                        recyclerView.setAdapter(adapter);
                    }

                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.GET_ADDRES);
        sendPostReqAsyncTask.execute();
    }
}