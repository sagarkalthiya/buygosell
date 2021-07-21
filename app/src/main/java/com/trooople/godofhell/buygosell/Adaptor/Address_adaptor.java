package com.trooople.godofhell.buygosell.Adaptor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_light_adaptor;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Light_dropdown;
import com.trooople.godofhell.buygosell.Fragment.Adress_info_fragment;
import com.trooople.godofhell.buygosell.Model.Address_model;
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

public class Address_adaptor extends RecyclerView.Adapter<Address_adaptor.HeroViewHolder> {


    public List<Address_model> heroList;
    private Context context;
    private AlertDialog.Builder alertDialog;;

    ViewGroup parent;

    EditText title_address,address,post_code,tlephone;
    Spinner country_sp, state_sp, city_sp;
    public static int currentPosition = 0;

    Component component = new Component();
    ProgressDialog myDialog;
    String country_id,state_id,city_id,country_name,state_name,city_name;
    UserSessionManager session;
    String userid;
    private Fragment fragment;

    public Address_adaptor(List<Address_model> heroList, Context context ,Fragment fragment) {
        this.heroList = heroList;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_address_info, parent, false);
        return new HeroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HeroViewHolder holder, final int position) {
        session = new UserSessionManager(context);
        myDialog = component.showProgressDialog(context, "Please wait...");

        HashMap<String, String> user = session.getUserDetails();

        userid = user.get(UserSessionManager.KEY_USER_ID);


        final Address_model hero = heroList.get(position);
        holder.Addres_title.setText(hero.getaddress_title());
        holder.adress.setText(hero.getaddress());
        holder.post_code.setText(hero.getpost_code());
        holder.telephonne.setText(hero.gettelephone());
        holder.country_sp.setText(hero.getcountry());
        holder.state_sp.setText(hero.getstate());
        holder.city_sp.setText(hero.getcity().trim());

       /* holder.linearLayout.setVisibility(View.GONE);


        if (currentPosition == position) {
            holder.linearLayout.setVisibility(View.VISIBLE);
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);
          holder.linearLayout.startAnimation(slideDown);
        }

        holder.Addres_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                //reloding the list
                notifyDataSetChanged();
            }
        });*/



        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog = new AlertDialog.Builder(context);
                LayoutInflater layout = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
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

                title_address.setText(holder.Addres_title.getText());
                address.setText(holder.adress.getText());
                post_code.setText(holder.post_code.getText());
                tlephone.setText(holder.telephonne.getText());

               /* litdropdown(country_sp);
                litdropdown(state_sp);
                litdropdown(city_sp);
*/

                alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title_addres =title_address.getText().toString();
                        String addres =address.getText().toString();
                        String p_code = post_code.getText().toString();
                        String telephone =tlephone.getText().toString();
                        if (title_addres.trim().equals("")|| addres.trim().equals("")|| p_code.trim().equals("") || telephone.trim().equals("")){
                            Toast.makeText(context, "enter all filed", Toast.LENGTH_SHORT).show();
                        }else {

                            Toast.makeText(context, hero.getaddress_id(), Toast.LENGTH_SHORT).show();
                           update_addres(hero.getaddress_id(), title_addres, addres, p_code, telephone, country_id, state_id, city_id);
                            ((Adress_info_fragment) fragment).get_addres(userid);
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
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                int MyVersion = Build.VERSION.SDK_INT;
                if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                    alertDialog.setTitle("Delete this item?");
                    alertDialog.setMessage("Are you sure you want to delete this?");
                    alertDialog.setIcon(R.drawable.email_icon_edittext);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    heroList.remove(position);
                                    notifyItemRemoved(position);
                                    delete_address(userid,hero.getaddress_id());
                                    ((Adress_info_fragment) fragment).get_addres(userid);
                                 ///   Toast.makeText(v.getContext(), "Deleted " + holder.textViewName.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                    alertDialog.setNegativeButton("Cancle",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }
                    );
                    alertDialog.show();
                }else {
                    heroList.remove(position);
                    notifyItemRemoved(position);
                    delete_address(userid,hero.getaddress_id());
                    ((Adress_info_fragment) fragment).get_addres(userid);
                   // Toast.makeText(v.getContext(), "Deleted " + holder.textViewName.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

  /*  public void litdropdown(Spinner spinner) {
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Country One");
        languages.add("Country Two");
        languages.add("Country Three");
        languages.add("Country Four");
        languages.add("Country Five");
        Light_dropdown light_dropdown = new Light_dropdown(context, languages);
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
    public void removeItem(int position) {
        heroList.remove(position);
        notifyItemRemoved(position);
    }


    public class HeroViewHolder extends RecyclerView.ViewHolder {
        TextView Addres_title, adress, post_code, telephonne,
                country_sp, state_sp, city_sp;
        ImageView imageView;
        public RelativeLayout viewBackground;
        public CardView cardViewForeground;
        public Button Delete;
        public Button Edit;
        public TextView Share;

        HeroViewHolder(View itemView) {
            super(itemView);

            Addres_title = (TextView) itemView.findViewById(R.id.textViewName);
            adress = (TextView) itemView.findViewById(R.id.textViewRealName);
            post_code = (TextView) itemView.findViewById(R.id.textViewTeam);
            telephonne = (TextView) itemView.findViewById(R.id.textViewFirstAppearance);
            country_sp = (TextView) itemView.findViewById(R.id.textViewCreatedBy);
            state_sp = (TextView) itemView.findViewById(R.id.textViewPublisher);
            city_sp = (TextView) itemView.findViewById(R.id.textViewBio);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            Delete = (Button) itemView.findViewById(R.id.Delete);
            Edit = (Button) itemView.findViewById(R.id.Edit);
        }
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

                        Dropdown_light_adaptor spinnerAdapter = new Dropdown_light_adaptor(context, heroList);
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
                    ArrayList<Dropdown_model> heroList = new ArrayList<>();

                    for (int i = 0; i < ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        Dropdown_model personUtils = new Dropdown_model();
                        personUtils.setId(jo.getString("id"));
                        personUtils.setName(jo.getString("name"));
                        personUtils.setphone_code(jo.getString("country_id"));
                        heroList.add(personUtils);

                    }

                    if (null != heroList) {
                        //  spinner = (Spinner) findViewById(R.id.spinner);
                        // assert spinner != null;
                        state_sp.setVisibility(View.VISIBLE);

                        Dropdown_light_adaptor spinnerAdapter = new Dropdown_light_adaptor(context, heroList);
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
                                myDialog.dismiss();
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
                        Dropdown_light_adaptor spinnerAdapter = new Dropdown_light_adaptor(context, heroList);
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

    public void update_addres(String user_id, String addres_title, String address, String post_code, String telephoe, String coutry_id, String stateid, String cityid) {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("address_id", user_id));
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

                 //   JSONArray publicationOption_array = new JSONArray(new JSONObject(output).getString("data"));
                   // JSONObject publicationOption_object;
                    String status =new JSONObject(output).getString("message");
                    Toast.makeText(context,  status, Toast.LENGTH_SHORT).show();


                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Toast.makeText(context,   "Internet Connection Is To Week", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.EDIT_ADDRES);
        sendPostReqAsyncTask.execute();
    }

    public void delete_address(String user_id,String address_id){

        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("address_id", address_id));
        }

        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {

                    //   JSONArray publicationOption_array = new JSONArray(new JSONObject(output).getString("data"));
                    // JSONObject publicationOption_object;
                    String status =new JSONObject(output).getString("status");
                    if (status.equals("true")){
                        // Snackbar.make(getView(), "Addres added sucsessfully", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(context,   "Addres delete sucsessfully", Toast.LENGTH_SHORT).show();
                    }else {
                        //Snackbar.make(getView(), "Addres added unsucsessfully", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(context,   "Addres delete unsucsessfully", Toast.LENGTH_SHORT).show();
                    }
                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Toast.makeText(context,   "Internet Connection Is To Week", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.DELETE_ADDRES);
        sendPostReqAsyncTask.execute();

    }

}
