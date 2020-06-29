package com.example.administrator.daoyunapplication.Activity;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityListActivityAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.LoginActivity;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.Disscuss;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.example.administrator.daoyunapplication.ReignActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploadTaskActivity extends AppCompatActivity {
    private String disscussDetail;
    private Disscuss d;
    private Classes c;
    private User u;
    private int id;
    private int state;
    private File file;
    private String path,uploadfile;
    private String presentation;
    private Uri ContentUriUtil;
    private static final String TAG = "ChooseFile";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_task_activity);
        Intent intent =getIntent();
        u = (User) intent.getSerializableExtra("user");
        c= (Classes) intent.getSerializableExtra("classes");
        d = (Disscuss)intent.getSerializableExtra("disscuss");
        getTaskDetail();
        TextView textDetail = (TextView)findViewById(R.id.task_detail);
        disscussDetail = d.getDisscussName()+"\n"+d.getQuestionName();
        textDetail.setText(disscussDetail);
//        Button download = (Button)findViewById(R.id.download_file);
//        if(state!=1){//尚未提交，隐藏下载附件按钮
//            download.setVisibility(View.GONE);
//        }else{
//            //如果点击下载，则...
//        }
//        Button upload = (Button)findViewById(R.id.add_file);
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 打开系统的文件选择器
//                showFileChooser();
////                   Log.e("path",path);
//            }
//        });
//        Button button = (Button)findViewById(R.id.upload);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadFile();
//            }
//        });

    }
    private void getTaskDetail(){
        final OkHttpClient client = new OkHttpClient();
        String pathh="http://129.211.87.192/daoyunapi/public/index.php/";

        pathh=pathh+"taskDetails";
        int pagenum=1;
        int pagesize=100;
        String format = String.format(pathh+"?taskId="+d.getQuestionId()+"&classId="+c.getNewsClassId()+"&pagenum="+pagenum+"&pagesize="+pagesize);
//        String format = path+"?taskId="+d.getQuestionId()+"&classId="+c.getNewsClassId()+"&pagenum="+pagenum+"&pagesize="+pagesize;
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
//        final Request build1 = new Request.Builder().url(format).build();
        final Request build1 = new Request.Builder().url(format).header("Authorization",u.getToken()).build();
        client.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                UploadTaskActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
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
                    JsonArray taskArray = result.get("taskDetails").getAsJsonArray();//取出用户信息
//
                    for(int i=0;i<taskArray.size();i++){
                        JsonObject re=taskArray.get(i).getAsJsonObject();
                        if(re.get("sno").getAsInt()==u.getUserId()){
                            //找到该学生与该任务的数据
                            id=re.get("id").getAsInt();//id
                            Log.e("state",re.get("state").getAsString());
                            state=re.get("state").getAsInt();//是否上传
                            presentation=re.get("presentation").getAsString();//文件地址
                        }
                    }
//                            Log.e("ada",mClassList.toString());
                    //ui更新必须用这个
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ActivityListMemberAdapter adapter = new ActivityListMemberAdapter(getContext(), R.layout.activity_member_list_item, mUserList,user,c);
//                            setListAdapter(adapter);
//                        }});
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }


            }
        });


    }


    private static final int FILE_SELECT_CODE = 0;
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult( Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
//                    String path = null;
                    try {
                        path = getPath(this, uri);
                        file=new File(path);
                        uploadfile=file.getName();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }

    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    private void uploadFile(){
        OkHttpClient client = new OkHttpClient();
//        Map<Object,Object> map = new HashMap<>();
//        Map<Object,Object> mmap = new HashMap<>();
//        Gson gson = new Gson();
//        map.put("uid",u.getUserId());
//        mmap.put("taskId",d.getQuestionId());
//        map.put("taskId",d.getQuestionId());
//        map.put("file",file);
//        String params = gson.toJson(map);
//        String params = gson.toJson(map);
//        String pparams = gson.toJson(mmap);
//        MediaType JSON= MediaType.parse("application/json; charset=utf-8");
//        RequestBody requestUid = RequestBody.create(JSON,params);
//        RequestBody requestTaskId = RequestBody.create(JSON,pparams);
//        MediaType MutilPart_Form_Data= MediaType.parse("application/json; charset=utf-8");
//        RequestBody uid = RequestBody.create(MutilPart_Form_Data,JSON.toJSONString(Integer.toString(u.getUserId())));
//        RequestBody tid = RequestBody.create(MutilPart_Form_Data,JSON.toJSONString(Integer.toString(d.getQuestionId())));
//        RequestBody requestBody = RequestBody.create(JSON,params);
//        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
////                .addFormDataPart("data",data.toString())
//                .addPart(JSON,requestUid)
////                .addPart("",requestUid) // 提交内容字段
//                .addPart("",requestTaskId)
//                .addFormDataPart("file", uploadfile, RequestBody.create(MediaType.parse("*/*"), file));// 第一个参数传到服务器的字段名，第二个你自己的文件名，第三个MediaType.parse("*/*")和我们之前说的那个type其实是一样的
//        RequestBody requestBody = requestBodyBuilder.build();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid",Integer.toString(u.getUserId()))
                .addFormDataPart("taskId",Integer.toString(d.getQuestionId()))
                .addFormDataPart("file",uploadfile,RequestBody.create(MediaType.parse("*/*"),file))
                .build();
        Request request = new Request.Builder()
                .url("http://129.211.87.192/daoyunapi/public/index.php/upload")//请求的url
                .header("Authorization",u.getToken())
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UploadTaskActivity.this, "失败！", Toast.LENGTH_SHORT).show();
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
                        String msg="111";
                        msg=jsonObjectMeta.get("msg").getAsString();
                        if (200==code)
                        {
                            //  String result = jsonObject.get("data").getAsString();//取出用户信息
                            msg=jsonObjectMeta.get("msg").getAsString();

                        }
                        Log.e("status",Integer.toString(code));
                        Toast.makeText(UploadTaskActivity.this,msg,Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(UploadTaskActivity.this, LoginActivity.class);
//                        startActivity(intent);
                    }
                });
            }
        });
//        manager.postFile(url, requestBody,new okhttp3.Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e(TAG, "onFailure: ",e);
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String responseBody = response.body().string();
//                final JSONObject obj = JSON.parseObject(responseBody);
//                Log.e(TAG,obj.toString());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 对返回结果进行操作
//                    }
//                });
//
//            }
//        });
    }
}
