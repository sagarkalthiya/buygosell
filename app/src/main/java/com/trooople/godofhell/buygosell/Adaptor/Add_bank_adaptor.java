package com.trooople.godofhell.buygosell.Adaptor;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.trooople.godofhell.buygosell.Fragment.Payment_info_fragment;
import com.trooople.godofhell.buygosell.Fragment.Save_bank_fragment;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.Add_card_model;
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

public class Add_bank_adaptor extends RecyclerView.Adapter<Add_bank_adaptor.HeroViewHolder> {


    public List<Add_card_model> heroList;
    private Context context;
    private AlertDialog.Builder alertDialog;;

    ViewGroup parent;

    EditText bank_name,owner_name,iban_number;
    Spinner country_sp, state_sp, city_sp;
    public static int currentPosition = 0;

    Component component = new Component();
    ProgressDialog myDialog;
    String country_id,state_id,city_id,country_name,state_name,city_name;
    UserSessionManager session;
    String userid;
    private Fragment fragment;

    public Add_bank_adaptor(List<Add_card_model> heroList, Context context,Fragment fragment) {
        this.heroList = heroList;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public HeroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_add_bank_card, parent, false);
        return new HeroViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HeroViewHolder holder, final int position) {
        session = new UserSessionManager(context);
        myDialog = component.showProgressDialog(context, "Please wait...");

        HashMap<String, String> user = session.getUserDetails();

        userid = user.get(UserSessionManager.KEY_USER_ID);


        final Add_card_model hero = heroList.get(position);
      //  holder.Addres_title.setText(hero.getbank_id());
        holder.bank_name_title.setText(hero.getbank_name());
        holder.bank_name.setText(hero.getbank_name());
        holder.owner_name.setText(hero.getowner_name());
        holder.iban.setText(hero.getiban_name());



        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog = new AlertDialog.Builder(context);
                LayoutInflater layout = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layout.inflate(R.layout.dailog_add_bank, null);
                alertDialog.setView(popupView);
                alertDialog.setCancelable(false);


                bank_name =(EditText) popupView.findViewById(R.id.dailog_bank_name_et);
                owner_name =(EditText) popupView.findViewById(R.id.dailog_bank_owner_bane_et);
                iban_number =(EditText) popupView.findViewById(R.id.dailog_bank_iban_number_et);

                bank_name.setText(holder.bank_name.getText());
                owner_name.setText(holder.owner_name.getText());
                iban_number.setText(holder.iban.getText());


                alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title_addres =bank_name.getText().toString();
                        String addres =owner_name.getText().toString();
                        String p_code = iban_number.getText().toString();
                        if (title_addres.trim().equals("")|| addres.trim().equals("")|| p_code.trim().equals("")){
                            Toast.makeText(context, "enter all filed", Toast.LENGTH_SHORT).show();
                        }else {
                            update_bank(userid, hero.getbank_id(), title_addres, addres, p_code);
                            ((Payment_info_fragment) fragment).get_my_banks(userid);
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

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
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
                                    remove_bank(userid,hero.getbank_id());
                                    heroList.remove(position);
                                    notifyItemRemoved(position);
                                  //  delete_address(userid,hero.getaddress_id());
                                 //((Adress_info_fragment) fragment).get_addres(userid);
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
                    remove_bank(userid,hero.getbank_id());
                    heroList.remove(position);
                    notifyItemRemoved(position);

                    // Toast.makeText(v.getContext(), "Deleted " + holder.textViewName.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return heroList.size();
    }

    public void removeItem(int position) {
        heroList.remove(position);
        notifyItemRemoved(position);
    }


    public class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView bank_name_title,bank_name,owner_name,iban;
        Button delete,edit;

        HeroViewHolder(View itemView) {
            super(itemView);

            bank_name_title = (TextView) itemView.findViewById(R.id.bank_name_title_txt);
            bank_name = (TextView) itemView.findViewById(R.id.bank_name_txt);
            owner_name = (TextView) itemView.findViewById(R.id.owner_txt);
            iban = (TextView) itemView.findViewById(R.id.iban_txt);

            edit = (Button) itemView.findViewById(R.id.Edit_add_bank_btn);
            delete = (Button) itemView.findViewById(R.id.Delete_add_bank_btn);

        }
    }


    public void update_bank(String user_id, String bank_id, String bank_name, String owner_name, String iban_number) {
        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("bank_id", bank_id));
            nameValuePairs.add(new BasicNameValuePair("bank_name", bank_name));
            nameValuePairs.add(new BasicNameValuePair("account_holder", owner_name));
            nameValuePairs.add(new BasicNameValuePair("bank_iban", iban_number));
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
        }, nameValuePairs, Url_Endpoint.EDIT_BANK);
        sendPostReqAsyncTask.execute();
    }

    public void remove_bank(String user_id,String address_id){

        myDialog.show();

        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        //  nameValuePairs.add(new BasicNameValuePair("cat_id",categry_id));

        if (!user_id.equals("")) {
            nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
            nameValuePairs.add(new BasicNameValuePair("bank_id", address_id));
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
                        Toast.makeText(context,   "Bank delete sucsessfully", Toast.LENGTH_SHORT).show();
                        ((Payment_info_fragment) fragment).get_my_banks(userid);
                    }else {
                        //Snackbar.make(getView(), "Addres added unsucsessfully", Snackbar.LENGTH_LONG).show();
                        Toast.makeText(context,   "Bank delete unsucsessfully", Toast.LENGTH_SHORT).show();
                    }
                    myDialog.dismiss();
                } catch (JSONException e) {
                    myDialog.dismiss();
                    Toast.makeText(context,   "Internet Connection Is To Week", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, nameValuePairs, Url_Endpoint.REMOVE_BANK);
        sendPostReqAsyncTask.execute();

    }

}