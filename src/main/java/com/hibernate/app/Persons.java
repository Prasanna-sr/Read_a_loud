package com.hibernate.app;

import com.hibernate.data.Book;
import com.hibernate.data.Person;
import com.hibernate.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Query;


public class Persons {

	public String getrole(String nickname)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			String role = null;
		
			session.beginTransaction();
			Query query = session.createQuery("select p.role from Person p where p.nickName = :nickname");
			query.setString("nickname", nickname);

            List lst = query.list();
    		Iterator it = lst.iterator();
            
            while(it.hasNext())
            {
            	role = (String) it.next();
				 
			 }
			
			session.getTransaction().commit();
			
			return role;
			
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
		
	}
	
	public Boolean queryPerson(String username,String password)
	{
		System.out.println("user  : "+username.length());
		System.out.println("pwd : "+password.length());
		Object[] obj = null;
		 Session session = HibernateUtil.getSessionFactory().getCurrentSession();

         try {
        	 Boolean value=false;
                 session.beginTransaction();

                 Query query = session.createQuery("from Person as p where p.nickName = :username and p.lastName = :password");
                 query.setParameter("username", username);
                 query.setParameter("password", password);
                 
                 List lst = query.list();
         		Iterator it = lst.iterator();
                 
                 while(it.hasNext())
                 {
                	  it.next();
                	 value=true;
                 }
                System.out.println("asdfsadasdsad : "+value);
                 
                 session.getTransaction().commit();
                 
                 return value;
                 
         } catch (RuntimeException e) {
                 session.getTransaction().rollback();
                 return null;

         }

	}

	private List getResultList() {
		// TODO Auto-generated method stub
		return null;
	}

}
