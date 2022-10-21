package com.dlihasa.countdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 倒计时模块
 * 一、列表onDetachedFromWindow后可以恢复倒计时
 * 二、onAttachedToWindow简单设置重新倒计时时间不正确
 */
public class CountDownView extends androidx.appcompat.widget.AppCompatTextView {

    private SimpleDateFormat simpleDateFormat;
    private String timeFormat;
    private long desTime;
    private long diffTime = 0;

    private CountDownTimer countDownTimer;
    private ICountDownListener listener;

    public CountDownView(Context context) {
        super(context);
        initView(context,null);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public void setCountDownListener(ICountDownListener listener){
        this.listener = listener;
    }

    public void setFormat(String format){
        timeFormat = format;
    }

    public void setCompareTime(long desTime){
        this.setCompareTime(desTime,0);
    }

    public void setCompareTime(long desTime,long diffTime){
        this.desTime = desTime;
        this.diffTime = diffTime;
    }

    /**
     * 倒计时，小于等于0在外部判断，防止因为服务器端脚本不及时执行状态未改变而一直倒计时为0，循环调用接口的问题
     */
    public void countDownTime(long time) {
        cancelCountDown();
        if(time <= 0 && listener!=null){
            listener.onFinish();
            return;
        }
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long leftTime) {
                setText(msToFormat(leftTime,timeFormat));
            }

            @Override
            public void onFinish() {
                cancelCountDown();
                if(listener!=null){
                    listener.onFinish();
                }
            }
        };
        countDownTimer.start();
    }
    
    public void cancelCountDown(){
        if(countDownTimer!=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        long serverTime = System.currentTimeMillis() + diffTime;
        if(desTime >= serverTime){
            countDownTime(desTime-serverTime);
        }else{
            throw new IllegalArgumentException("setCompareTime() Need BIGGER TIME ONLY");
        }
    }

    /**
     * 组件内部处理释放资源，外部不需要操心重置倒计时以及资源释放
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelCountDown();
    }

    private String msToFormat(long ms, String format) {
        if(simpleDateFormat == null){
            simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        }
        return simpleDateFormat.format(ms);
    }

    private void initView(Context context, AttributeSet attrs) {
        if(attrs != null){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
            timeFormat = array.getString(R.styleable.CountDownView_countDownTimeFormat);
            array.recycle();
        }
        if(TextUtils.isEmpty(timeFormat)){
            timeFormat = "HH:mm:ss";
        }
    }
}
