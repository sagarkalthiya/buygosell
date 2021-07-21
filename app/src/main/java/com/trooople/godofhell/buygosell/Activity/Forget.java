package com.trooople.godofhell.buygosell.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.catagory_dropdown_model;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Forget extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Snackbar snackbar;
    Component component = new Component();
    ProgressDialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        getSupportActionBar().hide();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Forget Password");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Forget.this,Login_activity.class);
                startActivity(mainIntent);
            }
        });

        myDialog = component.showProgressDialog(this, "Please wait...");
        editText =(EditText) findViewById(R.id.forget_edittext);
        textView =(TextView) findViewById(R.id.forget_btn);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forget_pass();
            }
        });

    }

    public void forget_pass(){
        myDialog.show();
        final String email = editText.getText().toString();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("member_mail",email));
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONObject obj = new JSONObject(output);
                    JSONObject jo;

                   String msg= obj.getString("message");
                    showSnackbar(msg);
                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    showSnackbar("Connection Problem!");
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.FORGET_API);
        sendPostReqAsyncTask.execute();

    }


    public void showSnackbar(String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

}
