package com.example.ytz.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ytz.library.R;

/**
 * Created by admin on 2016/10/21.
 */

public abstract class BaseFragment extends Fragment {
    public BaseActivity mActivity;
    public LayoutInflater mLayoutInflater;
    public FragmentManager mFragmentManager;
    public View fragView;
    public TextView mTextLeft,mTextCenter,mTextRight;
    //返回是否添加标题栏
    public boolean isUseTitleBar() {
        return true;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) this.getActivity();
        mLayoutInflater = LayoutInflater.from(mActivity);
        mFragmentManager = mActivity.getSupportFragmentManager();

        int layoutFragId = initLayoutFragment();
        fragView= mLayoutInflater.inflate(layoutFragId,container,false);
        //判断是否使用标题栏，使用的话，将原有布局嵌套到定义有标题栏的布局内
        if(isUseTitleBar() && TitleBarInfo.isUseTitleBar){
            addTitleBar(layoutFragId);
        }
        initFragView();
        initFragData();
        return fragView;
    }
    //添加标题栏的方法
    private void addTitleBar(int layoutId) {
        //创建线性布局，即标题栏布局和原布局的总布局
        LinearLayout layout = new LinearLayout(mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        //获取标题栏布局并添加到总布局
        View titleView = mLayoutInflater.inflate(TitleBarInfo.titleBarID,layout,false);
        layout.addView(titleView);

        //获取原本布局并添加到总布局
        layout.addView(fragView);

        //将现有布局（嵌套标题栏和原布局的总布局），赋值到frag布局
        fragView = layout;
        //实例化标题栏控件
        mTextLeft = (TextView) titleView.findViewById(R.id.title_left);
        mTextCenter = (TextView) titleView.findViewById(R.id.title_center);
        mTextRight = (TextView) titleView.findViewById(R.id.title_right);
        if (mTextLeft != null){
            mTextLeft.setVisibility(View.INVISIBLE);
        }
        if (mTextRight != null) {
            mTextRight.setVisibility(View.INVISIBLE);
        }
    }
    //设置标题控件
    public void setTextLeft(String text){
        setTextLeft(text,null);
    }
    public void setTextLeft(String text, View.OnClickListener listener){
        if (mTextLeft != null) {
            mTextLeft.setVisibility(View.VISIBLE);
            mTextLeft.setText(text);
            if (listener != null) {
                mTextLeft.setOnClickListener(listener);
            }
        }
    }
    public void setTextCenter(String text){
        setTextCenter(text,null);
    }
    public void setTextCenter(String text, View.OnClickListener listener){
        if (mTextCenter != null) {
            mTextCenter.setText(text);
            if (listener != null) {
                mTextCenter.setOnClickListener(listener);
            }
        }
    }
    public void setTextRight(String text){
        setTextRight(text,null);
    }
    public void setTextRight(String text, View.OnClickListener listener){
        if (mTextRight != null) {
            mTextRight.setVisibility(View.VISIBLE);
            mTextRight.setText(text);
            if (listener != null) {
                mTextRight.setOnClickListener(listener);
            }
        }
    }
    //启动activity
    public void goActivity(Class activity){
        goActivity(activity,null,null);
    }
    //activity启动并传参
    public void goActivity(Class activity,String key,Bundle value){
        Intent intent = new Intent(mActivity,activity);
        if (key != null && value!=null) {
            intent.putExtra(key,value);
        }
        this.startActivity(intent);
    }
    //启动service
    public void goService(Class service){
        goService(service,null,null);
    }
    //启动service并传参
    public void goService(Class service,String key,Bundle value){
        Intent intent = new Intent(mActivity,service);
        if (key != null && value!=null) {
            intent.putExtra(key,value);
        }
        mActivity.startService(intent);
    }
    //添加fragment
    public void addFragment(int desId, Fragment fragment){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(desId,fragment,fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
    //移除fragment
    public void removeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        fragmentTransaction.commit();
    }
    //替换fragment
    public void replaceFragment(int desId,Fragment fragment){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(desId,fragment,fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }
    //隐藏fragment
    public void hideFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }
    //显示fragment
    public void showFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }
    //定义初始化布局样式，控件，数据的抽象方法
    protected abstract int initLayoutFragment();
    protected abstract void initFragView();
    protected abstract void initFragData();
    public View findViewById(int resId){
        return  fragView.findViewById(resId);
    }
}
