package org.dungmd.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class JdbcDaoImplementation extends JdbcDaoSupport {
    
    public int getCircleCount() {
        String sql = "SELECT COUNT(*) FROM circle";
        return this.getJdbcTemplate().queryForObject(sql, int.class);        
    }
}
