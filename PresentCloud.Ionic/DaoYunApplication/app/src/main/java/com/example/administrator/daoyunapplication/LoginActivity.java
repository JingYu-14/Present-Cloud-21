package com.example.administrator.daoyunapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Home.HomeActivity;
import com.example.administrator.daoyunapplication.Model.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhangyin on 2020/3/10 0010.
 */
//test
public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    private Button btn_forget_pass;
    private EditText account_input;
    private EditText password_input;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_forget_pass = (Button)findViewById(R.id.btn_forget_pass);
        account_input = (EditText)findViewById(R.id.account_input);
        password_input = (EditText)findViewById(R.id.password_input);

        btn_login.setOnClickListener(new RegisterButton());
        btn_forget_pass.setOnClickListener(new RegisterButton());

    }
    public class RegisterButton implements View.OnClickListener {
        @Override
        public void onClick(View v) {
             username = account_input.getText().toString().trim();
             password = password_input.getText().toString().trim();


            switch (v.getId()) {
                //登录开始，判断登录条件
                case R.id.btn_login:
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) ) {
                        Toast.makeText(LoginActivity.this, "各项均不能为空!", Toast.LENGTH_SHORT).show();
                    } else {

                            OkHttpClient client = new OkHttpClient();

                        // 上传文件使用MultipartBody.Builder
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("username",username)
                                .addFormDataPart("password",password)
                                .build();

                            Request request = new Request.Builder()
                                    .url("http://3r1005r723.wicp.vip/daoyunapi/public/index.php/login")//请求的url
                                    .post(requestBody)
                                    .build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
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
                                            JsonObject jsonObjectMeta =jsonObject.get("meta").getAsJsonObject();
                                            int code = jsonObjectMeta.get("status").getAsInt();
                                            String msg="";
                                            msg=jsonObjectMeta.get("msg").getAsString();
                                            if (200==code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                                            {
                                                //这边要注意，获取的是用户对象getAsString();不行.getAsJsonObject();用这个
                                                JsonObject result = jsonObject.get("data").getAsJsonObject();//取出用户信息
                                                User user = new User();
                                                user.setUserId(result.get("id").getAsInt());
                                                user.setName(result.get("name").getAsString());
                                               String pathName=(result.get("pathName").getAsString());
                                                int role=1;
                                                //0:管理员1：学生2：教师
                                                if(pathName.equals("AdminHome")){
                                                    role=0;

                                                }else if(pathName.equals("TeacherHome")){
                                                    role=2;
                                                }else if(pathName.equals("StudentHome")){
                                                    role=1;
                                                }
                                                user.setRole(role);
                                                user.setAvatar(result.get("avatar").getAsString());
                                                user.setUsername(username);
                                                user.setPassword(password);


                                                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                intent.putExtra("user",user);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();


                                            }

                                        }
                                    });
                                }
                            });


                    }
                    break;


                case R.id.btn_forget_pass:

                    break;




            }
        }
    }


}
