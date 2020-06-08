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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityListActivityAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.Adapter.ListClassAdapter;
import com.example.administrator.daoyunapplication.Home.HomeActivity;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.Disscuss;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//底部选项卡对应的Fragment
@SuppressLint("ValidFragment")
public class ActivityContentFragment extends ListFragment//extends Fragment
{
    private static Classes c;
    private static User u;
    List<Disscuss> mDisscussList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public ActivityContentFragment(){}
    @SuppressLint("ValidFragment")
    public ActivityContentFragment(Classes c,User u){
        this.c=c;
        this.u=u;
    }
    //这边做数据的初始化，从服务器获取数据
    private void initClass(int mt){
//        mDisscussList.add(new Disscuss(1,"第六周任务","需求文档","2020-02-01","2020-02-02"));
//        mDisscussList.add(new Disscuss(1,1,1,"第六周任务","需求文档","2020-02-01","2020-02-02"));
//        mDisscussList.add(new Disscuss(1,1,1,"第六周任务","需求文档","2020-02-01","2020-02-02"));
        getTaskData(mt);
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //布局文件中只有一个居中的ListView
        viewContent = inflater.inflate(R.layout.fragment_task,container,false);
        if(u.getRole()==2){
            //老师，可以创建班课
            Button buttonTask = (Button)viewContent.findViewById(R.id.create_task);
            buttonTask.setText("创建任务");
            buttonTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ActivityCreateTask.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("classes", c);
                    bundle.putSerializable("user",u);
                    bundle.putString("title","");
                    bundle.putString("content","");
                    intent.putExtras(bundle);
                    startActivity(intent);
//                    startActivityForResult(intent,2);
                }
            });
        }else if(u.getRole()==1){
            //学生，隐藏创建任务按钮
            Button buttonTask = (Button)viewContent.findViewById(R.id.create_task);
            buttonTask.setVisibility(buttonTask.INVISIBLE);
        }
//        TextView textView = (TextView) viewContent.findViewById(R.id.tv_content);
//        textView.setText(this.mTitle);

        Log.e("mType:",mType+" ,");
        mDisscussList = new ArrayList<>();
        if( mType==0) {
            initClass(mType);
        } else if(mType==1){
            initClass(mType);
//            mDisscussList.add(new Disscuss(1,"第六周任务","需求文档","2020-02-01","2020-02-02"));
        }else if(mType==2){
            initClass(mType);
//            mDisscussList.add(new Disscuss(1,"第六周任务","需求文档","2020-02-01","2020-02-02"));
        }

        ActivityListActivityAdapter adapter = new ActivityListActivityAdapter(getContext(), R.layout.activity_activity_list_item, mDisscussList);
        this.setListAdapter(adapter);
        return viewContent;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        Disscuss c= (Disscuss)l.getItemAtPosition(position);
        Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        //这边写跳转到任务的详细页面，提交任务页面
    }

    private void getTaskData(final int mt){
        final OkHttpClient client = new OkHttpClient();
        String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";

        path=path+"tasks";
        String format = String.format(path+"?id="+c.getNewsClassId());
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
        final Request build1 = new Request.Builder().url(format).header("Authorization",u.getToken()).build();

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
                    JsonArray result = jsonObject.get("data").getAsJsonArray();
                    for(int i=0;i<result.size();i++){
                        JsonObject re=result.get(i).getAsJsonObject();
                        Disscuss d = new Disscuss(
                                re.get("id").getAsInt(),
                                re.get("name").getAsString(),
                                re.get("detail").getAsString(),
                                re.get("start_time").getAsString(),
                                re.get("state").getAsString(),
                                re.get("end_time").getAsString());
                        //mt=0，显示全部，mt=1，正在进行中，显示截至日期还未过的
                        //mt=2显示已经结束的
                        if(mt==0){
                            mDisscussList.add(d);
                        }else if(mt==1){
                            if(d.getState().equals("进行中")){
                                mDisscussList.add(d);
                            }
                        }else if(mt==2){
                            if(d.getState().equals("过期")){
                                mDisscussList.add(d);
                            }
                        }

                    }
//                            Log.e("ada",mClassList.toString());
                    //ui更新必须用这个
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityListActivityAdapter adapter = new ActivityListActivityAdapter(getContext(), R.layout.activity_activity_list_item, mDisscussList);
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
