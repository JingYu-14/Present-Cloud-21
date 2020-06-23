package com.example.administrator.daoyunapplication.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityListResourceAdapter;
import com.example.administrator.daoyunapplication.Model.Sign;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivitySignDetail extends AppCompatActivity {
    private int totalnum=0;
    List<Sign> mSignList;
    private LinearLayout lv ;
    ImageView im;
    View iim;
    TextView t;
    TextView tt;
    User u;
    private LinearLayout lt;
    private LinearLayout ll;
    private LinearLayout lm;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail);
        Intent intent =getIntent();
        mSignList = new ArrayList<>();
        lv= (LinearLayout) findViewById(R.id.lv);
        ll=(LinearLayout)findViewById(R.id.have_sign);
        String searchtime=intent.getStringExtra("searchtime");
        u=(User)intent.getSerializableExtra("user");
        int classid=intent.getIntExtra("classid",0);
        int studentsNum=intent.getIntExtra("studentsnum",0);
        Sign ss[]=new Sign[studentsNum];
        getSignDetail(searchtime,ss,classid);
//        TextView tv=(TextView)findViewById(R.id.not_sign);
//        TextView ttv=(TextView)findViewById(R.id.sign);
//        Log.e("signNum",String.valueOf(signNum));
//        if(studentsNum==signNum){
//            tv.setVisibility(View.GONE);
//        }
//        if(signNum==0){
//            ttv.setVisibility(View.GONE);
//        }
    }


    private void getSignDetail(String searchTime, final Sign ss[],int classid){//教师获取签到记录
        final OkHttpClient client = new OkHttpClient();
        String path="http://129.211.87.192/daoyunapi/public/index.php/";
        path = path + "teacherSigns";
        int pagenum=1;
        int pagesize=100;
        String format = String.format(path+"?id="+classid+"&pagenum="+pagenum+"&pagesize="+pagesize+"&searchtime="+searchTime);
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
//        final Request build1 = new Request.Builder().url(format).build();
        Log.e("token",u.getToken());
        final Request build1 = new Request.Builder().url(format).header("Authorization",u.getToken()).build();
        client.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ActivitySignDetail.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(ActivitySignDetail.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String info = response.body().string();//获取服务器返回的json格式数据

                Log.e("TAG", "onResponse: "+info );
                JsonObject jsonObject = new JsonParser().parse(info).getAsJsonObject();
                JsonObject jsonObjectMeta =jsonObject.get("meta").getAsJsonObject();
                int code = jsonObjectMeta.get("status").getAsInt();
                String msg="";
                msg=jsonObjectMeta.get("msg").getAsString();

                if (200==code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                {
                    //这边要注意，获取的是用户对象getAsString();不行.getAsJsonObject();用这个
                    JsonObject result = jsonObject.get("data").getAsJsonObject();
                    JsonArray signsArray = result.get("signs").getAsJsonArray();//取出用户信息
                   totalnum=result.get("total").getAsInt();
                    Log.e("totalnum1", String.valueOf(totalnum));
                    for(int i=0;i<signsArray.size();i++){
                        JsonObject re=signsArray.get(i).getAsJsonObject();
                        ss[i]=new Sign(
                                re.get("id").getAsInt(),
                                re.get("sname").getAsString(),
                                re.get("class_id").getAsInt(),
                                re.get("time").getAsString(),
                                re.get("sno").getAsInt(),
                                re.get("state").getAsBoolean(),
                                re.get("account").getAsString(),
                                re.get("date").getAsString()
                        );
                        mSignList.add(ss[i]);
//                        Log.e("sname",ss[i].getSname());
                    }
                    ActivitySignDetail.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            ActivityListResourceAdapter adapter = new ActivityListResourceAdapter(getContext(), R.layout.activity_resource_list_item, mSignList,studentsNum);
//                            setListAdapter(adapter);
                            int signNum=0;
                            for(int i=0;i<totalnum;i++){
                                lt=new LinearLayout(ActivitySignDetail.this);
                                lm=new LinearLayout(ActivitySignDetail.this);
                                t=new TextView(ActivitySignDetail.this);
                                tt=new TextView(ActivitySignDetail.this);
                                im=new ImageView(ActivitySignDetail.this);
                                iim=new View(ActivitySignDetail.this);
                                im.setImageResource(R.drawable.avatar);
                                iim.setBackgroundColor(Color.parseColor("#E3E3E3"));
//                                iim.getLayoutParams().width=10;
                                iim.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,10));
                                t.setText(ss[i].getSname());
                                t.setTextSize(30);
                                tt.setText(String.valueOf(ss[i].getSno()));
                                tt.setTextSize(20);
                                lm.setOrientation(LinearLayout.VERTICAL);
                                lm.addView(t);
                                lm.addView(tt);
                                lt.setOrientation(LinearLayout.HORIZONTAL);
                                lt.addView(im);
                                lt.addView(lm);
                                if(ss[i].getState()){
                                    signNum++;
                                    ll.addView(iim);
                                    ll.addView(lt);
//                                    ll.addView(im);
//                                    ll.addView(t);
//                                    ll.addView(tt);
                                }else{
                                    lv.addView(iim);
                                    lv.addView(lt);
//                                    lv.addView(im);
//                                    lv.addView(t);
//                                    lv.addView(tt);
                                }

                            }
                            TextView tv=(TextView)findViewById(R.id.not_sign);
                            TextView ttv=(TextView)findViewById(R.id.sign);
                            if(signNum==0){
                                ttv.setVisibility(View.GONE);
                            }
                            if(signNum==totalnum){
                                tv.setVisibility(View.GONE);
                            }

                        }});
                    Looper.prepare();
                    Toast.makeText(ActivitySignDetail.this,msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(ActivitySignDetail.this,msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }


            }
        });


    }
}
