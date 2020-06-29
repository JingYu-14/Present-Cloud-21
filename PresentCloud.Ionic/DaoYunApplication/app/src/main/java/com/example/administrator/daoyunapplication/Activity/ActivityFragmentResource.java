package com.example.administrator.daoyunapplication.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListResourceAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListSignAdapter;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.Sign;
import com.example.administrator.daoyunapplication.Model.SignResult;
import com.example.administrator.daoyunapplication.Model.StudentSign;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yinzhang on 2020/3/10.
 */

public class ActivityFragmentResource extends ListFragment{
    List<SignResult> mSignList;
    List<StudentSign> studentList;
    private static Classes c;
    private static User user;
    private static int studentsNum=0;
    private static int signNum;
    public ActivityFragmentResource(){}
    @SuppressLint("ValidFragment")
    public ActivityFragmentResource(Classes classs, User u){
        c=classs;
        user=u;
    }
    @Nullable
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //这边做数据的初始化，从服务器获取数据
    private void initClass(){
        //注意，第一个数据不显示，后面自己补一个初始数据，后台获取的接到初始数据中
//        mSignList.add(new SignResult());
        if(user.getRole()==2){//如果是老师
            getSign();
        }else if (user.getRole()==1){//如果是学生
            getStudentSign();
        }

    }

    private View viewContent;
    private int mType = 0;//上选项卡的名称 编号
    private String mTitle;//上选项卡的名称


    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    //    private View viewContent;
//    private ViewPager vp_user;//用户内容
//    private ViewPager vp_list;//列表内容
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (viewContent == null) {
            if(user.getRole()==2){//老师
                viewContent=inflater.inflate(R.layout.fragment_activity_resource, null);
            }else if (user.getRole()==1){//学生
                viewContent=inflater.inflate(R.layout.fragment_activity_sign, null);
                viewContent.setEnabled(false);
            }


        }
        mSignList = new ArrayList<>();
        studentList=new ArrayList<>();
        getStudentesNum();
        initClass();
        Log.e("dafasf",c.toString());
//        ActivityListMemberAdapter adapter = new ActivityListMemberAdapter(getContext(), R.layout.activity_member_list_item, mUserList);
//        this.setListAdapter(adapter);

        return viewContent;

    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        SignResult cc= (SignResult) l.getItemAtPosition(position);
//        Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        //这边写跳转到任务的详细页面，提交任务页面
        if(user.getRole()==2){
            List<Sign> list = new ArrayList<>();
            String searchTime=cc.getDate()+" "+cc.getTime().replaceAll("-",":");
            Intent intent = new Intent(getActivity(), ActivitySignDetail.class);
            intent.putExtra("searchtime",searchTime);
            intent.putExtra("classid",c.getNewsClassId());
            intent.putExtra("studentsnum",studentsNum);
            intent.putExtra("user",user);
            startActivity(intent);
        }

    }

    private void getStudentesNum(){
        final OkHttpClient client = new OkHttpClient();
        String path="http://129.211.87.192/daoyunapi/public/index.php/";

        path = path + "students";


        int pagenum=1;
        int pagesize=100;
        String format = String.format(path+"?id="+c.getNewsClassId()+"&pagenum="+pagenum+"&pagesize="+pagesize);
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
        final Request build1 = new Request.Builder().url(format).header("Authorization",user.getToken()).build();

        client.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
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
                    studentsNum = result.get("total").getAsInt();//取出用户信息
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });


    }
    private void getSign(){//教师获取签到记录
        final OkHttpClient client = new OkHttpClient();
        String path="http://129.211.87.192/daoyunapi/public/index.php/";

        path = path + "signs";


        int pagenum=1;
        int pagesize=100;
        String format = String.format(path+"?id="+c.getNewsClassId()+"&pagenum="+pagenum+"&pagesize="+pagesize);
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
        final Request build1 = new Request.Builder().url(format).header("Authorization",user.getToken()).build();

        client.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
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
                    Sign s[]=new Sign[signsArray.size()];
                    int k=0;
                    for(int i=0;i<signsArray.size();i++){
                        JsonObject re=signsArray.get(i).getAsJsonObject();
                        if(re.get("class_id").getAsInt()==c.getNewsClassId()){//是当前班级的信息
                            s[i]=new Sign(
                                    re.get("id").getAsInt(),
                                    re.get("sname").getAsString(),
                                    re.get("class_id").getAsInt(),
                                    re.get("time").getAsString(),
                                    re.get("sno").getAsInt(),
                                    re.get("state").getAsBoolean(),
                                    re.get("account").getAsString(),
                                    re.get("date").getAsString()
                            );
                            k++;
//                            Log.e("sign",String.valueOf(s[i].getClassId()));
                        }
                    }
                    Log.e("k",String.valueOf(k));
//                    studentsNum = result.get("total").getAsInt();//取出用户信息
                    if(k!=0){
                        SignResult sr[]=new SignResult[k];
                       int num = s[0].caculate(s,k,sr);
                        Log.e("num",String.valueOf(num));
                        for(int j=0;j<num;j++){
                            mSignList.add(sr[j]);
                        }
                    }

//                            Log.e("ada",mClassList.toString());
                    //ui更新必须用这个
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityListResourceAdapter adapter = new ActivityListResourceAdapter(getContext(), R.layout.activity_resource_list_item, mSignList,studentsNum);
                            setListAdapter(adapter);
                        }});
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }


            }
        });


    }


    private void getStudentSign(){//教师获取签到记录
        final OkHttpClient client = new OkHttpClient();
        String path="http://129.211.87.192/daoyunapi/public/index.php/";
        path = path + "signs";
        int pagenum=1;
        int pagesize=100;
        String format = String.format(path+"?id="+c.getNewsClassId()+"&uid="+user.getUserId()+"&pagenum="+pagenum+"&pagesize="+pagesize);
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
        final Request build1 = new Request.Builder().url(format).header("Authorization",user.getToken()).build();

        client.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
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
                    for(int i=0;i<signsArray.size();i++){
                        JsonObject re=signsArray.get(i).getAsJsonObject();
                        if(re.get("class_id").getAsInt()==c.getNewsClassId()){//是当前班级的信息
                            StudentSign s=new StudentSign(
                                    re.get("id").getAsInt(),
                                    re.get("code").getAsString(),
                                    re.get("sname").getAsString(),
                                    re.get("class_id").getAsInt(),
                                    re.get("time").getAsString(),
                                    re.get("sno").getAsInt(),
                                    re.get("state").getAsBoolean(),
                                    re.get("account").getAsString(),
                                    re.get("date").getAsString()
                            );
                            studentList.add(s);
//                            Log.e("sign",String.valueOf(s[i].getClassId()));
                        }
                    }

//                            Log.e("ada",mClassList.toString());
                    //ui更新必须用这个
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityListSignAdapter adapter = new ActivityListSignAdapter(getContext(), R.layout.activity_resource_list_item, studentList);
                            setListAdapter(adapter);
                        }});
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }


            }
        });


    }

}
