package com.my.loopiine.utils.userdemo.greendao.generate;

import android.database.sqlite.SQLiteDatabase;

import com.my.loopiine.utils.userdemo.greendao.bean.Student;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig studentDaoConfig;

    private final StudentDao studentDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        studentDaoConfig = daoConfigMap.get(StudentDao.class).clone();
        studentDaoConfig.initIdentityScope(type);

        studentDao = new StudentDao(studentDaoConfig, this);

        registerDao(Student.class, studentDao);
    }
    
    public void clear() {
        studentDaoConfig.getIdentityScope().clear();
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

}