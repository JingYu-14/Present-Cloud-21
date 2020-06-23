package com.example.administrator.daoyunapplication.Activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


@SuppressLint("Registered")
public class SetDeadlineActivity extends AppCompatActivity {
    private static int sHour;
    private static int sMinute;
    private static int sYear;
    private static int sMonth;
    private static int sDay;
    private User u;
    private Classes c;
    private String title;
    private String content;
//    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_deadline);
        c=(Classes)getIntent().getSerializableExtra("classes");
        u=(User)getIntent().getSerializableExtra("user");
        title=getIntent().getStringExtra("title");
        content=getIntent().getStringExtra("content");
//        AlarmManager mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);//设置时区
//        mAlarmManager.setTimeZone("Asia/Shanghai");// Asia/Taipei//GMT+08:00
        TimePicker timePicker = (TimePicker)findViewById(R.id.set_time);
        timePicker.setIs24HourView(true);
        final Time t = new Time();
        t.setToNow();
        timePicker.setHour(t.hour);
        timePicker.setMinute(t.minute);
        sYear=t.year;
        sMonth=t.month+1;
        sDay=t.monthDay;
        sHour=t.hour;
        sMinute=t.minute;
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.YEAR,t.year);
        mCalendar.set(Calendar.MONTH,t.month);
        mCalendar.set(Calendar.DAY_OF_MONTH,t.monthDay);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                sHour=hourOfDay;
                sMinute=minute;
//                Toast.makeText(SetDeadlineActivity.this,sHour+"时"+sMinute+"分", Toast.LENGTH_SHORT).show();
            }
        });
        DatePicker datePicker =(DatePicker)findViewById(R.id.set_date);
        datePicker.init(t.year, t.month, t.monthDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                sYear=year;
                sMonth=monthOfYear+1;
                sDay=dayOfMonth;
            }
        });
        datePicker.setMinDate(mCalendar.getTimeInMillis());
        Button timeDeadline = (Button)findViewById(R.id.time_deadline);
        timeDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((sYear==t.year)&&(sMonth==t.month+1)&&(sDay==t.monthDay)&&((sHour<t.hour)||((sHour==t.hour)&&(sMinute<=t.minute)))){
                        Toast.makeText(SetDeadlineActivity.this,"截止日期应该大于当前时间", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SetDeadlineActivity.this,sYear+"年"+sMonth+"月"+sDay+"日"+sHour+"时"+sMinute+"分", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SetDeadlineActivity.this,ActivityCreateTask.class);
                    String s=sYear+"-"+sMonth+"-"+sDay+"  "+sHour+":"+sMinute+":00";
                    intent.putExtra("time",s);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("classes", c);
                    bundle.putSerializable("user",u);
                    bundle.putString("title",title);
                    bundle.putString("content",content);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

}
