package com.hibernate.app;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hibernate.data.Invertedindex;
import com.hibernate.util.HibernateUtil;

public class Books {
	
	
	public String queryIndex(String words,String category)
	{
		Session session=null;
		if((category.equals("Adventure")) || (category.equals("Fiction")) ||(category.equals("Fantasy")))
		{
			session = HibernateUtil.getSessionFactory2().getCurrentSession();
		}
		else if((category.equals("Reality")) || (category.equals("Romance")) ||(category.equals("Kidssection")))
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		}
	
	try {
		session.beginTransaction();
		Query query = session.createQuery("select I.invertedindex from Invertedindex I where I.words = :words and I.category = :category");
		query.setString("words", words);
		query.setString("category", category);
		
		Invertedindex invIdx;
		String invStr=null;
        List lst = query.list();
		Iterator it = lst.iterator();
        
        while(it.hasNext())
        {
        	invStr =  (String) it.next();  
        }
		session.getTransaction().commit();
		
		
		
		System.out.println("Index data retrieved ! ");
		
		return invStr;

		}
	catch (RuntimeException e) {
		session.getTransaction().rollback();
		throw e;
	}
		
}
	
	public String[] queryAuthor(List rlist)
	{
		Object[] obj=null;
		String[] strlist = new String[10];
		String strBook=null;
		int i=0;
		Iterator it1 = rlist.iterator();
		Session session1 = HibernateUtil.getSessionFactory().getCurrentSession();
		session1.beginTransaction();
		
		
		while(it1.hasNext())
		{
			//taking first 10 rows
			if(i==10)
				break;
			strBook= (String) it1.next();
			strBook=strBook.trim();
			Query query = session1.createQuery("select b.bookname,b.authorname,b.title,b.releasedate from Book b where b.bookname = :bookname");
			query.setString("bookname", strBook);
			List lstBook = query.list();
			Iterator itBook = lstBook.iterator();
			
			while(itBook.hasNext())
			{
				obj = (Object[]) itBook.next();
				strlist[i]= obj[0] + "," +obj[1]+ "," +obj[2]+ "," + obj[3];
			}
			i++;
		}

		session1.getTransaction().commit();

		return strlist;
		
	}
	
	
	public List<String> getBooks(String username)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("select b.bookname from Book b where b.nickname = :nickname");
			query.setString("nickname", username);

			List<String> lst = query.list();
			
			session.getTransaction().commit();
			
			return lst;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void findBookByName(String bookname) {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("select b.authorname,b.title,b.releasedate from Book b where b.bookname in");
			query.setString("bookname", bookname);
			Object[] obj = null;
			for(Iterator it=query.iterate();it.hasNext();)
			 {
				obj = (Object[]) it.next();
				 System.out.println("author name: " + obj[0]);
				 System.out.println("title: " + obj[1]);
				 System.out.println("release date: " + obj[2]);
				 }
			session.getTransaction().commit();
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
		
		}



}