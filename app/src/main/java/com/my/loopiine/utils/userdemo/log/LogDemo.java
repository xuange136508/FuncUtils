package com.my.loopiine.utils.userdemo.log;

import com.socks.library.KLog;

/**
 * KLog的特点:
         支持显示行号
         支持显示Log所在函数名称
         支持无Tag打印
         支持点击函数名称，跳转至执行文件位置
         支持JSON字符串解析打印
         依赖库非常小，核心代码200行，只有4K
         KLog.v()
         KLog.d()
         KLog.i()
         KLog.w()
         KLog.e()
         KLog.a()

         1 KLog.d(String)
           使用这个方法，可以正常打印，默认Tag是当前类的名称
        2 KLog.d(Tag,String)
            这个方法和我们最常用的一样
        3 KLog.json(String)
            使用这个方法可以打印JSON格式的字符串，Tag默认为当前类的名称
 */

public class LogDemo {

    public void demo(){

        KLog.d("你好");


    }


}
