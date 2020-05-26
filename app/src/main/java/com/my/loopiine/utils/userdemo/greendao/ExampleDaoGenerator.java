package com.my.loopiine.utils.userdemo.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {
    public static void main(String[] args) {
        //生成实体类entity 即对应的表
        Schema schema = new Schema(1, "com.student.entity");
        //添加节点
        addStudent(schema);

        schema.setDefaultJavaPackageDao("com.student.dao");// 设置数据的会话层

        //C:\Users\Administrator\Desktop
        //将生成的内容放在指定的路径下C:\Users\admin\Desktop\shujuku\MyApplication\app\src\main\java-gen
        try {
            new DaoGenerator().generateAll(schema, "C:/Users/Administrator/Desktop/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    ////创建数据库的表
    private static void addStudent(Schema schema) {

        Entity entity = schema.addEntity("Student"); //创建数据库的表


        /**
         * 设置字符串获其他类型为主键
         * entity.addStringProperty("身份证号").primaryKey();
         */
        //当前表中的列

        entity.addIdProperty();// 主键
        entity.addStringProperty("name");
        entity.addStringProperty("address");
        entity.addIntProperty("age");

    }


}  
