package com.example.administrator.daoyunapplication.SignActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.daoyunapplication.Home.HomeActivity;
import com.example.administrator.daoyunapplication.LoginActivity;
import com.example.administrator.daoyunapplication.MainActivity;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * Created by Administrator on 2020/4/18 0018.
 */

public class SignActivity extends AppCompatActivity {
    private Button reSet_bt;
    private Button start_bt;
    private static TextView test;
    private static Classes c;
    private static User user;
    public LocationClient mLocationClient;
    private static double latitude=0.0;//纬度
    private static double longitude=0.0;//经度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        //  首先获取到意图对象
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        c = (Classes) intent.getSerializableExtra("classes");


        latitude=user.getLatitude();
        longitude=user.getLongitude();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public User getUser() {
        return user;
    }

    public class SignButton implements View.OnClickListener {
    @Override
    public void onClick(View v) {

        switch (v.getId()) {


//                case R.id.start_bt:
//
//                    break;
        }
    }
}

    public static class SudokuViewT extends View {
    private static final int DEFALUT_CELL_WIDTH = 60; //默认的cell宽度
    private static final int DEFALUT_CELL_STROKE_WIDTH = 2;
    private static final int DEFALUT_SPACE = DEFALUT_CELL_WIDTH >> 1;

    private Cell mCells[] = new Cell[9]; // 九宫格：定义用于存放九个数组

    private int mCellWidth;
    private int mCellRadius;
    private int mCellStrokeWidth;
    private int mSpace;

    private Paint mPaintNormal;
    private Paint mPaintSelected;
    private int mWidth;
    private int mHeight;

    private float mCurrentX;
    private float mCurrentY;
    private boolean mFinish = false;

    private StringBuffer mSbSelected = new StringBuffer(20);
    //    private Button reSet_bt;
//    private Button start_bt;

    /**
     * 下边是三个构造方法：每一个构造方法中有一个初始化操作
     */
    public SudokuViewT(Context context) {
        super(context);

        init();

    }

    public SudokuViewT(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();


    }

    public SudokuViewT(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();


    }

    /**
     * 初始化操作
     */
    public void init() {
        mCellWidth = DensityUtil.dip2px(getContext(), DEFALUT_CELL_WIDTH);
        mCellRadius = DensityUtil.dip2px(getContext(), DEFALUT_CELL_WIDTH >> 1);
        mCellStrokeWidth = DensityUtil.dip2px(getContext(),
                DEFALUT_CELL_STROKE_WIDTH);
        mSpace = DensityUtil.dip2px(getContext(), DEFALUT_SPACE);

        mPaintNormal = new Paint();
        mPaintNormal.setColor(Color.BLUE);
        mPaintNormal.setStrokeWidth(mCellStrokeWidth);
        mPaintNormal.setStyle(Paint.Style.STROKE);
        mPaintNormal.setAntiAlias(true);

        mPaintSelected = new Paint();
        mPaintSelected.setColor(Color.CYAN);
        mPaintSelected.setStrokeWidth(mCellStrokeWidth);
        mPaintSelected.setStyle(Paint.Style.STROKE);
        mPaintSelected.setAntiAlias(true);

        Cell cell;
        float x;
        float y;

        for (int i = 0; i < 9; i++) {
            x = mSpace * (i % 3 + 1) + mCellRadius + mCellWidth * (i % 3);
            y = mSpace * (i / 3 + 1) + mCellRadius + mCellWidth * (i / 3);

            cell = new Cell(x, y);
            mCells[i] = cell;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCell(canvas);
        drawLine(canvas);
    }

    private void drawCell(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            canvas.drawCircle(mCells[i].getCenterX(), mCells[i].getCenterY(),
                    mCellRadius, mCells[i].isSelected() ? mPaintSelected
                            : mPaintNormal);
        }
    }

    private void drawLine(Canvas canvas) {
        if ("".equals(mSbSelected.toString())) {
            return;
        }

        String[] selectedIndexs = mSbSelected.toString().split(",");
        Cell cell = mCells[Integer.valueOf(selectedIndexs[0])];
        Cell nextCell;
        if (selectedIndexs.length > 1) {
            for (int i = 1; i < selectedIndexs.length; i++) {
                nextCell = mCells[Integer.valueOf(selectedIndexs[i])];
                canvas.drawLine(cell.getCenterX(), cell.getCenterY(),
                        nextCell.getCenterX(), nextCell.getCenterY(),
                        mPaintSelected);

                cell = nextCell;
            }
        }

        if (!mFinish) {
            canvas.drawLine(cell.getCenterX(), cell.getCenterY(), mCurrentX,
                    mCurrentY, mPaintSelected);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mWidth);
    }

