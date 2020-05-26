package com.my.loopiine.utils.userdemo.rxbus;

import com.my.loopiine.uitl.uitls2.RxBus;
import com.socks.library.KLog;

import rx.Observable;
import rx.functions.Action1;

/**
 * https://segmentfault.com/a/1190000005867208
 * RxBus使用
 */

public class RxBusDemo {

    private Observable<String> zhang;

    private  void demo(){

        zhang = RxBus.get().register("zhang", String.class);
        zhang.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                KLog.e("zhang", "+++++++++++++++++++++++++++++++"+s);
            }
        });

    }


    //使用  RxBus.get().post("finish", false);
    private Observable<Boolean> mFinishObservable;
    private void initFinishRxBus() {
        mFinishObservable = RxBus.get().register("finish", Boolean.class);
        mFinishObservable.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean themeChange) {

            }
        });
    }

   // @Override
    protected void onDestroy() {
        //super.onDestroy();
        if (mFinishObservable != null) {
            RxBus.get().unregister("finish", mFinishObservable);
        }
    }




    //java封装类
    //RxBus.get().post("student",new RxBusDemo.StudentEvent("007","小明"));
    public static class StudentEvent {
        private String id;
        private String name;
        public StudentEvent(String id, String name) {
            this.id = id;
            this.name = name;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }


}
