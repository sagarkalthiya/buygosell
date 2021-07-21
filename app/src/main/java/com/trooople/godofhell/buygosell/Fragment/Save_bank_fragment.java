package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.trooople.godofhell.buygosell.MainActivity;
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

public class Save_bank_fragment extends Fragment {

    EditText bank_name_et,owner_name_et,iban_et;
    Component component = new Component();
    ProgressDialog myDialog;

    UserSessionManager session;
    String userid;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_save_bank, null);

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

        bank_name_et = (EditText) rootView.findViewById(R.id.bank_name_et);
        owner_name_et = (EditText) rootView.findViewById(R.id.owner_name_et);
        iban_et = (EditText) rootView.findViewById(R.id.iban_number_et);
        Button b = (Button) rootView.findViewById(R.id.save_bank_btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_addres = bank_name_et.getText().toString();
                String addres = owner_name_et.getText().toString();
                String p_code = iban_et.getText().toString();

                if (title_addres.trim().equals("") || addres.trim().equals("") || p_code.trim().equals("")) {
                    Snackbar.make(getView(), "Enter all filed", Snackbar.LENGTH_LONG).show();
                } else {
                 save_bank(userid,title_addres,addres,p_code);
                }
            }
        });



        return rootView;
    }


    public void save_bank(String user_id, String bank_name, String owner_name, String iban) {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("bank_name", bank_name));
            nameValuePairs.add(new BasicNameValuePair("account_holder", owner_name));
            nameValuePairs.add(new BasicNameValuePair("bank_iban", iban));
        }

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    JSONArray publicationOption_array = new JSONArray(new JSONObject(output).getString("data"));
                    JSONObject publicationOption_object;
                    String status = new JSONObject(output).getString("status");
                    if (status.equals("true")) {
                        myDialog.dismiss();
                        Snackbar.make(getView(), "Addres added sucsessfully", Snackbar.LENGTH_LONG).show();
                        ((MainActivity)getActivity()).fregment(new Payment_info_fragment(),true);
                    } else {
                        myDialog.dismiss();
                        Snackbar.make(getView(), "Addres added unsucsessfully", Snackbar.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.ADD_BANK);
        sendPostReqAsyncTask.execute();
    }



}