    private int getRealSize(int measureSpc) {
        int result;
        int mode = MeasureSpec.getMode(measureSpc);
        int size = MeasureSpec.getSize(measureSpc);

        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            result = mCellWidth * 3 + mSpace * 4;
        } else {
            result = size;
        }

        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mFinish) {
                    for (int i = 0; i < 9; i++) {
                        mCells[i].setSelected(false);
                    }
                    mFinish = false;
                    mSbSelected.delete(0, mSbSelected.length());
                    invalidate();
                    return false;
                }
                handleDownEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                mFinish = true;
                //这边显示手势解锁的编码，mSbSelected.toString()
//                Toast.makeText(getContext(), mSbSelected.toString(), Toast.LENGTH_SHORT).show();
                //这边要写一个把编码传给服务器

                int role = user.getRole();
                if (role == 2) //老师发起签到
                {
                    setSignCode(mSbSelected.toString());
                } else if (role == 1) //学生签到
                {
                    setSignCodeStudent(mSbSelected.toString());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                handleMoveEvent(event);
                break;
        }

        return true;
    }

    private void setSignCodeStudent(String code) {
        final OkHttpClient client = new OkHttpClient();
        String path = "http://129.211.87.192/daoyunapi/public/index.php/";
        path = path + "sign";
        //int id = c.getNewsClassId();//班级id
        int id = user.getUserId();//用户id
        code = code.replaceAll(",", "");
        Gson gson = new Gson();
        Map<Object, Object> map = new HashMap<>();
        map.put("uid", id);
        map.put("code", code);
        map.put("lng", longitude);
        map.put("lat", latitude);
        String params = gson.toJson(map);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, params);
        ///////////////////////////
        Request request = new Request.Builder()
                .url(path)//请求的url
                .header("Authorization",user.getToken())
                .put(body)
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

                Log.e("TAG", "onResponse: " + info);
                JsonObject jsonObject = new JsonParser().parse(info).getAsJsonObject();
                JsonObject jsonObjectMeta = jsonObject.get("meta").getAsJsonObject();
                int code = jsonObjectMeta.get("status").getAsInt();
                String msg = "";
                msg = jsonObjectMeta.get("msg").getAsString();
                if (200 == code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                {
                    //这边要注意，获取的是用户对象getAsString();不行.getAsJsonObject();用这个
                    // JsonObject result = jsonObject.get("data").getAsJsonObject();//取出用户信息
                    Looper.prepare();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    //跳转回去
                    user.setEmpiricalValue(user.getEmpiricalValue() + 2);
                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    intent.putExtra("user", user);
                    getContext().startActivity(intent);

                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        });


        ////////////////


    }

    private void setSignCode(String code) {
        final OkHttpClient client = new OkHttpClient();
        String path = "http://129.211.87.192/daoyunapi/public/index.php/";
        path = path + "teacherSigns";
        int id = c.getNewsClassId();//班级id
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String signtime = df.format(new Date()).toString();// new Date()为获取当前系统时间
        code = code.replaceAll(",", "");
        Gson gson = new Gson();
        Map<Object, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("code", code);
        map.put("signtime", signtime);
        map.put("lng", longitude);
        map.put("lat", latitude);
        String params = gson.toJson(map);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, params);
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

                Log.e("TAG", "onResponse: " + info);
                JsonObject jsonObject = new JsonParser().parse(info).getAsJsonObject();
                JsonObject jsonObjectMeta = jsonObject.get("meta").getAsJsonObject();
                int code = jsonObjectMeta.get("status").getAsInt();
                String msg = "";
                msg = jsonObjectMeta.get("msg").getAsString();
                if (200 == code)//如果code等于200，则说明存在该用户，而且服务器还返回了该用户的信息
                {
                    //这边要注意，获取的是用户对象getAsString();不行.getAsJsonObject();用这个
                    // JsonObject result = jsonObject.get("data").getAsJsonObject();//取出用户信息
                    Looper.prepare();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        });


        ////////////////


    }

    private void handleMoveEvent(MotionEvent event) {
        int index = findCellIndex(event.getX(), event.getY());
        if (index != -1) {
            mCells[index].setSelected(true);
            mSbSelected.append(index).append(",");
        }
        invalidate();

        mCurrentX = event.getX();
        mCurrentY = event.getY();
    }

    private void handleDownEvent(MotionEvent event) {
        int index = findCellIndex(event.getX(), event.getY());
        if (index != -1) {
            mCells[index].setSelected(true);
            mSbSelected.append(index).append(",");
            invalidate();
        }

        mCurrentX = event.getX();
        mCurrentY = event.getY();
    }

    private int findCellIndex(float x, float y) {
        float cellX;
        float cellY;
        int result = -1;

        for (int i = 0; i < 9; i++) {
            if (mCells[i].isSelected()) {
                continue;
            }

            cellX = mCells[i].getCenterX();
            cellY = mCells[i].getCenterY();

            float tempX = cellX - x;
            float tempY = cellY - y;

            float distance = (float) Math.sqrt(tempX * tempX + tempY * tempY);

            if (distance < mCellRadius) {
                result = i;
                break;
            }
        }

        return result;
    }


}
}