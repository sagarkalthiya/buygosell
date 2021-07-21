package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.trooople.godofhell.buygosell.Adaptor.Add_bank_adaptor;
import com.trooople.godofhell.buygosell.Adaptor.Address_adaptor;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.Add_card_model;
import com.trooople.godofhell.buygosell.Model.Address_model;
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

/**
 * Created by God of hell on 2018-04-27.
 */

public class Payment_info_fragment extends Fragment {

    RecyclerView recyclerView;
    Add_bank_adaptor adapter;
    Component component = new Component();
    ProgressDialog myDialog;

    UserSessionManager session;
    String userid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_payment_info, null);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("Add Bank Info");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).fregment(new Home_fragment(),false);
            }
        });

        session = new UserSessionManager(getActivity());
        myDialog = component.showProgressDialog(getActivity(), "Please wait...");

        HashMap<String, String> user = session.getUserDetails();

        userid = user.get(UserSessionManager.KEY_USER_ID);


        Button button =(Button) rootView.findViewById(R.id.add_bank_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).fregment(new Save_bank_fragment(),true);
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_bank_info);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        get_my_banks(userid);

        return rootView;
    }


    public void get_my_banks(final String user_id) {
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
                    List<Add_card_model> get_list = new ArrayList<>();
                    for (int i = 0; i < publicationOption_array.length(); i++) {
                        Add_card_model model = new Add_card_model();
                        publicationOption_object = publicationOption_array.getJSONObject(i);
                        model.setbank_id(publicationOption_object.getString("user_bank_id"));
                        model.setbank_name(publicationOption_object.getString("user_bank_name"));
                        model.setowner_name(publicationOption_object.getString("user_bank_holder_name"));
                        model.setiban_name(publicationOption_object.getString("user_bank_iban"));
                       /* model.settelephone(publicationOption_object.getString("user_address_phone"));
                        model.setcountry(publicationOption_object.getString("user_address_country_name"));
                        model.setstate(publicationOption_object.getString("user_address_state_name"));
                        model.setcity(publicationOption_object.getString("user_address_city_name"));*/
                        get_list.add(model);

                    }

                    if (null != get_list) {
                        adapter = new Add_bank_adaptor(get_list, getContext(), Payment_info_fragment.this );
                        recyclerView.setAdapter(adapter);
                    }

                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.MY_BANKS);
        sendPostReqAsyncTask.execute();
    }

}