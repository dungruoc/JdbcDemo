package org.dungmd.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dungmd.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * Here, I make a connection to MariaDB server, installed on localhost
 * via Java driver provided on https://mariadb.com/kb/en/library/about-mariadb-connector-j/
 * Use DBeaver as client for MariaDB server to create a circle table in test
 * 
 * 1) create a new database test
 * 2) use test;
 * CREATE TABLE `circle` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `name` varchar(100) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
 * 
 * 3) insert a row for testing:
 * 
 *    insert into test.circle values (1, "First name");
 * 
 */

@Component
public class JdbcDaoImpl {
    
    private Connection connection;
    
    @Autowired
    private DataSource dataSource;
    
//    public JdbcDaoImpl() throws Exception {
//        connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test?user=root&password=root");
//        System.out.println("JdbcDaoImpl connection established");
//    }

    public Circle getCircle(int id) {
        Circle circle = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement sqlStatement = connection.prepareStatement("SELECT * from circle where id = ?");
            sqlStatement.setInt(1, id);
            ResultSet rs = sqlStatement.executeQuery();
            if (rs.next()) {
                circle = new Circle(id, rs.getString("name"));
            }
            rs.close();
            sqlStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return circle;
    }
    
    public void myCleanup() throws Exception {
        System.out.println("Spring terminating");
        connection.close();
        System.out.println("JdbcDaoImpl connection close");
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
