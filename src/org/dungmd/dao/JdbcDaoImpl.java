package org.dungmd.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.dungmd.model.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
       
    @Autowired
    private DataSource dataSource;
    
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;   

    public NamedParameterJdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class CircleMapper implements RowMapper<Circle> {

        @Override
        public Circle mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Circle(rs.getInt("id"), rs.getString("name"));
        }
        
    }
    
    public Circle getCircleById(int id) {
        String sql = "SELECT * from circle where id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, namedParameters, new CircleMapper());
    }
    
    public List<Circle> getAllCircles() {
        String sql = "SELECT * from circle";
        return jdbcTemplate.query(sql, new CircleMapper());
    }
    
    public int getCircleCount() {
        String sql = "SELECT COUNT(*) FROM circle";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), int.class);
    }
    
    public void insertCircle(Circle circle) {
        String sql = "INSERT INTO circle (ID, NAME) VALUES (:id, :name)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", circle.getId()).addValue("name", circle.getName());
        jdbcTemplate.update(sql, namedParameters);
    }
    
    public void deleteCircleById(int id) {
        String sql = "DELETE FROM circle where id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(sql, namedParameters);
    }
    
    public void myCleanup() throws Exception {
        System.out.println("Spring terminating");
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
