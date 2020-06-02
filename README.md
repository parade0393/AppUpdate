1. 引入方式
   gradle `implementation 'com.sanzhi.appupdate:appupdate:1.0.0'`

2. 使用方式

   ```java
   new AppUpdate(this)//上下文context
           .setAppId("nsibdczem16vuj5y")//APP_ID
           .setSmallIcon(R.mipmap.ic_launcher)//通知栏图标
           .update();//开始检查更新下载
   ```