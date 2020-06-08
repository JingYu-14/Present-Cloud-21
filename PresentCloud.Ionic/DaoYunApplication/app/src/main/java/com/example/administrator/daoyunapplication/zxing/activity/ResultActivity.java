package com.example.administrator.daoyunapplication.zxing.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.administrator.daoyunapplication.zxing.R;
import com.example.administrator.daoyunapplication.Home.HomeActivity;
import com.example.administrator.daoyunapplication.LoginActivity;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.example.administrator.daoyunapplication.zxing.decode.DecodeThread;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResultActivity extends Activity {

	private ImageView mResultImage;
	private TextView mResultText;
	private String result="";
	private int userId;
	private User user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		Bundle extras = getIntent().getExtras();

//		mResultImage = (ImageView) findViewById(R.id.result_image);
		mResultText = (TextView) findViewById(R.id.result_text);

		if (null != extras) {
//			int width = extras.getInt("width");
//			int height = extras.getInt("height");
//
//			LayoutParams lps = new LayoutParams(width, height);
//			lps.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//			lps.leftMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
//			lps.rightMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

//			mResultImage.setLayoutParams(lps);

			result = extras.getString("result");
			userId = extras.getInt("userId");
			user = (User)extras.getSerializable("user");
			//Toast.makeText(this,result,Toast.LENGTH_SHORT).show();

			yesaddClass();
			mResultText.setText(result);
//			Bitmap barcode = null;
//			byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
//			if (compressedBitmap != null) {
//				barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
//				// Mutable copy:
//				barcode = barcode.copy(Bitmap.Config.RGB_565, true);
//			}

//			mResultImage.setImageBitmap(barcode);
		}
	}
	//加入班级
	public void yesaddClass(){
		final OkHttpClient client = new OkHttpClient();
		String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";
		path = path + "studentClasses";
//		int id=user.getUserId();//用户id
//		Log.e("uid",id+"");
		Gson gson = new Gson();
		Map<Object,Object> map = new HashMap<>();
		map.put("uid",userId);
		map.put("code", result);
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
				Toast.makeText(ResultActivity.this, "网络连接失败！", Toast.LENGTH_SHORT).show();
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
					Toast.makeText(ResultActivity.this,msg,Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
					intent.putExtra("user",user);
					startActivity(intent);
					Looper.loop();
				}else{
					Looper.prepare();
					Toast.makeText(ResultActivity.this,msg,Toast.LENGTH_SHORT).show();
					Looper.loop();
				}

			}
		});


	}
}
