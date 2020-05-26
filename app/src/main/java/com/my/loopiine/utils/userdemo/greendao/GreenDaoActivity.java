package com.my.loopiine.utils.userdemo.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.my.loopiine.R;
import com.my.loopiine.utils.userdemo.greendao.bean.Student;

import java.util.ArrayList;
import java.util.List;

//http://blog.csdn.net/u013378580/article/details/52882575
public class GreenDaoActivity extends Activity {

    private static final String TAG="信息1";
    private CommitUtils commitUtils;

    private TextView jb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_greendao);
        commitUtils = new CommitUtils(this);

        jb = (TextView) findViewById(R.id.jb);
        InsertData(null);
        QueryData(null);
    }

    //插入数据
    public void InsertData(View view) {
        Log.i(TAG, "insert Data");
        Student student = new Student();
        student.setAddress("北京");
        student.setAge(23);
        //student.setId(10001l);//这里主键是自增的，所以强制设置会报错
        student.setName("张三");
        commitUtils.insertStudent(student);

    }

    //插入多条数据
    public void InsertMulData(View view) {
        Log.i(TAG, "insert Mut Data");
        List<Student> list = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setAddress("北京");
            student.setAge(23 + i);
//            student.setId(10001l + i);
            student.setName("张三");
            list.add(student);
        }
        commitUtils.insertMultStudent(list);
    }


    /**
     * //更改数据
     * @param view
     */
    public void updataData(View view) {
        Log.i(TAG, "data Data");
        Student student = new Student();
        student.setAge(1000);
        student.setName("xiaolei");
        student.setId(1l);
        student.setAddress("2432");
        commitUtils.updateStudent(student);
    }


    public void deleteData(View view) {
        Student student = new Student();
        student.setId(2l);
        commitUtils.deleteStudent(student);
    }

    public void oneList(View view) {
//        Student student = commitUtils.ListOneStudent(1);  
        Student student = commitUtils.ListOneStudent(1);
        Log.i(TAG, student.getName() + "");

    }

    public void mutiList(View view) {
        List<Student> list = commitUtils.ListAll();
//        if(list!=null)  
        Log.i(TAG, list.toString());
    }

    public  void QueryData(View view){
//       List<Student> query = commitUtils.Query();  


//       for(int i=0;i<query.size();i++)  
//       {  
//          Log.i(TAG, query.get(i).getAge().toString()+" :"+ query.get(i).getId()) ;  
//       }  

        List<Student> students = commitUtils.Query();
        if(students!=null)
            for(int i=0;i<students.size();i++) {
                Log.i(TAG, students.get(i).getAge() + " :" + students.get(i).getId());
                jb.setText(students.toString());
            }

    }


}
