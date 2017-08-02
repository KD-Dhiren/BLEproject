package com.cws.bleproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cws.bleproject.R;
import com.cws.bleproject.activity.DisPlayModeActivity;
import com.cws.bleproject.activity.ScanDeviceActivity;
import com.cws.bleproject.model.ActionModel;
import com.cws.bleproject.view.BufferDialog;
import com.veryfit.multi.nativeprotocol.Protocol;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dhiren khatik on 31-07-2017.
 */

public class ActionAdapter extends BaseAdapter {

    Context context;
    ArrayList<ActionModel> actionList;
    LayoutInflater inflater;
    private BufferDialog dialog;


    public ActionAdapter(Context context, ArrayList<ActionModel> actionList) {
        this.context = context;
        this.actionList = actionList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialog = new BufferDialog(context);
    }

    @Override
    public int getCount() {
        return actionList.size();
    }

    @Override
    public Object getItem(int position) {
        return actionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_ble_action, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        createAction(holder, actionList.get(position));
        return view;
    }

    private void createAction(ViewHolder holder, final ActionModel am) {
        holder.txt_action_text.setText(am.getActionName());

        /*int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
//        holder.ll_circular.setBackgroundColor(randomAndroidColor);
        holder.ll_circular.setBackgroundResource(R.drawable.circle_action);

        GradientDrawable drawable = (GradientDrawable) holder.ll_circular.getBackground();
        drawable.setColor(randomAndroidColor);*/
        /*
         actinList.add(new ActionModel("Unbind", 1));
        actinList.add(new ActionModel("Disconnect", 2));
        actinList.add(new ActionModel("Re-Connect", 3));
        actinList.add(new ActionModel("Display-Mode", 4));
        actinList.add(new ActionModel("Reboot", 4));
        actinList.add(new ActionModel("BLE device Info", 5));
        actinList.add(new ActionModel("Alarm", 6));
        actinList.add(new ActionModel("Music Control", 7));
        actinList.add(new ActionModel("Camera Control", 8));
        actinList.add(new ActionModel("Call Control", 9));
        actinList.add(new ActionModel("UserInfo", 10));
        actinList.add(new ActionModel("SendLog Info", 11));
        actinList.add(new ActionModel("Sync Health Info", 12));
        actinList.add(new ActionModel("Call Reminders", 13));
        actinList.add(new ActionModel("Anti Lost Reminders", 14));
        actinList.add(new ActionModel("Emergency Help", 15));
        actinList.add(new ActionModel("Looking for a CellPhone", 16));
        actinList.add(new ActionModel("Do Not Disturb mode", 17));
        */

        holder.ll_circular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (am.getActionNumber()) {
                    case 1:
                        // Unbonded when the device is not connected to the case only to delete the data and delete some of the binding state, reset the APP is not binding device status
                        if (ProtocolUtils.getInstance().isAvailable() != ProtocolUtils.SUCCESS) {// To determine whether the device is connected
                            ProtocolUtils.getInstance().setBindMode(Protocol.SYS_MODE_SET_NOBIND);
                            Calendar mCalendar1 = Calendar.getInstance();
                            int year = mCalendar1.get(Calendar.YEAR);
                            int month = mCalendar1.get(Calendar.MONTH);
                            int day = mCalendar1.get(Calendar.DAY_OF_MONTH);
                            ProtocolUtils.getInstance().enforceUnBind(new Date(year, month, day));// (Month from 0 to start in August, such as August 7)
                            //Intent intent1 =
                            context.startActivity(new Intent(context, ScanDeviceActivity.class));

                        } else {
                            // If the device is connected, use the following method to unbind
                            ProtocolUtils.getInstance().setBindMode(Protocol.SYS_MODE_SET_NOBIND);
                            ProtocolUtils.getInstance().setUnBind();
                        }
                        break;
                    case 2:
                        // Set disconnection
                        ProtocolUtils.getInstance().setNewUnConnect();
                        break;
                    case 3:
                        // Set reconnection
                        ProtocolUtils.getInstance().reConnect();
                        break;
                    case 4:
                        Intent disPlayIntent = new Intent(context, DisPlayModeActivity.class);
                        context.startActivity(disPlayIntent);
                        break;
                    case 5:
                        dialog.show();
                        ProtocolUtils.getInstance().reStartDevice();
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                    case 10:
                        break;
                    case 11:
                        break;
                    case 12:
                        break;
                    case 13:
                        break;
                    case 14:
                        break;
                    case 15:
                        break;
                    case 16:
                        break;
                    case 17:
                        break;
                    case 18:
                        break;
                    case 19:
                        break;
                    case 20:
                        break;
                    case 21:
                        break;
                    case 22:
                        break;
                    case 23:
                        break;
                    case 24:
                        break;
                    case 25:
                        break;
                }
            }
        });

    }

    private class ViewHolder {
        TextView txt_action_text;
        LinearLayout ll_circular;

        public ViewHolder(View view) {
            ll_circular = (LinearLayout) view.findViewById(R.id.ll_circular);
            txt_action_text = (TextView) view.findViewById(R.id.txt_action_text);
        }
    }
}
