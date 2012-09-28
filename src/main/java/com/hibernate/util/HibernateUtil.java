package com.hibernate.util;


import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	
	
	private static final SessionFactory sessionFactory[];

	static {
		sessionFactory = new SessionFactory[2];
		sessionFactory[0] = buildSessionFactory();
		sessionFactory[1] = buildSessionFactory2();
	}

	private static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			return new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	private static SessionFactory buildSessionFactory2() {
		try {
			// TODO need to pass the location where the property file is
			// located!
			String path =  new java.io.File(".").getCanonicalPath();
			File cfgF = new File(path + "/src/main/java/hibernate2.cfg.xml");
			Configuration cfg = new Configuration();
			cfg.configure(cfgF);
			return cfg.buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory[0];
	}
	public static SessionFactory getSessionFactory2() {
		return sessionFactory[1];
	}

}