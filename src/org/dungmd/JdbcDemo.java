package org.dungmd;

import org.dungmd.dao.JdbcDaoImpl;
import org.dungmd.model.Circle;

public class JdbcDemo {

    public static void main(String[] args) {
        try {
            Circle circle = (new JdbcDaoImpl()).getCircle(1);
            System.out.println(circle.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
