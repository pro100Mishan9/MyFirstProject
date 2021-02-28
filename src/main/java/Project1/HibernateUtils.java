package Project1;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

public class HibernateUtils {

    private static final SessionFactory sessionFactory = createSessionFactory();

    private static SessionFactory createSessionFactory() {
        return new Configuration().configure(new File
                ("C:\\files\\SDA\\MyFirstProject\\src\\main\\resources\\hibernate.cfg.xml")).buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutDownSession() {
        sessionFactory.close();
    }
}