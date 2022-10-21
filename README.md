# easykits
A Simple Kits For Android

<h1>Android倒计时组件**</h1><br>

1、适用于列表及其他任何场景的倒计时<br>
2、组件内部处理资源释放和倒计时的处理<br>
3、使用时只需设置目标时间（目标时间需要大于当前时间），可以自由支持展示文本格式<br>
4、倒计时结束需要处理事务时传入结束监听，自行处理结束业务。<br>


<h1>使用方式：</h1>
<h2>（1）在project下的build.gradle文件中加入</h2>
```
repositories {
   mavenCentral()     
}
```
<h2>(2)在app下的build.gradle文件中引入依赖</h2>
```
implementation 'cn.dlihasa.easykits:countdown:x.x.x
```
<h2>（3）在xml中引入控件</h2>
...

<h2>（4）代码中使用方式如下</h2>

```
CountDownView countDownView = (CountDownView)findViewById(R.id.countDownView);
countDownView.setCompareTime(System.currentTimeMillis()+2000);//设置目标时间
countDownView.setFormat("剩余时间：mm:hh:ss");//支持xml配置文本格式
countDownView.setCountDownListener(new ICountDownListener() {
    @Override
    public void onFinish() {
        //设置监听结束业务
    }
});
```
