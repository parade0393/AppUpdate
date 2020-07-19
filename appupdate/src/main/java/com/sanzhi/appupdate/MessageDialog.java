package com.sanzhi.appupdate;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;

/**
 * @author : parade
 * date : 2020 07 2020/7/19
 * description :
 */
public class MessageDialog implements CommonDialog.OnAllItemClickListener {
    private static MessageDialog messageDialog;
    private Context context;
    private CommonDialog dialog;

    private TextView title;
    private TextView content;
    private TextView negativeBtn;
    private TextView positiveBtn;
    /** 弹窗标题 */
    private String titleText = "温馨提示";
    /** 标题文字大小 */
    private float titleSize = 17;
    private int titleColor = 0xff000000;
    /** 弹窗内容 */
    private String contentText = "有新版本,请更新";
    /** 内容文字大小 */
    private float contentSize = 13;
    private int contentColor = 0xff9c9c9c;
    /** 确定按钮背景资源 */
    private int positionBtnResId = R.drawable.btn_bac;
    /** 确定按钮背景颜色 */
    private int positionBtnBgColor;
    /** 确定按钮文本 */
    private String positionBtnText = "更新";
    /** 确定按钮文字颜色 */
    private int positionBtnTextColor = 0xffffffff;
    /** 确定按钮文字大小 */
    private float positionBtnTextSize = 13;
    /** 取消按钮背景资源 */
    private int negativeBtnResId = R.drawable.btn_bac_gray;
    /** 取消按钮背景颜色 */
    private int negativeBtnBgColor;
    /** 取消按钮文本 */
    private String negativeBtnText = "取消";
    /** 取消按钮文字颜色 */
    private int negativeBtnTextColor = 0xff9c9c9c;
    /** 取消按钮文字大小 */
    private float negativeBtnTextSize = 13;

    private OnPositionBtnClickListener positionBtnClickListener;
    private OnNegativeBtnClickListener negativeBtnClickListener;

    private MessageDialog(Context context){
        this.context = context.getApplicationContext();
        dialog = new CommonDialog(context, R.layout.dialog_update)
                .setListenItem(new int[]{R.id.btnNegativeUpdate,R.id.btnPositiveUpdate})
                .setListener(this);
        title = dialog.findViewById(R.id.tvTitleUpdate);
        content = dialog.findViewById(R.id.tvContentUpdate);
        negativeBtn = dialog.findViewById(R.id.btnNegativeUpdate);
        positiveBtn = dialog.findViewById(R.id.btnPositiveUpdate);
    }

    public static MessageDialog getInstance(Context context){
        if (null == messageDialog){
            messageDialog = new MessageDialog(context);
        }
        return messageDialog;
    }


    public MessageDialog setTitle(String title){
        this.titleText = title;
        return this;
    }

    public MessageDialog setTitleSize(float titleSize){
        this.titleSize = titleSize;
        return this;
    }

    public MessageDialog setTitleColor(int color){
        this.titleColor = color;
        return this;
    }

    public MessageDialog setContent(String content){
        this.contentText = content;
        return this;
    }

    public MessageDialog setContentSize(float contentSize){
        this.contentSize = contentSize;
        return this;
    }

    public MessageDialog setContentColor(int color){
        this.contentColor = color;
        return this;
    }

    public MessageDialog setPositionBtnResId(@IdRes int resId){
        this.positionBtnResId = resId;
        return this;
    }

    public MessageDialog setPositionBtnBgColor(int positionBtnBgColor){
        this.positionBtnBgColor = positionBtnBgColor;
        return this;
    }

    public MessageDialog setPositionText(String positionBtnText){
        this.positionBtnText = positionBtnText;
        return this;
    }

    public MessageDialog setPositionBtnTextColor(int positionBtnTextColor){
        this.positionBtnBgColor = positionBtnTextColor;
        return this;
    }

    public MessageDialog setPositionBtnTextSize(float spValue){
        this.positionBtnTextSize = spValue;
        return this;
    }

    public MessageDialog setNegativeBtnResId(@IdRes int negativeBtnResId){
        this.negativeBtnResId = negativeBtnResId;
        return this;
    }

    public MessageDialog setNegativeBtnBgColor(int negativeBtnBgColor){
        this.negativeBtnBgColor = negativeBtnBgColor;
        return this;
    }

    public MessageDialog setNegativeBtnTextColor(int negativeBtnTextColor){
        this.negativeBtnTextColor = negativeBtnTextColor;
        return this;
    }

    public MessageDialog setNegativeBtnText(String text){
        this.negativeBtnText = text;
        return this;
    }

    public MessageDialog setNegativeBtnTextSize(float spValue){
        this.negativeBtnTextSize = spValue;
        return this;
    }

    public MessageDialog setGravity(Integer gravity){
        dialog.setGravity(gravity);
        return this;
    }

    public MessageDialog build(){
        //标题设置
        title.setText(titleText);
        title.setTextColor(titleColor);
        title.setTextSize(TypedValue.COMPLEX_UNIT_PX,sp2px(titleSize));

        //内容设置

        content.setText(contentText);
        content.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(contentSize));
        content.setTextColor(contentColor);

        //取消按钮设置

        negativeBtn.setText(negativeBtnText);
        negativeBtn.setTextSize(negativeBtnTextSize);
        negativeBtn.setTextColor(negativeBtnTextColor);
        if (negativeBtnBgColor != 0){
            //如果设置了取消按钮的背景颜色
            negativeBtn.setBackgroundColor(negativeBtnBgColor);
        }else {
            negativeBtn.setBackgroundResource(negativeBtnResId);
        }

        //确定按钮
        positiveBtn.setText(positionBtnText);
        positiveBtn.setTextColor(positionBtnTextColor);
        positiveBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, sp2px(positionBtnTextSize));
        if (positionBtnBgColor != 0){
            //如果设置了确定按钮的背景颜色
            positiveBtn.setBackgroundColor(positionBtnBgColor);
        }else {
            positiveBtn.setBackgroundResource(positionBtnResId);
        }
        return this;
    }


    @Override
    public void handleClick(CommonDialog commonDialog, View view) {
        if (view.getId() == R.id.btnNegativeUpdate) {
            if (null != negativeBtnClickListener) {
                negativeBtnClickListener.onNegativeBtnClick(commonDialog, view);
            }
        }else if (view.getId() == R.id.btnPositiveUpdate){
            if (null != positionBtnClickListener){
                positionBtnClickListener.onPositionBtnClick(commonDialog,view);
            }
        }
    }

    public interface OnPositionBtnClickListener{
        void onPositionBtnClick(CommonDialog commonDialog,View view);
    }

    public interface OnNegativeBtnClickListener{
        void onNegativeBtnClick(CommonDialog commonDialog,View view);
    }

    public MessageDialog setOnPositionBtnClickListener(OnPositionBtnClickListener listener){
        this.positionBtnClickListener = listener;
        return this;
    }

    public MessageDialog setOnNegativeBtnClickListener(OnNegativeBtnClickListener listener){
        this.negativeBtnClickListener = listener;
        return this;
    }

    public void show(){
        if (null == dialog)return;
        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    public void dismiss(){
        if (null == dialog)return;
        if (dialog.isShowing()){
            dialog.dismiss();
        }
    }

    private int sp2px(final float spValue) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
