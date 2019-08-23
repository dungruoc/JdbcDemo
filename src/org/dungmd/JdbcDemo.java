package org.dungmd;

import org.dungmd.dao.JdbcDaoImpl;
import org.dungmd.model.Circle;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcDemo {

    public static void main(String[] args) {
        try {
            AbstractApplicationContext aContext = new ClassPathXmlApplicationContext("springcontext.xml");
            aContext.registerShutdownHook();

            JdbcDaoImpl jdbcDao = aContext.getBean("jdbcDaoImpl", JdbcDaoImpl.class);
            Circle circle = jdbcDao.getCircle(1);
            System.out.println(circle.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
