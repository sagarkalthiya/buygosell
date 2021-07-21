package com.trooople.godofhell.buygosell.Tools;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.trooople.godofhell.buygosell.Provider.Post_Data;

import java.time.format.DateTimeFormatter;

import static android.support.constraint.Constraints.TAG;

public class Component {

    public void closeKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }



    public static ProgressDialog showProgressDialog(Context context ,String msg){
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(msg);
        m_Dialog.setProgress(2000);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        return m_Dialog;
    }




}
