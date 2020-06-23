package com.example.administrator.daoyunapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ActivityCreateTask extends AppCompatActivity {
    private int id;
    private Classes c;
    private User u;
    private String ti;
    private String con;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        final LinearLayout deadLine = (LinearLayout)findViewById(R.id.LL_deadline);
        final EditText title=(EditText) findViewById(R.id.edit_title);
        final EditText detail=(EditText)findViewById(R.id.edit_detail);
        c=(Classes)getIntent().getSerializableExtra("classes");
        u=(User)getIntent().getSerializableExtra("user");
        ti=getIntent().getStringExtra("title");
        con=getIntent().getStringExtra("content");
        title.setText(ti);
        detail.setText(con);
        id =c.getNewsClassId();
        deadLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityCreateTask.this, SetDeadlineActivity.class);
//                startActivityForResult(intent);
                Bundle bundle = new Bundle();
                bundle.putSerializable("classes", c);
                bundle.putSerializable("user",u);
                bundle.putString("title",title.getText().toString());
                bundle.putString("content",detail.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
//                startActivityForResult(intent,1);
            }
        });
        TextView textView =(TextView)findViewById(R.id.create_confirm);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_name=title.getText().toString();
                String detail_content=detail.getText().toString();
                String deadline=getIntent().getStringExtra("time");
                if(title_name==null||detail_content==null||deadLine==null){
                    Toast.makeText(ActivityCreateTask.this,"任务标题、详情以及截止日期均不能为空", Toast.LENGTH_SHORT).show();
                }else{

                    addTask(title_name,detail_content,deadline,id);
                    Intent intent=new Intent(ActivityCreateTask.this,ActivityHome.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("classes", c);
                    bundle.putSerializable("user",u);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    public void addTask(String title,String content,String deadline,int id){
        Log.e("deadline2:",deadline+" ,");
//        String test=stampToDate(deadline);
//        Log.e("time1",test);
        final OkHttpClient client = new OkHttpClient();
        String path="http://129.211.87.192/daoyunapi/public/index.php/";
        path = path + "teacherTasks";
        Gson gson = new Gson();
        Map<Object,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",title);
        map.put("detail",content);
        map.put("deadline",deadline);
        String params = gson.toJson(map);
        MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,params);
        ///////////////////////////
        Request request = new Request.Builder()
                .url(path)//请求的url
                .header("Authorization",u.getToken())
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(ActivityCreateTask.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
                Looper.loop();
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
                if (201==code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                {
                    //这边要注意，获取的是用户对象getAsString();不行.getAsJsonObject();用这个
                    // JsonObject result = jsonObject.get("data").getAsJsonObject();//取出用户信息
                    Looper.prepare();
                    Toast.makeText(ActivityCreateTask.this,msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(ActivityCreateTask.this,msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        });



        ////////////////

    }
}
