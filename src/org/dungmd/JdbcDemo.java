package org.dungmd;

import org.dungmd.dao.JdbcDaoImpl;
import org.dungmd.dao.JdbcDaoImplementation;
import org.dungmd.model.Circle;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcDemo {

    public static void main(String[] args) {
        try {
            AbstractApplicationContext aContext = new ClassPathXmlApplicationContext("springcontext.xml");
            aContext.registerShutdownHook();

            JdbcDaoImpl jdbcDao = aContext.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
            Circle circle = jdbcDao.getCircleById(1);
            System.out.println(circle.toString());
            
            
            jdbcDao.deleteCircleById(3);
            System.out.println(jdbcDao.getCircleCount());
            jdbcDao.insertCircle(new Circle(3, "Third Name"));
            System.out.println(jdbcDao.getCircleCount());
            
            for (Circle c : jdbcDao.getAllCircles()) {
                System.out.println(c);
            }
            
            System.out.println("Using a new DAO");
            JdbcDaoImplementation myDao = aContext.getBean("jdbcDaoImplementation", JdbcDaoImplementation.class);
            System.out.println(myDao.getCircleCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
