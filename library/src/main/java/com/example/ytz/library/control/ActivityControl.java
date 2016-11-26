package com.example.ytz.library.control;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by admin on 2016/10/21.
 */

public class ActivityControl {
    private static List<Activity> mActivities = new ArrayList<>();
    //activity添加到统一管理
    public static void addActivity(Activity activity){
        mActivities.add(activity);
    }
    //activity移除出统一管理
    public static void removeActivity(Activity activity){
        mActivities.remove(activity);
    }
    //从集合中获取一个activity
    public static Activity getActivity(Class activity){
        for (Activity activity1 : mActivities) {
            if(activity1.getClass() == activity){
                return activity1;
            }
        }
        return null;
    }
    //关闭所有activity
    public static void killAll(){
        Iterator<Activity> it = mActivities.iterator();
        while(it.hasNext()){
            it.next().finish();
        }
    }
}
