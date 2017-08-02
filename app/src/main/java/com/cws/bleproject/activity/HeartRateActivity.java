package com.cws.bleproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cws.bleproject.R;
import com.veryfit.multi.nativedatabase.HealthHeartRate;
import com.veryfit.multi.nativedatabase.HealthHeartRateItem;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HeartRateActivity extends AppCompatActivity implements OnClickListener {
	private Button btnHeartRateByDate,btnHeartRateItemByDate,btnAll,btnWeek,btnMonth,btnYear;
	private TextView tvData;
	private int year,month,day;
	private StringBuffer Sb=new StringBuffer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_heartrate);
		initView();
		addListener();
	}
	
	public void initView(){
		Calendar mCalendar = Calendar.getInstance();
		year = mCalendar.get(Calendar.YEAR);
		month=mCalendar.get(Calendar.MONTH);
		day = mCalendar.get(Calendar.DAY_OF_MONTH);
		btnAll=(Button)findViewById(R.id.btn_heartrate_all);
		btnHeartRateByDate=(Button)findViewById(R.id.btn_heartrate_bydate);
		btnHeartRateItemByDate=(Button)findViewById(R.id.btn_heartrateitem_bydate);
		btnWeek=(Button)findViewById(R.id.btn_heartrate_week);
		btnMonth=(Button)findViewById(R.id.btn_heartrate_month);
		btnYear=(Button)findViewById(R.id.btn_heartrate_year);
		tvData=(TextView)findViewById(R.id.tv_data);
		
	}
	
	
	public void initData(){
		
	}
	
	public void addListener(){
		btnAll.setOnClickListener(this);
		btnMonth.setOnClickListener(this);
		btnHeartRateByDate.setOnClickListener(this);
		btnHeartRateItemByDate.setOnClickListener(this);
		btnWeek.setOnClickListener(this);
		btnYear.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_heartrate_bydate:
			//According to the date of access to data only need to pass the date of the day you want to query, to facilitate the debugging of today as an example to get the movement data must be synchronized before synchronization will have the corresponding day of heart rate data (provided that the bracelet With heart rate function and heart rate data)
			@SuppressWarnings("deprecation")
            HealthHeartRate heartRate = ProtocolUtils.getInstance().getHealthRate(new Date(year, month, day));//(Month from 0 to the beginning of August, such as August 7)
			if(heartRate!=null){
				tvData.setText(heartRate.toString());
			}else {
				tvData.setText("No data today");
			}
			break;
		case R.id.btn_heartrateitem_bydate:
			Sb.delete(0, Sb.length());
			//According to the date to obtain the data item only need to pass the day of the day to query the item can be item (brains to have heart rate data), to today as an example
			//Heart rate data of the number of items is not fixed, usually separated by five minutes or so will collect a heart rate data, if there is no bracelet, then there will be no heart rate data and item
			@SuppressWarnings("deprecation")
			//The time point of the heart rate value at each point in time is the offsetMinute calculated from the offsetMinute in item. The offsetMinute is the number of minutes from the previous item. The offsetMinute of the first item is the number of minutes from zero
                    List<HealthHeartRateItem> items = ProtocolUtils.getInstance().getHeartRateItems(new Date(year, month, day));//(月份从0开始例如8月传7)
			if(items!=null){
				for (int i = 0; i < items.size(); i++) {
					Sb.append("item:"+i+"   "+items.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_heartrate_week:
			Sb.delete(0, Sb.length());
			//Get the week data parameter 1: (the current week pass 0 on a week pass -1 and then on a week pass -2, and so on)
			List<HealthHeartRate> weekHeartRates= ProtocolUtils.getInstance().getWeekHealthHeartRate(0);
			if(weekHeartRates!=null&&weekHeartRates.size()>0){
				for (int i = 0; i < weekHeartRates.size(); i++) {
					Sb.append(weekHeartRates.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_heartrate_month:
			Sb.delete(0, Sb.length());
			//Get the monthly data parameters 1: (the current month 0 on the last month and then on the January-2, and so on)
			List<HealthHeartRate> monthHeartRates= ProtocolUtils.getInstance().getMonthHeartRate(0);
			if(monthHeartRates!=null&&monthHeartRates.size()>0){
				for (int i = 0; i < monthHeartRates.size(); i++) {
					Sb.append(monthHeartRates.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_heartrate_year:
			Sb.delete(0, Sb.length());
			//Get the annual data parameters 1: (then Chou Chou 0 on a year pass again on a year pass -2, and so on)
			List<HealthHeartRate> yearHealthHeartRates= ProtocolUtils.getInstance().getYearHealthHeartRate(0);
			if(yearHealthHeartRates!=null&&yearHealthHeartRates.size()>0){
				for (int i = 0; i < yearHealthHeartRates.size(); i++) {
					Sb.append(yearHealthHeartRates.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_heartrate_all:
			Sb.delete(0, Sb.length());
			//Get the aggregated data already stored in all the databases and return a few summaries for a few days
			List<HealthHeartRate> healthHeartRates= ProtocolUtils.getInstance().getAllHealthRate();
			if(healthHeartRates!=null&&healthHeartRates.size()>0){
				for (int i = 0; i < healthHeartRates.size(); i++) {
					Sb.append(healthHeartRates.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;

		default:
			break;
		}
	}
	
	

}
