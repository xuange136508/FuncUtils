package com.my.loopiine.utils.userdemo.greendao;

import java.util.List;


import android.content.Context;

import com.my.loopiine.utils.userdemo.greendao.bean.Student;
import com.my.loopiine.utils.userdemo.greendao.generate.StudentDao;

import de.greenrobot.dao.query.QueryBuilder;

public class CommitUtils {
    private DaoManager manager;

    public CommitUtils(Context context) {
        manager = DaoManager.getInstance();
        manager.init(context);
    }

    /**
     * 完成对数据库表的插入操作
     *
     * @param student
     * @return
     */
    public boolean insertStudent(Student student) {
        boolean flag = false;
        flag = manager.getDaoSession().insert(student) != -1 ? true : false;
        return flag;
    }

    /**
     * 完成对数据库的多次插入
     *
     * @param students
     * @return
     */
    public boolean insertMultStudent(final List<Student> students) {
        boolean flag = false;

        try { // 启动一个线程，执行多次插入

            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Student student : students) {
                        manager.getDaoSession().insertOrReplace(student);
                    }

                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新对student某一条记录的修改
     * @param student
     * @return
     */
    public boolean updateStudent(Student student) {
        boolean flag = false;
        try {
            manager.getDaoSession().update(student);
            flag = true;
        } catch (Exception e)

        {
            e.printStackTrace();
        }


        return flag;
    }


    /**
     * 删除一条数据
     * @param student
     * @return
     */
    public boolean deleteStudent(Student student) {

        boolean flag = false;
        try {
            // 删除一条记录
            manager.getDaoSession().delete(student);

            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        manager.getDaoSession().deleteAll(Student.class);

        return flag;

    }
//

    /**
     * 返回多行记录
     * @return
     */
    public List<Student> ListAll() {
        return manager.getDaoSession().loadAll(Student.class);
    }

    /**
     * 按照主键返回单行记录
     * @param key
     * @return
     */
    public Student ListOneStudent(long key) {
        return manager.getDaoSession().load(Student.class, key);
    }


    /**
     * 查询数据 条件查询
     * @return
     */
    public   List<Student> Query1() {


        return manager.getDaoSession().queryRaw(Student.class, "where name like ? and _id > ?", new String[]{"%张三%", "2"});

    }

    /**
     * 查询数据 对于数据库不熟悉可使用这种方式
     * @return
     */
    public List<Student> Query() {

        QueryBuilder<Student> builder = manager.getDaoSession().queryBuilder(Student.class);
        List<Student> list = builder.where(StudentDao.Properties.Age.between(23, 26))
                .where(StudentDao.Properties.Address.like("北京")).list();
//        builder.orderAsc(StudentDao.Properties.Age);  

        return list;
    }

}  