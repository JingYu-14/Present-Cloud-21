package com.example.administrator.daoyunapplication.Home;

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

import com.example.administrator.daoyunapplication.Activity.ActivityHome;
import com.example.administrator.daoyunapplication.Adapter.ListClassAdapter;
import com.example.administrator.daoyunapplication.Adapter.createClassAdapter;
import com.example.administrator.daoyunapplication.LoginActivity;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//底部选项卡对应的Fragment
public class ContentFragment extends ListFragment//extends Fragment
{   private  User user;
     List<Classes>  mClassList;
    public ContentFragment(){}
    @SuppressLint("ValidFragment")
    public ContentFragment(User user){
        this.user = user;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void initClass(){
//        mClassList.add(new Classes(1,"apple","工程实践","池老标1","2019级专硕"));
//        mClassList.add(new Classes(2,"banana","工程实践","池老标2","2019级专硕"));
//        mClassList.add(new Classes(3,"hot","工程实践","池老标3","2019级专硕"));
        getClassesData();
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
        viewContent = inflater.inflate(R.layout.fragment_content,container,false);

//        TextView textView = (TextView) viewContent.findViewById(R.id.tv_content);
//        textView.setText(this.mTitle);

        Log.e("mType:",mType+" ,");
//        mClassList = new ArrayList<>();


        if (mType == 0) {
            if(user.getRole()==2) {//老师就是有我创建的，学生没有
                initClass();
            }
        } else if (mType == 1) {
           // mClassList.add(new Classes(5,"2222","工程实践","池老标4","2019级专硕"));

                initClass();

//            ListClassAdapter adapter = new ListClassAdapter(getContext(), R.layout.list_item, mClassList);
//            setListAdapter(adapter);
        } else if (mType == 2) {
            mClassList = new ArrayList<>();
            mClassList.add(new Classes(6,"333333","工程实践",1,"池老标5","2019级专硕","hv"));
            createClassAdapter adapter = new createClassAdapter(getContext(), R.layout.creater_class_button, mClassList,user);
            this.setListAdapter(adapter);
//            ListClassAdapter adapter = new ListClassAdapter(getContext(), R.layout.list_item, mClassList);
//            this.setListAdapter(adapter);

        }
      //  Log.e("ada",mClassList.toString());
//        ListClassAdapter adapter = new ListClassAdapter(getContext(), R.layout.list_item, mClassList);
//        this.setListAdapter(adapter);



        return viewContent;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();

        Classes c= (Classes)l.getItemAtPosition(position);
        Toast.makeText(getActivity(), "You have selected " +c.getNewsTeacherName(), Toast.LENGTH_SHORT).show();
        if( mType==0) {
            Intent intent = new Intent(getActivity(), ActivityHome.class);
            intent.putExtra("classes", c);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        else if( mType==1) {
            Intent intent = new Intent(getActivity(), ActivityHome.class);
            intent.putExtra("classes", c);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }

    private void getClassesData(){
        final OkHttpClient client = new OkHttpClient();
        String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";
        int role= user.getRole();
        if(role==1){
            path=path+"studentClasses";
        }
        else if(role==2){
            path=path+"teacherClasses";
        }
        String format = String.format(path+"?uid="+user.getUserId());
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
        final Request build1 = new Request.Builder().url(format).build();

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
                        mClassList = new ArrayList<>();
                        if (200==code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                        {
                            //这边要注意，获取的是用户对象getAsString();不行.getAsJsonObject();用这个
                            JsonArray result = jsonObject.get("data").getAsJsonArray();//取出用户信息
//                            Log.e("ada",result.toString());
                            for(int i=0;i<result.size();i++){
                                JsonObject re=result.get(i).getAsJsonObject();
                                Classes c = new Classes(
                                re.get("id").getAsInt(),
                                re.get("name").getAsString(),
                                re.get("class_name").getAsString(),
                                re.get("tno").getAsInt(),
                                re.get("invitation_code").getAsString(),
                                re.get("task_id").isJsonNull()?"":re.get("task_id").getAsString(),
                                re.get("task").getAsString());
                                mClassList.add(c);
                            }
//                            Log.e("ada",mClassList.toString());
                            //ui更新必须用这个
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ListClassAdapter adapter = new ListClassAdapter(getContext(), R.layout.list_item, mClassList);
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
