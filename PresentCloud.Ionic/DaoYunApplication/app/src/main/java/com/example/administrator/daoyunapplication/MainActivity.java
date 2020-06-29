package com.example.administrator.daoyunapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.administrator.daoyunapplication.SignActivity.SignActivity;

public class MainActivity extends AppCompatActivity {
    private static final int BAIDU_READ_PHONE_STATE = 100;//定位权限请求
    private static final int PRIVATE_CODE = 1315;//开启GPS权限
    private LocationManager lm;

    private Button signin;
    private Button enroll;
    Intent intent;
    public LocationClient mLocationClient;
    private static double latitude=0.0;//纬度
    private static double longitude=0.0;//经度
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin = (Button)findViewById(R.id.signin);
        enroll = (Button)findViewById(R.id.enroll);
        signin.setOnClickListener(onClickListener);
        enroll.setOnClickListener(onClickListener);

        showGPSContacts();
    }
    /**
     * 检测GPS、位置权限是否开启
     */

    public void showGPSContacts() {

        //得到系统的位置服务，判断GPS是否激活
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (ok||network) {//开了定位服务
            if (Build.VERSION.SDK_INT >= 23) { //判断是否为android6.0系统版本，如果是，需要动态添加权限
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {// 没有权限，申请权限。
                    ActivityCompat.requestPermissions(this, LOCATIONGPS, BAIDU_READ_PHONE_STATE);
                } else {
                   // initLocationOption();//有权限，进行相应的处理
                    //获取经纬度
                    //requestLocation();

                }
            } else {
               // initLocationOption();//有权限，进行相应的处理
                //获取经纬度
                ///requestLocation();
            }
        } else {
            Toast.makeText(this, "系统检测到未开启GPS定位服务,请开启", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, PRIVATE_CODE);
        }
    }

    /**
     * Android6.0申请权限的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
//             requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                //如果用户取消，permissions可能为null.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0) { //有权限
                    // 获取到权限，作相应处理
                    //initLocationOption();//有权限，进行相应的处理
                } else {
                    /*
                    * 无权限
                    * */

                    Toast.makeText(this, "你未开启定位权限!", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                break;
        }
    }

    static final String[] LOCATIONGPS = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
    };

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.signin:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                   // intent.putExtra("pathFile", wPath);
                    startActivity(intent);
                    break;
                case R.id.enroll:
                    intent = new Intent();
                    intent.setClass(MainActivity.this, ReignActivity.class);
                    // intent.putExtra("pathFile", wPath);
                    startActivity(intent);
                    break;

            }

        }
    };
}
