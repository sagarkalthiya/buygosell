package com.trooople.godofhell.buygosell.Provider;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class Get_Data extends AsyncTask<String, Void, String> {

    final String ServerURL = "http://buygosell.com/api/v1/" ;

    public List<NameValuePair> nameValuePairs = null;
    Context context;
    public String method = null;

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }

    public Get_Data.AsyncResponse delegate = null;


    public Get_Data(Get_Data.AsyncResponse delegate,  String method){
        this.delegate = delegate;
       // this.nameValuePairs = nameValuePairs;
        this.method = method;
    }

    @Override
    protected String doInBackground(String... params) {

        try {



            String Response;

            HttpClient httpClient = new DefaultHttpClient();

            HttpGet httpPost = new HttpGet(ServerURL+method);

        //    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse httpResponse = httpClient.execute(httpPost);

          /*  HttpGet httpget = new HttpGet(ServerURL+method);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            Response = httpClient.execute(httpget, responseHandler);*/

            // HttpEntity httpEntity = httpResponse.getEntity();

            Response = EntityUtils.toString(httpResponse.getEntity());

            return Response;

        } catch (ClientProtocolException e) {


        } catch (IOException e) {

        }
        return "";
    }




    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        android.util.Log.d("----->",result);
        delegate.processFinish(result);

    }
}