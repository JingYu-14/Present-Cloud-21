//package com.example.administrator.daoyunapplication.Activity;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.ListFragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.example.administrator.daoyunapplication.Adapter.ActivityListMessageAdapter;
//import com.example.administrator.daoyunapplication.Model.Disscuss;
//import com.example.administrator.daoyunapplication.Model.Message;
//import com.example.administrator.daoyunapplication.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
////底部选项卡对应的Fragment
//public class MessageContentFragment extends ListFragment//extends Fragment
//{
//    List<Message> mMessageList;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//    //这边做数据的初始化，从服务器获取数据
//    private void initClass(){
//        mMessageList.add(new Message(0,"班课通知","暂无通知"));
//        mMessageList.add(new Message(1,"小蓝消息","出勤率在班课中遥遥领先"));
//    }
//
//    private View viewContent;
//    private int mType = 0;//上选项卡的名称 编号
//    private String mTitle;//上选项卡的名称
//
//
//    public void setType(int mType) {
//        this.mType = mType;
//    }
//
//    public void setTitle(String mTitle) {
//    this.mTitle = mTitle;
//}
//
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        //布局文件中只有一个居中的ListView
//        viewContent = inflater.inflate(R.layout.fragment_content,container,false);
//
////        TextView textView = (TextView) viewContent.findViewById(R.id.tv_content);
////        textView.setText(this.mTitle);
//
//        Log.e("mType:",mType+" ,");
//        mMessageList = new ArrayList<>();
//        if( mType==0) {
//            initClass();
//        } else if(mType==1){
////            mMessageList.add(new Disscuss(1,1,1,"第六周任务","需求文档","2020-02-01","2020-02-02"));
//        }else if(mType==2){
////            mDisscussList.add(new Disscuss(1,1,1,"第六周任务","需求文档","2020-02-01","2020-02-02"));
//        }
//
//        ActivityListMessageAdapter adapter = new ActivityListMessageAdapter(getContext(), R.layout.activity_message_list_item, mMessageList);
//        this.setListAdapter(adapter);
//        return viewContent;
//    }
//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
//        Disscuss c= (Disscuss)l.getItemAtPosition(position);
//        Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
//        //这边写跳转到任务的详细页面，提交任务页面
//    }
//
//
//}

package com.example.administrator.daoyunapplication.Activity;

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
import com.example.administrator.daoyunapplication.Adapter.ActivityListMessageAdapter;
import com.example.administrator.daoyunapplication.Model.Disscuss;
import com.example.administrator.daoyunapplication.Model.Message;
import com.example.administrator.daoyunapplication.Model.User;
import com.example.administrator.daoyunapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//底部选项卡对应的Fragment
public class MessageContentFragment extends ListFragment//extends Fragment
{
    List<Message> mMessageList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    //这边做数据的初始化，从服务器获取数据
    private void initClass(){
        mMessageList.add(new Message(0,"班课通知","暂无通知"));
        mMessageList.add(new Message(1,"小蓝消息","出勤率在班课中遥遥领先"));
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


        mMessageList = new ArrayList<>();
        initClass();

        ActivityListMessageAdapter adapter = new ActivityListMessageAdapter(getContext(), R.layout.activity_member_list_item, mMessageList);
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

