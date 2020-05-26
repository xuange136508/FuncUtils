package com.my.loopiine.uitl;

/**
 * Created by Administrator on 2016/12/26.
 */

public class NoFastClickUtils {

    private static long lastClickTime=0;//上次点击的时间
    private static int spaceTime = 1000;//时间间隔

    public static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();//当前系统时间
        boolean isAllowClick;//是否允许点击
             if (currentTime - lastClickTime > spaceTime) {
                        isAllowClick= false;
                   } else {
                        isAllowClick = true;
                   }
            lastClickTime = currentTime;
        return isAllowClick;
    }

    private  void user(){
        if(NoFastClickUtils.isDoubleClick()) {
            //快速点击后的逻辑，可以提示用户点击太快，休息一会

        }else{
            //正常点击的逻辑

        }
    }


    //方法2
//    屏蔽Button的点击事件
//    步骤一：在Button的点击事件里面
//    btn.setClickable(false);//设置button不可以点击
//    步骤二：在网络请求完成的时候（请求获得数据或者请求失败，请求取消等操作）
//            btn.setClickable(true);//设置button可以点击


    //方法3
//    自定义一个NoDoubleClickListener,继承自OnClickListener：
//    //代码2
//    public abstract class NoDoubleClickListener implements OnClickListener {
//        public static final int MIN_CLICK_DELAY_TIME = 1000;
//        private long lastClickTime = 0;
//        @Override
//        public void onClick(View v) {
//            long currentTime = Calendar.getInstance().getTimeInMillis();
//            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
//                lastClickTime = currentTime;
//                onNoDoubleClick(v);
//            }
//        }
//    }
//    使用方法—— 给submitButton设置点击事件时用NoDoubleClickListener代替OnClickListener，并且实现方法onNoDoubleClick代替onClick即可，像这样：
//    submitButton.setOnClickListener(new NoDoubleClickListener() {
//        @Override
//        public void onNoDoubleClick(View v) {
//            submitOrder();
//        }
//    });

}
