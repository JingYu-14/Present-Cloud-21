package com.example.administrator.daoyunapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/3/10 0010.
 */

public class ReignActivity extends AppCompatActivity {
    private Button btn_enroll;
    private EditText account_input;//账号
    private EditText password_input;
    private EditText name_input;
    private EditText nick_input;
    private EditText email_input;
    private EditText telephone_input;
    private RadioGroup radioGroup_role;
    private RadioButton student;
    private RadioButton teacher;
    private int role =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reign);
        btn_enroll = (Button)findViewById(R.id.btn_enroll);

        account_input = (EditText)findViewById(R.id.account_input);
        password_input = (EditText)findViewById(R.id.password_input);
        name_input = (EditText)findViewById(R.id.name_input);
//        nick_input = (EditText)findViewById(R.id.nick_input);
        email_input = (EditText)findViewById(R.id.email_input);
//        telephone_input = (EditText)findViewById(R.id.telephone_input);

        radioGroup_role = (RadioGroup) this.findViewById(R.id.radioButton_role);
        student=(RadioButton)findViewById(R.id.student);//1
        teacher=(RadioButton)findViewById(R.id.teacher);//2
        btn_enroll.setOnClickListener(new RegisterButton());
        student.setOnClickListener(new RegisterButton());
        teacher.setOnClickListener(new RegisterButton());
    }


    public class RegisterButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String username = account_input.getText().toString().trim();
            String password = password_input.getText().toString().trim();
            String name = name_input.getText().toString().trim();
//            String nick = nick_input.getText().toString().trim();
            String email = email_input.getText().toString().trim();
//            String telephone = telephone_input.getText().toString().trim();

            switch (v.getId()) {
                //注册开始，判断注册条件
                case R.id.btn_enroll:
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email) || TextUtils.isEmpty(name)) {
                        Toast.makeText(ReignActivity.this, "各项均不能为空", Toast.LENGTH_SHORT).show();
                    } else {
//                        if(isMobileNO(telephone)){
                            //手机号验证成功
                        //    if (TextUtils.equals(password, password2)) {

                                //执行注册操作
                                    OkHttpClient client = new OkHttpClient();
                        Gson gson = new Gson();
                        Map<Object,Object> map = new HashMap<>();
                        map.put("account",username);
                        map.put("pwd",password);
                        map.put("email",email);
                        map.put("name",name);
                        map.put("type",role);
                        String params = gson.toJson(map);
                        MediaType JSON= MediaType.parse("application/json; charset=utf-8");
                        RequestBody requestBody = RequestBody.create(JSON,params);
//                                    User user=new User();
//                                    user.setUsername(username);
//                                    user.setPassword(password);
////                                    user.setNick(nick);
//                                    user.setName(name);
//                                    user.setEmail(email);
////                                    user.setTelephone(telephone);
//                                    user.setRole(role);
//                                    //使用Gson 添加 依赖 compile 'com.google.code.gson:gson:2.8.1'
//                                    Gson gson = new Gson();
//                                    //使用Gson将对象转换为json字符串
//                                    String json = gson.toJson(user);
//                            //MediaType  设置Content-Type 标头中包含的媒体类型值
//                            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
//                                    , json);

                            Request request = new Request.Builder()
                                    .url("http://129.211.87.192/daoyunapi/public/index.php/users")//请求的url
                                    .post(requestBody)
                                    .build();
                            client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(ReignActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }

                                        @Override
                                        public void onResponse(Call call, final Response response) throws IOException {
                                            final String info = response.body().string();//获取服务器返回的json格式数据
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Log.e("TAG", "onResponse: "+info );
                                                    JsonObject jsonObject = new JsonParser().parse(info).getAsJsonObject();
                                                //    JSONObject jsonObject = JSON.parseObject(info);//获得一个JsonObject的对象
                                                   // int code = jsonObject.getObject("code", Integer.class);
                                                   // int code = jsonObject.get("code").getAsInt();
                                                    JsonObject jsonObjectMeta =jsonObject.get("meta").getAsJsonObject();
                                                    int code = jsonObjectMeta.get("status").getAsInt();
                                                    String msg="";
                                                    if (201==code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                                                    {
                                                      //  String result = jsonObject.get("data").getAsString();//取出用户信息
                                                        msg=jsonObjectMeta.get("msg").getAsString();

                                                    }
                                                    Toast.makeText(ReignActivity.this,msg,Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ReignActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    });
//


//                            } else {
//                                Toast.makeText(RegisterActivity.this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
//                            }
//                        }else{
//                            Toast.makeText(ReignActivity.this, "手机号码错误", Toast.LENGTH_SHORT).show();
//                        }

                    }
                    break;


                case R.id.student:
                    role = 1;
                    break;
                case R.id.teacher:
                    role = 2;
                    break;



            }
        }
    }

    public static boolean isMobileNO(String mobiles) {

        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)){
            return false;
        }
        else return mobiles.matches(telRegex);
    }
}
