package com.example.ytz.library.net;

/**
 * Created by Administrator on 2016/10/18.
 * 
 * 回调机制，在创建NetCallBack类时就要覆写方法
 *
 *
 * //        我的网络访问.set网络访问结束Listener（）{
 //            public void onSuccess结束{
 //                
 //            }
 //        
 //            public void onError结束{
 //            
 //            }
 //            
 //        }
 */
public abstract class NetCallback<T> {
    public abstract void onSuccess(T t);
    public abstract void onError(Throwable throwable);
    public abstract void onFinished();
}
