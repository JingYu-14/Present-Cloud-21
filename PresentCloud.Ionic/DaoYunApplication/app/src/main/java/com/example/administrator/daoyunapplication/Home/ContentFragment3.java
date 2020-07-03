package com.example.administrator.daoyunapplication.Home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Activity.ActivityHome;
import com.example.administrator.daoyunapplication.Adapter.ListUserAdapter;
import com.example.administrator.daoyunapplication.LoginActivity;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
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
public class ContentFragment3 extends ListFragment//extends Fragment
{
    private View mView;
    private Bitmap bitmap;
    private User user;
    private String mParam1;
    public ContentFragment3(){}
    @SuppressLint("ValidFragment")
    public ContentFragment3(String msg){
        mParam1=msg;

    }



    @SuppressLint("ValidFragment")
    public ContentFragment3(User user){
        this.user = user;
    }
//    @SuppressLint("ValidFragment")
//    public ContentFragment3(User user,Bitmap bitmap){
//        this.user = user;
//        this.bitmap=bitmap;
//
//    }
    List<User> mUserList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    private void initUser(User user){
        // mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
         mUserList.add(user);

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
        viewContent = inflater.inflate(R.layout.fragment_content3,container,false);

        Log.e("mType:",mType+" ,");
        mUserList = new ArrayList<>();
        if( mType==0) {
            initUser(user);

           // Toast.makeText(getContext(),mParam1,Toast.LENGTH_SHORT).show();

        }
        ListUserAdapter adapter = new ListUserAdapter(getContext(), R.layout.list_item_user, mUserList,bitmap);
        this.setListAdapter(adapter);
        return viewContent;
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();

        User c= (User)l.getItemAtPosition(position);
        Toast.makeText(getActivity(), "You have selected " +c.getName(), Toast.LENGTH_SHORT).show();
        if( mType==0) {
            mView=v;
            updateUserDialog();

        }
    }
    public void updateUserDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(R.string.updataUserTitle);
        View view=  LayoutInflater.from(getContext()).inflate(R.layout.activity_update_user,null);
        setUser(view);
        dialog.setView(view);

        //  dialog.setView(textView);
        dialog.setCancelable(false);

        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateUser();
            }
        });
        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }
    EditText update_account_input;
    EditText update_nick_input;
    EditText update_password_input;
    EditText update_name_input;
    EditText update_email_input;
    EditText update_telephone_input;
    public void setUser(View view){
//        update_account_input=(EditText) view.findViewById(R.id.update_account_input);
//        update_nick_input=(EditText)view.findViewById(R.id.update_nick_input);
        update_password_input=(EditText)view.findViewById(R.id.update_password_input);
       // update_name_input=(EditText)view.findViewById(R.id.update_name_input);
//        update_email_input=(EditText)view.findViewById(R.id.update_email_input);
//        update_telephone_input=(EditText)view.findViewById(R.id.update_telephone_input);
//        update_account_input.setText(user.getUsername());
//        update_nick_input.setText(user.getNick());//user是null
        update_password_input.setText(user.getPassword());
       // update_name_input.setText(user.getName());
//        update_email_input.setText(user.getEmail());
//        update_telephone_input.setText(user.getTelephone());
    }
    String password;
    String name;
    public void updateUser(){
//        update_account_input.getText().toString();
//        update_nick_input.getText().toString();
        password= update_password_input.getText().toString();
       // name = update_name_input .getText().toString();
        name =user.getName();
//        update_email_input.getText().toString();
//        update_telephone_input.getText().toString();
        yesUpdataUser(name,password);


    }

    public void yesUpdataUser(final String name, final String password){
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        Map<Object,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("pwd",password);
        String str = gson.toJson(map);
        Log.e("sadfaf",str);
        MediaType JSON =MediaType.parse("application/json;charset=utf-8");
        RequestBody requestBody=RequestBody.create(JSON,str);
        Request request = new Request.Builder()
                .url("http://129.211.87.192/daoyunapi/public/index.php/users/"+user.getUserId())//请求的url
                .addHeader("content-type", "application/json;charset:utf-8")
                .header("Authorization",user.getToken())
                .put(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
                Looper.loop();// 进入loop中的循环，查看消息队列
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
                         //   JsonObject result = jsonObject.get("data").getAsJsonObject();//取出用户信息
                            user.setName(name);
                            user.setPassword(password);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                            TextView currView = (TextView) mView.findViewById(R.id.textView_user_name);
                            currView.setText(name);
                                }
                            });

                            Looper.prepare();
                            Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列

                        }else{
                            Looper.prepare();
                            Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                        }


            }
        });
    }

}
