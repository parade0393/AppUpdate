1. 引入方式 gradle `implementation
   'com.sanzhi.appupdate:appupdate:1.0.3'`

2. 使用方式

   ```java
   new AppUpdate(this)//上下文context
           .setAppId("nsibdczem16vuj5y")//APP_ID
           .setSmallIcon(R.mipmap.ic_launcher)//通知栏图标
           .update();//开始检查更新下载
   ```
3. 额外方法

| 方法                                              | 作用                      | 默认值                           |
|:--------------------------------------------------|:-------------------------|:---------------------------------|
| setTitle(String title)                            | 设置标题内容              | 温馨提示                         |
| setTitleSize(float titleSize)                     | 设置标题字体大小          | 17sp                             |
| setTitleColor(int color)                          | 设置标题文本颜色          | 0xff000000                       |
| setContent(String content)                        | 设置内容文本              | 有新版本,请更新                   |
| setContentSize(float contentSize)                 | 设置内容文本字体大小       | 15sp                            |
| setContentColor(int color)                        | 设置内容文本颜色          | 0xff9c9c9c                       |
| setPositionBtnResId(@IdRes int resId)             | 设置确定按钮的背景资源     | 和下面的方法互斥                 |
| setPositionBtnBgColor(int positionBtnBgColor)     | 设置确定按钮的背景颜色     | 设置了背景颜色就不会使用背景资源了 |
| setPositionText(String positionBtnText)           | 设置确定按钮的文本        | 更新                             |
| setPositionBtnTextColor(int positionBtnTextColor) | 设置确定按钮的文本颜色     | 0xffffffff                      |
| setPositionBtnTextSize(float spValue)             | 设置确定按钮的文本字体大小 | 13sp                             |
| setNegativeBtnResId(@IdRes int negativeBtnResId)  | 设置取消按钮的背景资源     | 和下面的方法互斥                 |
| setNegativeBtnBgColor(int negativeBtnBgColor)     | 设置取消按钮的背景颜色     | 设置了背景颜色就不会使用背景资源了 |
| setNegativeBtnText(String text)                   | 设置取消按钮的文本        | 取消                             |
| setNegativeBtnTextColor(int negativeBtnTextColor) | 设置取消按钮文本颜色       | 0xff9c9c9c                      |
| setNegativeBtnTextSize(float spValue)             | 设置取消按钮文本字体大小   | 13sp                            |

4. 更新库附带一个Dialog构建工具
   *  使用方法

        ```java
        CommonDialog commonDialog = new CommonDialog(this, R.layout.activity_main)//传入上下文和自定义布局
                .setListenItem(new int[]{})//传入需要监听的控件id数组
                .setListener(this);//传入监听器
        //其它方法都和原生dialog一样
        commonDialog.getWindow().setDimAmount(0f);//这是原生的方法，可以去掉dialog的遮罩
        commonDialog.show();
        ```
