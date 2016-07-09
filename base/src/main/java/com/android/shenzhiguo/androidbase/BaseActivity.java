package com.android.shenzhiguo.androidbase;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /** 是否沉浸状态栏 **/
    private boolean isSetStatusBar = true;

    /** 是否允许全屏 **/
    private boolean isAllowFullScreen = true;

    /** 是否禁止旋转屏幕 **/
    private boolean isAllowRoateScreen = false;

    /** 当前Activity渲染的视图View **/
    private View mContextView = null;

    protected String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        if(isAllowFullScreen){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if(isSetStatusBar) {
            setupStatusBar();
        }
        if(!isAllowRoateScreen) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(mContextView);
        bindViews();
        bindParams();
        bindListeners();
        doBusiness(this);
    }

    @LayoutRes
    protected abstract int bindLayout();

    /**
     * 绑定初始化Activity参数
     *
     * @param
     * */
    public abstract void bindParams();

    /**
     * 绑定视图
     *
     * @return
     * */
    public abstract void bindViews();

    /**
     * 绑定监听器
     *
     * @return
     * */
    public abstract int bindListeners();

    /**
     * View点击
     * */
    public abstract void viewClick(View v);

    /**
     * 绑定控件
     *
     * @param resId
     *
     * @returns
     * */
    protected <T extends View> T bind(@IdRes int resId){
        return (T) mContextView.findViewById(resId);
    }

    /**
     * 设置状态栏
     * */
    private void setupStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 业务逻辑
     *
     * @param mContext
     * */
    public abstract void doBusiness(Context mContext);

    /**
     * 页面跳转
     *
     * @param cls
     * */
    public void startActivity(Class<?> cls){
        startActivity(new Intent(this,cls));
    }

    /**
     * 携带数据的页面跳转
     *
     * @param cls
     * @param bundle
     * */
    public void startActivity(Class<?> cls,Bundle bundle){
        Intent intent = new Intent();
        intent.setClass(this,cls);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 有返回结果的页面跳转
     *
     * @param cls
     * @param bundle
     * @param requestCode
     * */
    public void startActivityForResult(Class<?> cls,Bundle bundle,int requestCode){
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        startActivityForResult(intent,requestCode);
    }

    /**
     * 消息提示
     *
     * @param msg
     * */
    protected void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置是否全屏
     * */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.isAllowFullScreen = allowFullScreen;
    }

    /**
     * 设置是否旋转屏幕
     * */
    public void setAllowRoateScreen(boolean allowRoateScreen) {
        this.isAllowRoateScreen = allowRoateScreen;
    }

    /**
     * 设置是否沉浸状态栏
     * */
    public void setSetStatusBar(boolean setStatusBar) {
        this.isSetStatusBar = setStatusBar;
    }

    @Override
    public void onClick(View v){
        viewClick(v);
    }

}
