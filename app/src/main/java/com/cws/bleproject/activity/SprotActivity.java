package com.cws.bleproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cws.bleproject.R;
import com.veryfit.multi.nativedatabase.HealthSport;
import com.veryfit.multi.nativedatabase.HealthSportItem;
import com.veryfit.multi.nativeprotocol.ProtocolUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SprotActivity extends AppCompatActivity implements OnClickListener {
	private Button btnSportByDate,btnSportItemByDate,btnAll,btnWeek,btnMonth,btnYear;
	private TextView tvData;
	private int year,month,day;
	private StringBuffer Sb=new StringBuffer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport);
		initView();
		addListener();
	}
	
	public void initView(){
		Calendar mCalendar = Calendar.getInstance();
		year = mCalendar.get(Calendar.YEAR);
		month=mCalendar.get(Calendar.MONTH);
		day = mCalendar.get(Calendar.DAY_OF_MONTH);
		btnAll=(Button)findViewById(R.id.btn_sport_all);
		btnSportByDate=(Button)findViewById(R.id.btn_sport_bydate);
		btnSportItemByDate=(Button)findViewById(R.id.btn_sportitem_bydate);
		btnWeek=(Button)findViewById(R.id.btn_sport_week);
		btnMonth=(Button)findViewById(R.id.btn_sport_month);
		btnYear=(Button)findViewById(R.id.btn_sport_year);
		tvData=(TextView)findViewById(R.id.tv_data);
	}
	
	
	public void initData(){
		
	}
	
	public void addListener(){
		btnAll.setOnClickListener(this);
		btnMonth.setOnClickListener(this);
		btnSportByDate.setOnClickListener(this);
		btnSportItemByDate.setOnClickListener(this);
		btnWeek.setOnClickListener(this);
		btnYear.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.btn_sport_bydate:
			//According to the date of access to data only need to pass the day of the day you want to query the day to facilitate debugging to today as an example to get the movement data must be synchronized before synchronization will have the corresponding day of the movement data
			@SuppressWarnings("deprecation")
            HealthSport sport = ProtocolUtils.getInstance().getHealthSport(new Date(year, month, day));//(月份从0开始例如8月传7)
			if(sport!=null){
				tvData.setText(sport.toString());
			}else {
				tvData.setText("No data today");
			}
			break;
		case R.id.btn_sportitem_bydate:
			Sb.delete(0, Sb.length());
			//According to the date to obtain the data item only need to pass the day to query the day of the day can be fixed every day 96 every 15 minutes there will be an item
			@SuppressWarnings("deprecation")
            List<HealthSportItem> items = ProtocolUtils.getInstance().getHealthSportItem(new Date(year, month, day));//(月份从0开始例如8月传7)
			if(items!=null){
				for (int i = 0; i < items.size(); i++) {
					Sb.append("item:"+i+"   "+items.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_sport_week:
			Sb.delete(0, Sb.length());
			//Get the week data parameter 1: (the current week pass 0 on a week pass -1 and then on a week pass -2, and so on)
			List<HealthSport> weekSprots= ProtocolUtils.getInstance().getWeekHealthSport(0);
			if(weekSprots!=null&&weekSprots.size()>0){
				for (int i = 0; i < weekSprots.size(); i++) {
					Sb.append(weekSprots.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_sport_month:
			Sb.delete(0, Sb.length());
			//Get the monthly data parameters 1: (the current month 0 on the last month and then on the January-2, and so on)
			List<HealthSport> monthSprots= ProtocolUtils.getInstance().getMonthHealthSprort(0);
			if(monthSprots!=null&&monthSprots.size()>0){
				for (int i = 0; i < monthSprots.size(); i++) {
					Sb.append(monthSprots.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_sport_year:
			Sb.delete(0, Sb.length());
			//Get the annual data parameters 1: (then Chou Chou 0 on a year pass again on a year pass -2, and so on)
			List<HealthSport> yearSprots= ProtocolUtils.getInstance().getYearHealthSport(0);
			if(yearSprots!=null&&yearSprots.size()>0){
				for (int i = 0; i < yearSprots.size(); i++) {
					Sb.append(yearSprots.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;
		case R.id.btn_sport_all:
			Sb.delete(0, Sb.length());
                //Get the aggregated data already stored in all the databases and return a few summaries for a few days
			List<HealthSport> sports= ProtocolUtils.getInstance().getAllHealthSport();
			if(sports!=null&&sports.size()>0){
				for (int i = 0; i < sports.size(); i++) {
					Sb.append(sports.get(i).toString()+"\n\n");
				}
				tvData.setText(Sb.toString());
			}
			break;

		default:
			break;
		}
	}
}
