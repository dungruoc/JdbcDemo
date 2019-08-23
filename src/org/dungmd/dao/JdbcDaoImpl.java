package org.dungmd.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.dungmd.model.Circle;

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


public class JdbcDaoImpl {

    public Circle getCircle(int id) throws Exception {
        Circle circle = null;
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test?user=root&password=root");
        PreparedStatement sqlStatement = connection.prepareStatement("SELECT * from circle where id = ?");
        sqlStatement.setInt(1, id);
        ResultSet rs = sqlStatement.executeQuery();
        if (rs.next()) {
            circle = new Circle(id, rs.getString("name"));
        }
        rs.close();
        sqlStatement.close();
        connection.close();
        return circle;
    }
}
