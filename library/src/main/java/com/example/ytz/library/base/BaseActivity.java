package com.example.ytz.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ytz.library.R;
import com.example.ytz.library.control.ActivityControl;

/**
 * Created by admin on 2016/10/21.
 *
 * 1.模板模式封装使用流程
 *
 * 2.常用属性
 *
 * 3.组件间通信
 *
 * 4.标题栏  :  我们的Activity出来以后，自带标题栏 ，数量 ：3  2 1 0
 */

public abstract class BaseActivity extends FragmentActivity{
    public BaseActivity mActivity;
    public LayoutInflater mLayoutInflater;
    public FragmentManager mFragmentManager;
    public TextView mTextLeft,mTextCenter,mTextRight;
    //是否使用标题栏
    public boolean isUseTitleBar(){
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityControl.addActivity(this);
        mActivity = this;
        mLayoutInflater = LayoutInflater.from(this);
        mFragmentManager = this.getSupportFragmentManager();
        //定义初始化流程
        int layoutId = initLayout();
        if (isUseTitleBar() && TitleBarInfo.isUseTitleBar){
            addTitleBar(layoutId);
        }else{
            setContentView(layoutId);
        }

        initView();
        initData();
    }
    //activity销毁时，从集合移除

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityControl.removeActivity(this);
    }

    //添加标题栏的方法
    private void addTitleBar(int layoutId) {
        //创建线性布局，即标题栏布局和原布局的总布局
        LinearLayout layout = new LinearLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);

        //获取标题栏布局并添加到总布局
        View titleView = mLayoutInflater.inflate(TitleBarInfo.titleBarID,layout,false);
        layout.addView(titleView);

        //获取原本布局并添加到总布局
        View oldView = mLayoutInflater.inflate(layoutId,layout,false);
        layout.addView(oldView);

        //给activity重新设置布局（嵌套标题栏和原布局的总布局）
        setContentView(layout);
        //实例化标题栏控件
        mTextLeft = (TextView) this.findViewById(R.id.title_left);
        mTextCenter = (TextView) this.findViewById(R.id.title_center);
        mTextRight = (TextView) this.findViewById(R.id.title_right);
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
    //抽象初始化布局，控件，数据的方法
    public abstract int initLayout();
    public abstract void initView();
    public abstract void initData();
    //启动activity
    public void goActivity(Class activity){
        goActivity(activity,null,null);
    }
    //activity启动并传参
    public void goActivity(Class activity,String key,Bundle value){
        Intent intent = new Intent(this,activity);
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
        Intent intent = new Intent(this,service);
        if (key != null && value!=null) {
            intent.putExtra(key,value);
        }
        this.startService(intent);
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
}
