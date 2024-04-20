package com.bagoshort.core.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ShowUtil {

    //Toast提示
    public static void showToast(Context context,String msg){
        Toast toast =  Toast.makeText(context,msg,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //底部提示框
    public static void showSnack(View view,String msg){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

}
