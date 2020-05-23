package com.example.administrator.daoyunapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    TextView t;
    TextView tt;
    private LinearLayout ll;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_detail);
//        LinearLayout have=(LinearLayout)findViewById(R.id.have_sign);
        Intent intent =getIntent();
        mSignList = new ArrayList<>();
        lv= (LinearLayout) findViewById(R.id.lv);
        ll=(LinearLayout)findViewById(R.id.have_sign);
//        t=new TextView(this);
//        tt=new TextView(this);
        String searchtime=intent.getStringExtra("searchtime");
        int classid=intent.getIntExtra("classid",0);
        int studentsNum=intent.getIntExtra("studentsnum",0);
        Sign ss[]=new Sign[studentsNum];
        getSignDetail(searchtime,ss,classid);
//        ss= Arrays.copyOfRange(ss,0,totalnum);
//        Log.e("totalnum", String.valueOf(totalnum));
//        Log.e("searchtime",searchtime);
//        Log.e("ss",ss[0].getSname());
//        int num= (int) intent.getIntExtra("number",0);
//        List<Sign> sign = (List<Sign>) getIntent().getSerializableExtra("signs");
//        Sign ss[]=new Sign[num];
//        ss=(Sign[])intent.getSerializableExtra("signs");
//        Log.e("num", String.valueOf(num));
//        Toast.makeText(ActivitySignDetail.this, String.valueOf(num), Toast.LENGTH_SHORT).show();
//        if(totalnum!=0){
//            for(int i=0;i<totalnum;i++){
//                LinearLayout ll=new LinearLayout(this);
//                ll.setOrientation(LinearLayout.VERTICAL);
//                TextView t1 =new TextView(this);
//                TextView t2=new TextView(this);
//                if(ss[i].getState()){
//                    //签到了
//                    t1.setText(ss[i].getSname());
//                    t2.setText(ss[i].getSno());
//                    ll.addView(t1);
//                    ll.addView(t2);
//                    have.addView(ll);
//                }
//            }
//        }else{
//            TextView t = new TextView(this);
//            t.setText("试试");
//            have.addView(t);
//        }


//        Sign u[] = (Sign) intent.getSerializableExtra("user");
//        lv.addView(t);
    }


    private void getSignDetail(String searchTime, final Sign ss[],int classid){//教师获取签到记录
        final OkHttpClient client = new OkHttpClient();
        String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";
        path = path + "teacherSigns";
        int pagenum=1;
        int pagesize=100;
        String format = String.format(path+"?id="+classid+"&pagenum="+pagenum+"&pagesize="+pagesize+"&searchtime="+searchTime);
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
        final Request build1 = new Request.Builder().url(format).build();

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
                            for(int i=0;i<totalnum;i++){
                                t=new TextView(ActivitySignDetail.this);
                                tt=new TextView(ActivitySignDetail.this);
                                t.setText(ss[i].getSname());
                                tt.setText(String.valueOf(ss[i].getSno()));
                                if(ss[i].getState()){
                                    ll.addView(t);
                                    ll.addView(tt);
                                }else{
                                    lv.addView(t);
                                    lv.addView(tt);
                                }

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
