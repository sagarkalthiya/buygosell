package com.trooople.godofhell.buygosell.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trooople.godofhell.buygosell.Adaptor.Catagory_adaptor;
import com.trooople.godofhell.buygosell.Dropdown_Adaptor.Dropdown_light_adaptor;
import com.trooople.godofhell.buygosell.MainActivity;
import com.trooople.godofhell.buygosell.Model.Dropdown_model;
import com.trooople.godofhell.buygosell.Model.catagory_dropdown_model;
import com.trooople.godofhell.buygosell.Provider.Post_Data;
import com.trooople.godofhell.buygosell.Provider.Url_Endpoint;
import com.trooople.godofhell.buygosell.R;
import com.trooople.godofhell.buygosell.Tools.Component;
import com.trooople.godofhell.buygosell.Tools.RecyclerItemClickListener;
import com.trooople.godofhell.buygosell.Tools.ResizableImageView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Catagory_fragmet extends Fragment {

    private RecyclerView mRecyclerView;
    private Catagory_adaptor mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Context context;
    ProgressDialog progressDialog;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragmet_catagory, null);
        progressDialog = new ProgressDialog(getContext());

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setTitle("Catagory");

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_left_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Component component = new Component();
                component.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).fregment(new Home_fragment(),false);
            }
        });

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.catagory_rv);
        cat();
        return rootView;
    }


    private void cat() {

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loding in. Please wait.");
        progressDialog.setCancelable(false);
        progressDialog.show();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Post_Data sendPostReqAsyncTask = new Post_Data(new Post_Data.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                try {
                    JSONArray ja = new JSONArray(output);
                    JSONObject jo;
                    ArrayList<catagory_dropdown_model> cat = new ArrayList<>();
                    final ArrayList<String> items = new ArrayList<String>();

                    JSONArray participants = ja;
                    for (int i = 0; i < participants.length(); i++) {
                        jo = participants.getJSONObject(i);
                        catagory_dropdown_model personUtils = new catagory_dropdown_model();
                        personUtils.setId(jo.getString("category_id"));
                        // items.add(jo.getString("category_name_en"));
                        personUtils.setName(jo.getString("category_name"));
                        personUtils.setEn_name(jo.getString("category_name_en"));
                        personUtils.setCat_img(Url_Endpoint.PRODUCT_IMAGE_URL+ jo.getString("category_picture"));
                        personUtils.setCat_icon(Url_Endpoint.PRODUCT_IMAGE_URL+ jo.getString("category_icon"));
                        cat.add(personUtils);
                        progressDialog.dismiss();
                    }

                    if (null != cat) {

                        mRecyclerView.setHasFixedSize(true);
                        int mNoOfColumns = Utility.calculateNoOfColumns(getActivity());
                        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(mNoOfColumns, 1);
                        mRecyclerView.setLayoutManager(gaggeredGridLayoutManager);
                        mRecyclerView.addOnItemTouchListener(
                                new RecyclerItemClickListener(context, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        // do whatever
                                        ((MainActivity)getActivity()).fregment(new Product_fragment(),true);
                                        // ((MainActivity)getActivity()).back_fregment(new Description_fragment());
                                    }

                                    @Override
                                    public void onLongItemClick(View view, int position) {
                                        // do whatever
                                    }
                                })
                        );
                        mAdapter = new Catagory_adaptor(getContext(), cat);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, nameValuePairs, Url_Endpoint.TOP_CATAGORY);
        sendPostReqAsyncTask.execute();

    }


    private void buildRecyclerView() {


    }


    public static class Utility {
        public static int calculateNoOfColumns(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (dpWidth / 180);
            return noOfColumns;
        }
    }




}