package com.example.administrator.daoyunapplication.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.R;
import com.example.administrator.daoyunapplication.zxing.activity.CaptureActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import java.util.Hashtable;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2020/5/15 0015.
 */

public class QRcodeImage extends AppCompatActivity {
    private ImageView im1;  //imageview图片
    private int w,h;        //图片宽度w,高度h
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_rcode_image);
        //扫一扫
        Button bt1=(Button)findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QRcodeImage.this,
                        CaptureActivity.class);
                startActivity(intent);


          }
        });
        //生成二维码
        Button bt2=(Button)findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText=(EditText)findViewById(R.id.editText);
                String classCode=getRandomString(6);
                createQRcodeImage(classCode);//自定义转换内容
            }
        });

        Button bt3=(Button)findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                im1=(ImageView)findViewById(R.id.imageView);
                recogQRcode(im1);
            }
        });


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

    //转换成二维码QRcode的函数。参数为一个字符串,生成二维码
    public void createQRcodeImage(String url)
    {
        im1=(ImageView)findViewById(R.id.imageView);
        w=im1.getWidth();
        h=im1.getHeight();
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
    //识别二维码的函数
    public void recogQRcode(ImageView imageView){
        Bitmap QRbmp = ((BitmapDrawable) (imageView).getDrawable()).getBitmap();   //将图片bitmap化
        int width = QRbmp.getWidth();
        int height = QRbmp.getHeight();
        int[] data = new int[width * height];
        QRbmp.getPixels(data, 0, width, 0, 0, width, height);    //得到像素
        RGBLuminanceSource source = new RGBLuminanceSource(QRbmp);   //RGBLuminanceSource对象
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result re = null;
        try {
            //得到结果
            re = reader.decode(bitmap1);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        //Toast出内容
        Toast.makeText(QRcodeImage.this,re.getText(),Toast.LENGTH_SHORT).show();

        //利用正则表达式判断内容是否是URL，是的话则打开网页
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//比对
        Matcher mat = pat.matcher(re.getText().trim());
        if (mat.matches()){
            Uri uri = Uri.parse(re.getText());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);//打开浏览器
            startActivity(intent);
        }

    }

    //识别图片所需要的RGBLuminanceSource类
    public class RGBLuminanceSource extends LuminanceSource {

        private byte bitmapPixels[];

        protected RGBLuminanceSource(Bitmap bitmap) {
            super(bitmap.getWidth(), bitmap.getHeight());

            // 首先，要取得该图片的像素数组内容
            int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
            this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());

            // 将int数组转换为byte数组，也就是取像素值中蓝色值部分作为辨析内容
            for (int i = 0; i < data.length; i++) {
                this.bitmapPixels[i] = (byte) data[i];
            }
        }

        @Override
        public byte[] getMatrix() {
            // 返回我们生成好的像素数据
            return bitmapPixels;
        }

        @Override
        public byte[] getRow(int y, byte[] row) {
            // 这里要得到指定行的像素数据
            System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
            return row;
        }
    }



}
