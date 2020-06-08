package com.example.administrator.daoyunapplication.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.Courses;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.example.administrator.daoyunapplication.zxing.activity.CaptureActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.widget.ArrayAdapter;
/**
 * Created by Administrator on 2020/4/23 0023.
 */

public class createClassAdapter  extends ArrayAdapter<Classes> {
    private int resourceId;
    private Context context;
    private User user;
    static List<Courses>  mCoursesList;
    private static List<String> data_list;

    public createClassAdapter(@NonNull Context context, int resource, @NonNull List<Classes> objects,User user) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
        this.user=user;
        getCourses();
    }
    //初始化获取课程名
    public void getCourses(){
        final OkHttpClient client = new OkHttpClient();
        String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";
        String format = String.format(path+"courses");
        Log.e("path:",format);
        //类似  KeyPath.Path.head + KeyPath.Path.smsalarm, username, userPass, type, lat, lon, finalOptions, text10            KeyPath.Path.head + KeyPath.Path.smsalarm是封装好的ip地址    后面是参数
        final Request build1 = new Request.Builder().url(format).header("Authorization",user.getToken()).build();
        client.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
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

                mCoursesList = new ArrayList<>();
                data_list = new ArrayList<String>();
                if (200==code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                {
                    //这边要注意，获取的是用户对象getAsString();不行.getAsJsonObject();用这个
                    JsonArray result = jsonObject.get("data").getAsJsonArray();//取出用户信息
//                            Log.e("ada",result.toString());
                    for(int i=0;i<result.size();i++){
                        JsonObject re=result.get(i).getAsJsonObject();
                        Courses c = new Courses(
                                re.get("id").getAsInt(),
                                re.get("cname").isJsonNull()?"":re.get("cname").getAsString()
                        );
                        data_list.add(re.get("cname").isJsonNull()?"":re.get("cname").getAsString());
                        mCoursesList.add(c);
                    }

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
    public void setResourceId(int resource){
        this.resourceId = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e("Listposition:",position +" ");
        int itemViewType=getItemViewType(position);
        View view = null;
        if(itemViewType==0) {
            setResourceId(R.layout.creater_class_button);
            view = LayoutInflater.from(context).inflate(resourceId, null);
            //手动的创建班级和加入班级
            Button bt_create_class = (Button) view.findViewById(R.id.bt_create_class);
            bt_create_class.setOnClickListener(new CreateClassButton(position));
            Button bt_add_class = (Button) view.findViewById(R.id.bt_add_class);
            bt_add_class.setOnClickListener(new CreateClassButton(position));
            //二维码的创建班级和加入班级
            Button bt_QR_code_create_class = (Button) view.findViewById(R.id.bt_QR_code_create_class);
            bt_QR_code_create_class.setOnClickListener(new CreateClassButton(position));
            Button bt_QR_code_add_class = (Button) view.findViewById(R.id.bt_QR_code_add_class);
            bt_QR_code_add_class.setOnClickListener(new CreateClassButton(position));


        }
        return view;
        //获取对应的Class班级对象
//        Classes aClass = getItem(position);
//        View view = LayoutInflater.from(context).inflate(resourceId,null);
//        ImageView imageView_headpic=(ImageView)view.findViewById(R.id.imageView_headpic);
//        TextView textView_course_name = (TextView)view.findViewById(R.id.textView_course_name);
//        TextView textView_teacher_name = (TextView)view.findViewById(R.id.textView_teacher_name);
//        TextView textView_class_name = (TextView)view.findViewById(R.id.textView_class_name);
//        imageView_headpic.setImageResource(R.drawable.ic_launcher);
//        textView_course_name.setText(aClass.getNewsCourseName());
//        textView_teacher_name.setText(aClass.getNewsTeacherName());
//        textView_class_name.setText(aClass.getNewsClassName());


    }

    //////二维码:初始化数据信息////////
    private ImageView im1;  //imageview图片
    private int w,h;        //图片宽度w,高度h
    EditText QR_code_class_name;
    String classCode="";
    private Spinner spinner;
    private ArrayAdapter<String> arr_adapter;
    public void createQRcodeClass(View view,String url){
        //课程名下来列表初始化
        spinner = (Spinner) view.findViewById(R.id.spinner);
        //适配器
        arr_adapter= new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_item,data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        /////课程名下来列表初始化结束//////
        im1=(ImageView)view.findViewById(R.id.QR_code_imageView);
        w=300;
        h=300;
       // spinner.getSelectedItem().toString();
//        QR_code_class_name=(EditText)view.findViewById(R.id.QR_code_class_name);
        try
        {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1)
            {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++)
            {
                for (int x = 0; x < w; x++)
                {
                    if (bitMatrix.get(x, y))
                    {
                        pixels[y * w + x] = 0xff000000;
                    }
                    else
                    {
                        pixels[y * w + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            //显示到我们的ImageView上面
            im1.setImageBitmap(bitmap);
        }
        catch (WriterException e)
        {
            e.printStackTrace();
        }


    }
    //////手动创建班级///////
//    EditText uid;
    EditText code;
    EditText class_name;
    public void createClass(View view,String classCode){
        //课程名下来列表初始化
        spinner = (Spinner) view.findViewById(R.id.spinner_class_name);
        //适配器
        arr_adapter= new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_spinner_item,data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        /////课程名下来列表初始化结束//////
//        uid=(EditText) view.findViewById(R.id.uid);
        code=(EditText)view.findViewById(R.id.code);
        code.setText(classCode);
       // class_name=(EditText)view.findViewById(R.id.class_name);
    }
    //手动加入班级
    public void addClass(View view){
        code=(EditText)view.findViewById(R.id.code);

    }
    public void yesaddClass(){
        final OkHttpClient client = new OkHttpClient();
        String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";
        path = path + "studentClasses";
        int id=user.getUserId();//用户id
        Log.e("uid",id+"");
        Gson gson = new Gson();
        Map<Object,Object> map = new HashMap<>();
        map.put("uid",id);
        map.put("code", code.getText().toString());
        String params = gson.toJson(map);
        MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,params);
        ///////////////////////////
        Request request = new Request.Builder()
                .url(path)//请求的url
                .header("Authorization",user.getToken())
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
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
    ///////////////////////////
    /*二维码形式加入班级,即扫一扫*/
    public void addQRcordClassDialog(){
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        intent.putExtra("userId",user.getUserId());
        intent.putExtra("user",user);
        getContext().startActivity(intent);
    }
    /*获取一条随机字符串*/
    public static String getRandomString(int length) { //length表示生成字符串的长度  
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    //二维码创建班级
    public void createQRcordClassDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("创建班级:(请截图保存二维码)");
        View view=  LayoutInflater.from(getContext()).inflate(R.layout.create_qr_code_class,null);
        classCode=getRandomString(6);
        createQRcodeClass(view,classCode);
        dialog.setView(view);

        //  dialog.setView(textView);
        dialog.setCancelable(false);

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yescreateClass(true);
            }
        });
        dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }

//////////////////////////////

    public void createClassDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("创建班级:");
        View view=  LayoutInflater.from(getContext()).inflate(R.layout.create_class,null);
        classCode=getRandomString(6);
        createClass(view,classCode);
        dialog.setView(view);

        //  dialog.setView(textView);
        dialog.setCancelable(false);

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yescreateClass(false);
            }
        });
        dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }
    public void yescreateClass(boolean isQRcode){
        final OkHttpClient client = new OkHttpClient();
        String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";
        path = path + "teacherClasses";
        int id=user.getUserId();//用户id
        Log.e("uid",id+"");
        Gson gson = new Gson();
        Map<Object,Object> map = new HashMap<>();
        map.put("uid",id);
        if(isQRcode){//二维码创建班级
            map.put("code", classCode);
            map.put("name",spinner.getSelectedItem().toString());
//            map.put("name",QR_code_class_name.getText().toString());
        }else{//手动创建班级
            map.put("code", code.getText().toString());
            map.put("name",spinner.getSelectedItem().toString());
            //map.put("name",class_name.getText().toString());
        }


        String params = gson.toJson(map);
        MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,params);
        ///////////////////////////
        Request request = new Request.Builder()
                .url(path)//请求的url
                .header("Authorization",user.getToken())
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        });



        ////////////////

    }
    public void addClassDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("加入班级:");
        View view=  LayoutInflater.from(getContext()).inflate(R.layout.add_class,null);
        addClass(view);
        dialog.setView(view);

        //  dialog.setView(textView);
        dialog.setCancelable(false);

        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                yesaddClass();
            }
        });
        dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }
    public class CreateClassButton implements View.OnClickListener {
        int mPosition;
        public CreateClassButton(int inPosition){
            mPosition= inPosition;
        }
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                //签到按钮
                case R.id.bt_create_class:
//                    Context currentActivity=v.getContext();
//                    //跳到签到页面
//                    Intent intent = new Intent(currentActivity, SignActivity.class);
//                    intent.putExtra("user",user);
//                    intent.putExtra("classes",c);
//                    currentActivity.startActivity(intent);
                    createClassDialog();//手动创建班级
                    break;
                case R.id.bt_add_class:
//                    Toast.makeText(v.getContext(), mPosition+"dfd", Toast.LENGTH_SHORT).show();
                    addClassDialog();//学生加入班级
                    break;
                case R.id.bt_QR_code_create_class:
//                    Toast.makeText(v.getContext(), mPosition+"dfd", Toast.LENGTH_SHORT).show();
                    createQRcordClassDialog();//老师二维码生成班级
                    break;
                case R.id.bt_QR_code_add_class:
//                    Toast.makeText(v.getContext(), mPosition+"dfd", Toast.LENGTH_SHORT).show();
                    addQRcordClassDialog();//二维码加入班级
                    break;

            }
        }

    }
}
