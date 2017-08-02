package com.cws.bleproject.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cws.bleproject.R;


@SuppressLint("HandlerLeak")
public class BufferDialog {
    private Dialog mDialog;
    private TextView titleView;
    private ProgressBar progressBar;

    public BufferDialog(Activity activity, String title) {
        mDialog = new Dialog(activity, R.style.dialog_translucent);
        mDialog.setContentView(R.layout.dialog_buffer);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        titleView = mDialog.findViewById(R.id.buffer_text_title);
        progressBar = mDialog.findViewById(R.id.pb_dialog);
        titleView.setText(title);
    }

    public BufferDialog(Context context) {
        mDialog = new Dialog(context, R.style.dialog_translucent);
        mDialog.setContentView(R.layout.dialog_buffer);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        titleView = (TextView) mDialog.findViewById(R.id.buffer_text_title);
        progressBar = (ProgressBar) mDialog.findViewById(R.id.pb_dialog);
    }

    public boolean isShow() {
        return mDialog.isShowing();
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setVisibility() {
        progressBar.setVisibility(View.GONE);
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public void show() {
        try {
            mDialog.show();
        } catch (Exception e) {
        }
    }
}
