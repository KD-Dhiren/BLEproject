package com.cws.bleproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cws.bleproject.R;
import com.veryfit.multi.nativedatabase.healthSleep;
import com.veryfit.multi.nativedatabase.healthSleepItem;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SleepActivity extends AppCompatActivity implements OnClickListener {
	private Button btnSleepByDate,btnSleepItemByDate,btnAll,btnWeek,btnMonth,btnYear;
	private TextView tvData;
	private int year,month,day;
	private StringBuffer Sb=new StringBuffer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sleep);
		initView();
		addListener();
	}
	
	public void initView(){
		Calendar mCalendar = Calendar.getInstance();
		year = mCalendar.get(Calendar.YEAR);
		month=mCalendar.get(Calendar.MONTH);
		day = mCalendar.get(Calendar.DAY_OF_MONTH);
		btnAll=(Button)findViewById(R.id.btn_sleep_all);
		btnSleepByDate=(Button)findViewById(R.id.btn_sleep_bydate);
		btnSleepItemByDate=(Button)findViewById(R.id.btn_sleepitem_bydate);
		btnWeek=(Button)findViewById(R.id.btn_sleep_week);
		btnMonth=(Button)findViewById(R.id.btn_sleep_month);
		btnYear=(Button)findViewById(R.id.btn_sleep_year);
		tvData=(TextView)findViewById(R.id.tv_data);
		
	}
	
	
	public void initData(){
		
	}
	
	public void addListener(){
		btnAll.setOnClickListener(this);
		btnMonth.setOnClickListener(this);
		btnSleepByDate.setOnClickListener(this);
		btnSleepItemByDate.setOnClickListener(this);
		btnWeek.setOnClickListener(this);
		btnYear.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_sleep_bydate:
			//According to the date of access to data only need to pass the day of the day you want to query the day to facilitate debugging to today as an example to get the sleep data must be synchronized before synchronization will have a corresponding day of sleep data
			@SuppressWarnings("deprecation")
                healthSleep sleep = ProtocolUtils.getInstance().getHealthSleep(new Date(year, month, day));//(Month from 0 to the beginning of August, such as August 7)
			if(sleep!=null){
				tvData.setText(sleep.toString());
			}else {
				tvData.setText("No data today");
			}
			break;
		case R.id.btn_sleepitem_bydate:
			Sb.delete(0, Sb.length());
			//According to the date of access to the data item only need to pass the day to find the day of the query can be (without ring sleep when there is no sleep data) sleep data item number of fixed length is not fixed
			@SuppressWarnings("deprecation")
            List<healthSleepItem> items = ProtocolUtils.getInstance().getHealthSleepItem(new Date(year, month, day));//(Month from 0 to the beginning of August, such as August 7)
			if(items!=null){
				for (int i = 0; i < items.size(); i++) {
					Sb.append("item:"+i+"   "+items.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_sleep_week:
			Sb.delete(0, Sb.length());
			//Get the week data parameter 1: (the current week pass 0 on a week pass -1 and then on a week pass -2, and so on)
			List<healthSleep> weekSleeps= ProtocolUtils.getInstance().getWeekHealthSleep(0);
			if(weekSleeps!=null&&weekSleeps.size()>0){
				for (int i = 0; i < weekSleeps.size(); i++) {
					Sb.append(weekSleeps.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_sleep_month:
			Sb.delete(0, Sb.length());
			//Get the monthly data parameters 1: (the current month 0 on the last month and then on the January-2, and so on)
			List<healthSleep> monthSleeps= ProtocolUtils.getInstance().getMonthHealthSleep(0);
			if(monthSleeps!=null&&monthSleeps.size()>0){
				for (int i = 0; i < monthSleeps.size(); i++) {
					Sb.append(monthSleeps.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_sleep_year:
			Sb.delete(0, Sb.length());
			//Get the annual data parameters 1: (then Chou Chou 0 on a year pass again on a year pass -2, and so on)
			List<healthSleep> yearSleeps= ProtocolUtils.getInstance().getYearHealthSleep(0);
			if(yearSleeps!=null&&yearSleeps.size()>0){
				for (int i = 0; i < yearSleeps.size(); i++) {
					Sb.append(yearSleeps.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_sleep_all:
			Sb.delete(0, Sb.length());
			//Get the aggregated data already stored in all the databases and return a few summaries for a few days
			List<healthSleep> sleeps= ProtocolUtils.getInstance().getAllHealthSleep();
			if(sleeps!=null&&sleeps.size()>0){
				for (int i = 0; i < sleeps.size(); i++) {
					Sb.append(sleeps.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;

		default:
			break;
		}
	}
	
	

}
