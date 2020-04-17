package com.example.administrator.daoyunapplication.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.daoyunapplication.Activity.ActivityHome;
import com.example.administrator.daoyunapplication.Adapter.ListClassAdapter;
import com.example.administrator.daoyunapplication.Model.Class;
import com.example.administrator.daoyunapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2020/3/11 0011.
 */
//底部选项卡对应的Fragment
public class ContentFragment extends ListFragment//extends Fragment
{
    List<Class> mClassList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void initClass(){
        mClassList.add(new Class(1,"apple","工程实践","池老标1","2019级专硕"));
        mClassList.add(new Class(2,"banana","工程实践","池老标2","2019级专硕"));
        mClassList.add(new Class(3,"hot","工程实践","池老标3","2019级专硕"));
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

        Log.e("mType:",mType+" ,");
        mClassList = new ArrayList<>();
        if( mType==0) {
            initClass();
        } else if(mType==1){
            mClassList.add(new Class(5,"2222","工程实践","池老标4","2019级专硕"));
        }else if(mType==2){
            mClassList.add(new Class(6,"333333","工程实践","池老标5","2019级专硕"));
        }

        ListClassAdapter adapter = new ListClassAdapter(getContext(), R.layout.list_item, mClassList);
        this.setListAdapter(adapter);


        return viewContent;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
       // Toast.makeText(getActivity(), "You have selected " +position, Toast.LENGTH_SHORT).show();
        Class c= (Class)l.getItemAtPosition(position);
        Toast.makeText(getActivity(), "You have selected " +c.getNewsTeacherName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ActivityHome.class);
        intent.putExtra("classes", c);
        startActivity(intent);
    }


}
