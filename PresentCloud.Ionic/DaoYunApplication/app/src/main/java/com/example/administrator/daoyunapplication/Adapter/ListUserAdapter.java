package com.example.administrator.daoyunapplication.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/3/12 0012.
 */

public class ListUserAdapter extends ArrayAdapter<User> {
    private Bitmap bitmap;
    private int resourceId;
    private Context context;

    public ListUserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects,Bitmap bitmap) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
        this.bitmap=bitmap;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      //  Log.e("Listposition:",position +" ");

        //获取User对象
        User aUser = getItem(position);
        View view = LayoutInflater.from(context).inflate(resourceId,null);
        ImageView imageView_headpic=(ImageView)view.findViewById(R.id.imageView_headpic);
        TextView textView_user_name = (TextView)view.findViewById(R.id.textView_user_name);
        TextView textView_empirical_value = (TextView)view.findViewById(R.id.textView_empirical_value);

        if(bitmap==null) {
            imageView_headpic.setImageResource(R.drawable.ic_launcher);
        }else {
            imageView_headpic.setImageBitmap(bitmap);
        }
        textView_user_name.setText(aUser.getName());
        //字符串需要拼接
        int v=aUser.getEmpiricalValue();
        String a=getContext().getResources().getString(R.string.exp);
        textView_empirical_value.setText(a +v);
//        textView_empirical_value.setText("经验值："+aUser.getEmpiricalValue());
        return view;
    }

    public void seachImage(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://129.211.87.192/daoyunapi/public/static/img/avatar.jpg")//请求的url
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), "网络连接失败！", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // final String info = response.body().string();//获取服务器返回的json格式数据
                InputStream inputStream = response.body().byteStream();//得到图片的流
                bitmap = BitmapFactory.decodeStream(inputStream);

            }
        });
    }
}

