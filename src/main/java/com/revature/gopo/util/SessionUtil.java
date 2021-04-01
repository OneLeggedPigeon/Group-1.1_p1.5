package com.revature.gopo.util;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

/**
 * A singleton-pattern util class for creating Hibernate's SessionFactory.
 */
public class SessionUtil {
    private static SessionFactory sessionFactory;

    /**
     * Gets the SessionFactory instance, building it if necessary.
     *
     * @return
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    /**
     * Configures and builds a SessionFactory using the default properties
     * (found in hibernate.cfg.xml)
     *
     * @return
     */
    private static SessionFactory buildSessionFactory() {
        // this looks like it SHOULD work without bothering with StandardServiceRegistry stuff...
        Configuration config = new Configuration();
        config.configure();
        sessionFactory = config.buildSessionFactory();
        return sessionFactory;
    }
}
