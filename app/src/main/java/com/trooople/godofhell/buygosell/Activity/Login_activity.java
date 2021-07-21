package com.trooople.godofhell.buygosell.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;
import com.trooople.godofhell.buygosell.UserSessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Login_activity extends AppCompatActivity implements View.OnClickListener {

    TextView loginbtn;
    TextView signupbtn, forget_btn;
    private Snackbar snackbar;
    EditText user_email,pass;
    Component component = new Component();
    ProgressDialog myDialog;

    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        myDialog = component.showProgressDialog(this,"Singing in. Please wait...");
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
       /* Intent main = new Intent(Login.this,MainActivity.class);
        startActivity(main);*/

        loginbtn = (TextView) findViewById(R.id.logbtn);
        forget_btn = (TextView) findViewById(R.id.forget_btn);
        signupbtn = (TextView) findViewById(R.id.signup_btn);

        user_email = (EditText) findViewById(R.id.et_user_name);
        pass = (EditText) findViewById(R.id.et_pass);



        loginbtn.setOnClickListener(this);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Login_activity.this, Sign_up_activity.class);
                startActivity(mainIntent);
            }
        });
        forget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Login_activity.this, Forget.class);
                startActivity(mainIntent);
            }
        });


    }

    private void login() {
        final String email_string = user_email.getText().toString();
        final String password = pass.getText().toString().trim();
        myDialog.show();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
         nameValuePairs.add(new BasicNameValuePair("user_mail",email_string));
         nameValuePairs.add(new BasicNameValuePair("password",password));
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                   // JSONObject obj = new JSONObject(output);

                    JSONObject jo = new JSONObject(output);
                    JSONObject userobj = new JSONObject(jo.getString("data"));


                    String user_id = userobj.getString("user_id");
                    String user_first_name =userobj.getString("user_first_name");
                    String user_last_name =userobj.getString("user_last_name");
                    String user_country = userobj.getString("user_country");
                    String user_state =userobj.getString("user_state");
                    String user_city = userobj.getString("user_city");
                    String user_profile_picture =userobj.getString("user_profile_picture");

                    String st = jo.getString("status");
                    if (st.equals("false")){
                        showSnackbar("Re-Try");
                        myDialog.dismiss();
                    }else {
                        showSnackbar("Success");
                        myDialog.dismiss();
                        session.createUserLoginSession(user_id,user_first_name,user_last_name,email_string,password,user_country,user_state,user_city,"","","");
                        // session.createUserLoginSession(firstname,lastname,email_string,password,country_id,state_id,city_id);
                        Intent mainIntent = new Intent(Login_activity.this, MainActivity.class);
                        startActivity(mainIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.LOGIN);
        sendPostReqAsyncTask.execute(email_string,password);
    }

    @Override
    public void onClick(View v) {
        TextView b = (TextView) v;
        switch (b.getId()) {
            case R.id.logbtn:
                if(user_email.getText().length() == 0) {
                    showSnackbar("Enter Your User Name");
                } else if (pass.getText().length() == 0){
                    showSnackbar("Enter Your Password");
                }else {
                    login();
                }

                break;

        }
    }

    public void showSnackbar(String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }
}