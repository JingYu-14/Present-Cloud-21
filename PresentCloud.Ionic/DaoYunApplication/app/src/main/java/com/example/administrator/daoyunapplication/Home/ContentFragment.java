package com.example.administrator.daoyunapplication.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.daoyunapplication.Adapter.ListMainAdapter;
import com.example.administrator.daoyunapplication.Model.Food;
import com.example.administrator.daoyunapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//底部选项卡对应的Fragment
public class ContentFragment extends ListFragment//extends Fragment
{
    List<Food> mFoodList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void initFood(){
        mFoodList.add(new Food("apple","工程实践","池老标","2019级专硕"));
        mFoodList.add(new Food("banana","工程实践","池老标","2019级专硕"));
        mFoodList.add(new Food("hot","工程实践","池老标","2019级专硕"));
    }

    private View viewContent;
    private int mType = 0;//上选项卡的名称 编号
    private String mTitle;//上选项卡的名称


    public void setType(int mType) {
        this.mType = mType;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //布局文件中只有一个居中的TextView
        viewContent = inflater.inflate(R.layout.fragment_content,container,false);

//        TextView textView = (TextView) viewContent.findViewById(R.id.tv_content);
//        textView.setText(this.mTitle);

        Log.e("mType:",mType+" ,");
        mFoodList = new ArrayList<>();
        if( mType==0) {
            initFood();
        } else if(mType==1){
            mFoodList.add(new Food("2222","工程实践","池老标","2019级专硕"));
        }else if(mType==2){
            mFoodList.add(new Food("333333","工程实践","池老标","2019级专硕"));
        }
        ListMainAdapter adapter = new ListMainAdapter(getContext(), R.layout.list_item, mFoodList);
        this.setListAdapter(adapter);
        return viewContent;
    }
}
