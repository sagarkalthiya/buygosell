package com.trooople.godofhell.buygosell.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.trooople.godofhell.buygosell.Activity.Sign_up_activity;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_Profile;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_Signup;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_light_adaptor;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_male_female_adaptor;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Light_dropdown;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.Dropdown_model;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.CircleImageView;
import com.trooople.godofhell.buygosell.Tools.Component;
import com.trooople.godofhell.buygosell.Tools.Font_Awesome.FontAwesome;
import com.trooople.godofhell.buygosell.Tools.RoundedImg;
import com.trooople.godofhell.buygosell.UserSessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;

public class Profile_fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    CircleImageView profilePic;
    RoundedImg roundedImage;
    View rootView;
    Component component = new Component();
    EditText old_pass_et, new_pass_et, new_pass_confrom_et;
    EditText et_fname, et_Lname, et_email, et_mobile, et_dob;
    TextView chang_pass_btn, user_name_txt, update_user_btn;
    // Session Manager Class
    UserSessionManager session;
    Snackbar snackbar;
    ProgressDialog myDialog;
    String main_user_id;

    String userid, user_first_name, user_last_name, user_mail, user_birthday, user_telephone, user_photo;

    Spinner country_sp, state_sp, city_sp, gander_sp;
    String country_id, state_id, city_id;
    String gander_string, gander_sp_item, img_base64_string;

    String[] gender = {"Male", "Female"};

    protected static EditText displayCurrentTime, et_end_date;
    DatePicker datePicker;
    public String day, month, year;

    Bitmap bitmap;
    private static final int REQUEST_CAMERA = 2;
    private static final int SELECT_FILE = 2;
    private static final int PICK_FROM_FILE = 3;


    private static final int PICK_GALLERY_IMAGE = 1;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.profile_ui, null);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.anim_toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                component.closeKeyboard(getActivity());
                ((MainActivity) getActivity()).fregment(new Home_fragment(),false);
            }
        });
        session = new UserSessionManager(getActivity());
        myDialog = component.showProgressDialog(getActivity(), "Please wait...");


        HashMap<String, String> user = session.getUserDetails();

        // name
        main_user_id = user.get(UserSessionManager.KEY_USER_ID);

        user_photo = user.get(UserSessionManager.PROFILE_PIC);

        user_birthday = user.get(UserSessionManager.DOB);

        user_telephone = user.get(UserSessionManager.MOBILE_NO);

        user_first_name = user.get(UserSessionManager.KEY_FNAME);
        user_last_name = user.get(UserSessionManager.KEY_LNAME);
        user_mail = user.get(UserSessionManager.KEY_EMAIL);


        // et_dob.setText(" {fa-smile-o} ");
        // email
        profile_init();
        if (session.checking_looging()) {

        } else {
            user_info(main_user_id);
        }


        country();
        return rootView;
    }


    public void profile_init() {
        user_name_txt = (TextView) rootView.findViewById(R.id.user_name_txt);

        FontAwesome.applyToAllViews(getActivity(), rootView.findViewById(R.id.profile_coordinatlayout_id));
        FontAwesome.applyToAllViews(getActivity(), rootView.findViewById(R.id.img_start_date));

        et_fname = (EditText) rootView.findViewById(R.id.et_fname);
        et_Lname = (EditText) rootView.findViewById(R.id.et_lname);
        et_email = (EditText) rootView.findViewById(R.id.et_email);
        et_mobile = (EditText) rootView.findViewById(R.id.et_phone_no);
        et_dob = (EditText) rootView.findViewById(R.id.et_DOB);

        country_sp = (Spinner) rootView.findViewById(R.id.country_sp);
        state_sp = (Spinner) rootView.findViewById(R.id.state_sp);
        city_sp = (Spinner) rootView.findViewById(R.id.city_sp);
        gander_sp = (Spinner) rootView.findViewById(R.id.gander_sp);
        litdropdown(gander_sp);


        et_fname.setText(user_first_name);
        et_Lname.setText(user_last_name);
        et_email.setText(user_mail);
        et_dob.setText(user_birthday);
        et_mobile.setText(user_telephone);

        update_user_btn = (TextView) rootView.findViewById(R.id.update_user_btn);

        update_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_fname.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(v, "Enter Your First Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (et_Lname.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(v, "Enter Your Last Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (et_email.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(v, "Enter Your Email Id", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (et_mobile.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(v, "Enter Your Mobile Number", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (et_dob.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(v, "Enter Date Of Birth", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    if (session.checkLogin()) {
                        getActivity().finish();

                    } else {
                        update_user(main_user_id);
                    }
                }
            }
        });

        if (UserSessionManager.KEY_FNAME != null) {

            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
            collapsingToolbarLayout.setTitle("Buy Go Sell");
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
            user_name_txt.setText("Buy Go Sell");

        } else {
            CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
            collapsingToolbarLayout.setTitle(user_first_name + " " + user_first_name);
            collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
            user_name_txt.setText(user_first_name + " " + user_last_name);

        }


        profilePic = (CircleImageView) rootView.findViewById(R.id.img_home_profile_pic);

        Glide.with(getActivity())
                .load(user_photo)
                .placeholder(R.drawable.profileppic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Add Photo!");

                builder.setItems(options, new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int item) {

                        if (options[item].equals("Take Photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (options[item].equals("Choose from Gallery")) {
                            Intent intentGalley = new Intent(Intent.ACTION_PICK);
                            intentGalley.setType("image/*");
                            startActivityForResult(intentGalley, PICK_GALLERY_IMAGE);
                        } else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }

                    }

                });

                builder.show();


                // galleryIntent();
            }
        });


        old_pass_et = (EditText) rootView.findViewById(R.id.et_old_pass);
        new_pass_et = (EditText) rootView.findViewById(R.id.et_new_pass);
        new_pass_confrom_et = (EditText) rootView.findViewById(R.id.et_new_pass_conform);

        //Typeface fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fa-soild.ttf");

        chang_pass_btn = (TextView) rootView.findViewById(R.id.change_pass_btn);


        final String password = new_pass_et.getText().toString().trim();
        final String confirmPassword = new_pass_confrom_et.getText().toString().trim();

        chang_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (old_pass_et.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(v, "Enter Your Old Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (new_pass_et.getText().length() == 0) {
                    Snackbar snackbar = Snackbar.make(v, "Enter Your New Password", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (password.equals(confirmPassword)) {
                    if (session.checkLogin()) {
                        getActivity().finish();

                    } else {
                        change_pass(main_user_id);
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(v, "Password not match", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });


        displayCurrentTime = (EditText) rootView.findViewById(R.id.et_DOB);
        TextView displayTimeButton = (TextView) rootView.findViewById(R.id.img_start_date);
        assert displayTimeButton != null;
        displayTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_dialog();
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    img_base64_string = getEncoded64ImageStringFromBitmap(bitmap);
                    Log.v("aaaaa", "data:image/png;base64," + img_base64_string);
                    if (session.checking_looging()) {

                    } else {
                        roundedImage = new RoundedImg(BitmapFactory.decodeFile(picturePath));
                        profilePic.setImageDrawable(roundedImage);

                        update_profile(main_user_id);
                    }


                }
                break;

            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                    File destination = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".jpg");


                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    img_base64_string = getEncoded64ImageStringFromBitmap(bitmap);
                    Log.v("camera", "data:image/png;base64," + img_base64_string);
                    if (session.checking_looging()) {

                    } else {
                        roundedImage = new RoundedImg(bitmap);
                        profilePic.setImageDrawable(roundedImage);
                        update_profile(main_user_id);
                    }
                }
            default:
                break;
        }
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);

        return imgString;
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
                displayCurrentTime.setText(year + "-" + month + "-" + day);
                dialog.dismiss();
            }
        });


        dialog.show();
    }


    public void litdropdown(Spinner spinner) {
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Male");
        languages.add("Female");

        Dropdown_male_female_adaptor light_dropdown = new Dropdown_male_female_adaptor(getActivity(), languages);
        spinner.setAdapter(light_dropdown);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gander_sp_item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {

        gander_string = gender[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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

                        Dropdown_Profile spinnerAdapter = new Dropdown_Profile(getActivity(), heroList);
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

                        Dropdown_Profile spinnerAdapter = new Dropdown_Profile(getActivity(), heroList);
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
                        Dropdown_Profile spinnerAdapter = new Dropdown_Profile(getActivity(), heroList);
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


    public void change_pass(String user_id) {
        myDialog.show();
        final String old_pass = old_pass_et.getText().toString();
        final String new_pass = new_pass_et.getText().toString();
        final String confrom_pass = new_pass_confrom_et.getText().toString();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_password", old_pass));
        nameValuePairs.add(new BasicNameValuePair("user_password_new", new_pass));
        nameValuePairs.add(new BasicNameValuePair("user_password_retype", confrom_pass));
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));


        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONObject obj = new JSONObject(output);
                  /*  JSONObject userobj = new JSONObject(obj.getString("data"));
                    String userid = userobj.getString("user_id");
*/
                    String st = obj.getString("status");
                    if (st.equals("false")) {
                        String msg = obj.getString("message");
                        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
                        myDialog.dismiss();

                    } else {
                        Snackbar.make(getView(), "RE-try", Snackbar.LENGTH_LONG).show();
                        myDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.CHANGE_PASSWORD);
        sendPostReqAsyncTask.execute(old_pass, new_pass, confrom_pass, user_id);

    }


    public void update_user(final String user_id) {

        myDialog.show();

        final String firstname = et_fname.getText().toString();
        final String lastname = et_Lname.getText().toString();
        final String email_string = et_email.getText().toString();
        final String mobile = et_mobile.getText().toString().trim();
        final String dob = et_dob.getText().toString().trim();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("user_first_name", firstname));
            nameValuePairs.add(new BasicNameValuePair("user_last_name", lastname));
            nameValuePairs.add(new BasicNameValuePair("user_telephone", mobile));
            nameValuePairs.add(new BasicNameValuePair("user_birthday", dob));
            nameValuePairs.add(new BasicNameValuePair("country_id", country_id));
            nameValuePairs.add(new BasicNameValuePair("state_id", state_id));
            nameValuePairs.add(new BasicNameValuePair("city_id", city_id));
            nameValuePairs.add(new BasicNameValuePair("user_gender", gander_sp_item));

        }


        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONObject obj = new JSONObject(output);
                    JSONObject userobj = new JSONObject(obj.getString("data"));
                    String userid = userobj.getString("user_birthday");
                    Toast.makeText(getActivity(), "dob-->" + userid + gander_sp_item + dob, Toast.LENGTH_SHORT).show();

                    user_info(main_user_id);
                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.UPDATE_USER);
        sendPostReqAsyncTask.execute();

    }


    public void update_profile(String user_id) {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("photo", "data:image/jpeg;base64," + img_base64_string));

        }


        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONObject obj = new JSONObject(output);
                   /* JSONObject userobj = new JSONObject(obj.getString("data"));
                    String userid = userobj.getString("user_birthday");
                    Toast.makeText(getActivity(), "dob-->"+userid+gander_sp_item+dob, Toast.LENGTH_SHORT).show();*/
                    user_info(main_user_id);
                    String msg = obj.getString("message");
                    Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();

                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.UPDATE_USER_PHOTO);
        sendPostReqAsyncTask.execute();
    }


    public void user_info(String user_id) {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        }


        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONObject obj = new JSONObject(output);
                    JSONObject userobj = new JSONObject(obj.getString("data"));
                    String status = obj.getString("status");
                    if (status.equals("true")) {

                        // session.Clear_Shared_Preferences();
                        userid = userobj.getString("user_id");
                        user_first_name = userobj.getString("user_first_name");
                        user_last_name = userobj.getString("user_last_name");
                        user_mail = userobj.getString("user_mail");
                        user_birthday = userobj.getString("user_birthday");
                        user_telephone = userobj.getString("user_telephone");
                        user_photo = Url_Endpoint.USER_IMAGE_PATH + userobj.getString("user_photo");

                        session.createUserLoginSession(userid, user_first_name, user_last_name, user_mail, "", "", "", "", user_telephone, user_birthday, user_photo);

                        Glide.with(getActivity())
                                .load(user_photo)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(profilePic);
                    }

                    String msg = obj.getString("message");
                    Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();

                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Snackbar.make(getView(), "Internet Connection Is To Week", Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.USER_INFO);
        sendPostReqAsyncTask.execute();
    }

}
