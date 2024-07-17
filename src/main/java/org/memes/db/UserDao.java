package org.memes.db;

import org.memes.context.DBAppContext;


import javax.sql.DataSource;


public class UserDao {
    private final DataSource dataSource = DBAppContext.getDataSource();
    private static UserDao dao;
    public static UserDao getDao(){
        if (dao == null){
            dao = new UserDao();
        }
        return dao;
    }
    private UserDao(){}
}
