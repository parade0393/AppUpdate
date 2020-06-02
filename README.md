1. 引入方式
   gradle `implementation 'com.sanzhi.appupdate:appupdate:1.0.0'`

2. 使用方式

   ```java
   new AppUpdate(this)//上下文context
           .setAppId("nsibdczem16vuj5y")//APP_ID
           .setSmallIcon(R.mipmap.ic_launcher)//通知栏图标
           .update();//开始检查更新下载
   ```
3. 更新库附带一个Dialog构建工具
   *  使用方法

        ```java
        CommonDialog commonDialog = new CommonDialog(this, R.layout.activity_main)//传入上下文和自定义布局
                .setListenItem(new int[]{})//传入需要监听的控件id数组
                .setListener(this);//传入监听器
        //其它方法都和原生dialog一样
        commonDialog.getWindow().setDimAmount(0f);//这是原生的方法，可以去掉dialog的遮罩
        commonDialog.show();
        ```
