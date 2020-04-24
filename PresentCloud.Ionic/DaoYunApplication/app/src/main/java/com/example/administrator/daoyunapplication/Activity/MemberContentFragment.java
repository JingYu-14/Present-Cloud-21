package com.example.administrator.daoyunapplication.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Adapter.ActivityListActivityAdapter;
import com.example.administrator.daoyunapplication.Adapter.ActivityListMemberAdapter;
import com.example.administrator.daoyunapplication.Model.Classes;
import com.example.administrator.daoyunapplication.Model.Disscuss;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//底部选项卡对应的Fragment
public class MemberContentFragment extends ListFragment//extends Fragment
{
    List<User> mUserList;
    private static Classes c;
    public  MemberContentFragment(){}
    @SuppressLint("ValidFragment")
    public  MemberContentFragment(Classes c){
        this.c=c;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //这边做数据的初始化，从服务器获取数据
    private void initClass(){
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));
        mUserList.add(new User("N190327124","zhangyin","福州大学数学与计算机学院","zhangyin","1143047418@qq.com","18250153365",0,"zhangyin",60));

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
        //布局文件中只有一个居中的ListView
        viewContent = inflater.inflate(R.layout.fragment_content,container,false);

//        TextView textView = (TextView) viewContent.findViewById(R.id.tv_content);
//        textView.setText(this.mTitle);


        mUserList = new ArrayList<>();
        initClass();
        Log.e("dafasf",c.toString());
        ActivityListMemberAdapter adapter = new ActivityListMemberAdapter(getContext(), R.layout.activity_member_list_item, mUserList);
        this.setListAdapter(adapter);
        return viewContent;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        User c= (User)l.getItemAtPosition(position);
        Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        //这边写跳转到任务的详细页面，提交任务页面
    }


}
