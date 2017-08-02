package com.cws.bleproject.model;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author: sslong
 * @package: com.veryfit.
 * @description: ${TODO}{一句话描述该类的作用}
 * @date: 2016/5/18 15:58
 */
public class DialogUtil {
    public static final int CAMERA = 1;
    public static final int PHOTOZOOM = 2;
    public static final int PHOTORESOULT = 3;
    /*拍照*/
    public static String photoTemp = "/temp.png";
    public static String photoPath = "/avatar.jpg";
    private static Toast toast;
    private static AlertDialog waitDialog;
    public static void showToast(Context context, String msg) {

        if (context == null) {
            return;
        }

        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(msg);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void showToast(Context context, int res) {
        if (context == null) {
            return;
        }
        String msg = context.getString(res);
        showToast(context, msg);
    }

    public static void showWaitDialog(Activity activity, String msg){

        waitDialog= new ProgressDialog(activity);
        waitDialog.setMessage(msg);
        waitDialog.show();

    }

    public static void updateWaitDialog(String msg){
        if (waitDialog!=null&& waitDialog.isShowing()) {
            waitDialog.setMessage(msg);
        }
    }



    public static void closeAlertDialog() {
        if (waitDialog != null && waitDialog.isShowing()) {
            waitDialog.dismiss();
            waitDialog = null;
        }
    }

    /**
     * @param context
     * @return true activity not distory
     */
    public static boolean checkActivityisDestroyed(Context context) {
//        boolean isActivity=context instanceof Activity;
        if (context == null) return false;
        if (!(context instanceof Activity)) {
//            throw  new IllegalArgumentException("context is not activity");
            return false;
        }
        Activity activity = (Activity) context;

        if (activity.isDestroyed()) {
//            throw  new IllegalArgumentException("activity is Destroyed");
            return false;
        }
        return true;
    }




}
