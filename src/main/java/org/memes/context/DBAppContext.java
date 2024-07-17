package org.memes.context;

import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


public class DBAppContext{

    private static DataSource DATASOURCE;

    public static DataSource getDataSource() {
        if (DATASOURCE == null){
            DATASOURCE = dataSource();
        }
        return DATASOURCE;
    }

    private DBAppContext(){

    }

    private static DataSource dataSource(){
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try{
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("bot");
            dataSource.setUser("root");
            dataSource.setPassword("Ridn4_nenka");
            try(Connection connection = dataSource.getConnection()){
                System.out.println("Successfully connection!");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return dataSource;
    }
}
