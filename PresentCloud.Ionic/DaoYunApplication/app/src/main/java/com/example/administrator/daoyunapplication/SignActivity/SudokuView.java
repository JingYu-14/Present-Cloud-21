package com.example.administrator.daoyunapplication.SignActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2020/4/18 0018.
 */

public class SudokuView extends View {
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
       private TextView test;
    /**
     * 下边是三个构造方法：每一个构造方法中有一个初始化操作
     */
    public SudokuView(Context context) {
        super(context);
//        reSet_bt = (Button)findViewById(R.id.reSet_bt);
//        start_bt = (Button)findViewById(R.id.start_bt);
//        reSet_bt.setOnClickListener(new SignButton());
//        start_bt.setOnClickListener(new SignButton());
        init();
        test = (TextView) findViewById(R.id.test);
        String i=test.getText().toString();
        Log.e("bjjb",i);

    }

    public SudokuView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        reSet_bt = (Button)findViewById(R.id.reSet_bt);
//        start_bt = (Button)findViewById(R.id.start_bt);
//        reSet_bt.setOnClickListener(new SignButton());
//        start_bt.setOnClickListener(new SignButton());
        init();

        test = (TextView) findViewById(R.id.test);
        String i=test.getText().toString();
        Log.e("bjjb",i);

    }

    public SudokuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        reSet_bt = (Button)findViewById(R.id.reSet_bt);
//        start_bt = (Button)findViewById(R.id.start_bt);
//        reSet_bt.setOnClickListener(new SignButton());
//        start_bt.setOnClickListener(new SignButton());
        init();
        test = (TextView) findViewById(R.id.test);
        String i=test.getText().toString();
        Log.e("bjjb",i);

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
                Toast.makeText(getContext(), mSbSelected.toString(),
                        Toast.LENGTH_SHORT).show();
                //这边要写一个把编码传给服务器
                //老师发起签到
                setSignCode(mSbSelected.toString());
                break;
            case MotionEvent.ACTION_MOVE:
                handleMoveEvent(event);
                break;
        }

        return true;
    }
    private void setSignCode(String coed){
        final OkHttpClient client = new OkHttpClient();
        String path="http://3r1005r723.wicp.vip/daoyunapi/public/index.php/";

        path = path + "teacherSigns";
        int id;//班级id
        String signtime;//签到时间
        String format = String.format(path);




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
