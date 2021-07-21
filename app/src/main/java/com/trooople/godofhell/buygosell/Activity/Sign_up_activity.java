package com.trooople.godofhell.buygosell.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_Signup;
import com.trooople.godofhell.buygosell.MainActivity;
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
import java.util.Map;


public class Sign_up_activity extends AppCompatActivity {
    TextView singbtn;
    Spinner country_sp, state_sp, city_sp;
    Component component = new Component();
    ProgressDialog myDialog;

    EditText f_name,L_name,email,pass,conform_pass;
    String country_id,state_id,city_id;
    Snackbar snackbar;
    // User Session Manager Class
    UserSessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        myDialog = component.showProgressDialog(this,"Singing in. Please wait...");
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        f_name =(EditText) findViewById(R.id.et_f_name);
        L_name =(EditText) findViewById(R.id.et_l_name);
        email =(EditText) findViewById(R.id.et_email);
        pass =(EditText) findViewById(R.id.et_password);
        conform_pass =(EditText) findViewById(R.id.et_conform_password);

        country_sp = (Spinner) findViewById(R.id.spinner);
        state_sp = (Spinner) findViewById(R.id.spinner2);
        city_sp = (Spinner) findViewById(R.id.spinner3);



       // country();

        singbtn = (TextView) findViewById(R.id.singbtn);
        singbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = f_name.getText().toString();
                final String lastname = L_name.getText().toString();
                final String email_string = email.getText().toString();
                final String password = pass.getText().toString().trim();
                final String confirmPassword = conform_pass.getText().toString().trim();

                if(f_name.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(v, "Enter First Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (L_name.getText().length() == 0){
                    Snackbar snackbar = Snackbar.make(v, "Enter Last Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (email.getText().length() == 0){
                    Snackbar snackbar = Snackbar.make(v, "Enter Email Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (pass.getText().length() == 0){
                    Snackbar snackbar = Snackbar.make(v, "Enter Your Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else if (password.equals(confirmPassword)) {
                   signup();
                } else {
                    Snackbar snackbar = Snackbar.make(v, "Password not match", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });

        country();
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
                      //  spinner = (Spinner) findViewById(R.id.spinner);
                      // assert spinner != null;
                      country_sp.setVisibility(View.VISIBLE);

                      Dropdown_Signup spinnerAdapter = new Dropdown_Signup(Sign_up_activity.this, heroList);
                      country_sp.setAdapter(spinnerAdapter);
                      // Listener called when spinner item selected
                      country_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                          @Override
                          public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                              // your code here

                              // Get selected row data to show on screen
                              String Company = ((TextView) v.findViewById(R.id.sign_dropdown_text)).getText().toString();
                              country_id = ((TextView) v.findViewById(R.id.country_id)).getText().toString();
                              String phone_no = ((TextView) v.findViewById(R.id.phone_code)).getText().toString();
                              String short_name = ((TextView) v.findViewById(R.id.shortname)).getText().toString();

                              String OutputMsg = "Selected Company : \n\n" + Company + country_id + phone_no + short_name;
                              myDialog.show();
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
                  ArrayList<Dropdown_model> heroList = new ArrayList<>();

                  for (int i = 0; i < ja.length(); i++) {
                      jo = ja.getJSONObject(i);
                      Dropdown_model personUtils = new Dropdown_model();
                      personUtils.setId(jo.getString("id"));
                      personUtils.setName(jo.getString("name"));
                      personUtils.setphone_code(jo.getString("country_id"));
                      heroList.add(personUtils);
                      myDialog.show();
                  }

                  if (null != heroList) {
                      //  spinner = (Spinner) findViewById(R.id.spinner);
                      // assert spinner != null;
                      state_sp.setVisibility(View.VISIBLE);

                      Dropdown_Signup spinnerAdapter = new Dropdown_Signup(Sign_up_activity.this, heroList);
                      state_sp.setAdapter(spinnerAdapter);
                      // Listener called when spinner item selected
                      state_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                          @Override
                          public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                              // your code here

                              // Get selected row data to show on screen
                              String Company = ((TextView) v.findViewById(R.id.sign_dropdown_text)).getText().toString();
                              state_id = ((TextView) v.findViewById(R.id.country_id)).getText().toString();
                              String phone_no = ((TextView) v.findViewById(R.id.phone_code)).getText().toString();

                            city(state_id);
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
                    ArrayList<Dropdown_model> heroList = new ArrayList<>();

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);

                        Dropdown_model personUtils = new Dropdown_model();
                        personUtils.setId(jo.getString("id"));
                        personUtils.setName(jo.getString("name"));
                        personUtils.setphone_code(jo.getString("state_id"));
                        heroList.add(personUtils);
                        myDialog.dismiss();
                    }


                    if (null != heroList) {
                        city_sp.setVisibility(View.VISIBLE);
                        Dropdown_Signup spinnerAdapter = new Dropdown_Signup(Sign_up_activity.this, heroList);
                        city_sp.setAdapter(spinnerAdapter);
                        city_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View v, int position, long id) {
                                // your code here

                                // Get selected row data to show on screen
                                String Company = ((TextView) v.findViewById(R.id.sign_dropdown_text)).getText().toString();
                                city_id = ((TextView) v.findViewById(R.id.country_id)).getText().toString();
                                String phone_no = ((TextView) v.findViewById(R.id.phone_code)).getText().toString();
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

    private void signup() {
     //   RequestQueue queue = Volley.newRequestQueue(this);

        myDialog.show();
        final String firstname = f_name.getText().toString();
        final String lastname = L_name.getText().toString();
        final String email_string = email.getText().toString();
        final String password = pass.getText().toString().trim();
        final String confirmPassword = conform_pass.getText().toString().trim();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("first_name",firstname));
        nameValuePairs.add(new BasicNameValuePair("last_name",lastname));
        nameValuePairs.add(new BasicNameValuePair("user_mail",email_string));
        nameValuePairs.add(new BasicNameValuePair("password",password));
        nameValuePairs.add(new BasicNameValuePair("repassword",confirmPassword));
        nameValuePairs.add(new BasicNameValuePair("country_id",country_id));
        nameValuePairs.add(new BasicNameValuePair("state_id",state_id));
        nameValuePairs.add(new BasicNameValuePair("city_id",city_id));


        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONObject obj = new JSONObject(output);
                    JSONObject userobj = new JSONObject(obj.getString("data"));
                    String userid = userobj.getString("user_id");

                    String st = obj.getString("status");
                    if (st.equals("false")){
                        showSnackbar("RE-try");
                    }else {
                        session.createUserLoginSession(userid,firstname,lastname,email_string,password,country_id,state_id,city_id,"","","");
                        // Starting MainActivity
                        Intent i = new Intent(getApplicationContext(), Login_activity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                    String msg =obj.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.REGISTER);
        sendPostReqAsyncTask.execute(firstname,lastname,email_string,password,country_id,state_id,city_id);

    }

    public void showSnackbar(String stringSnackbar){
        snackbar.make(findViewById(android.R.id.content), stringSnackbar.toString(), Snackbar.LENGTH_SHORT)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }


}
