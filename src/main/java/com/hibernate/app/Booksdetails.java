package com.hibernate.app;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.hibernate.data.Bookdetails;
import com.hibernate.util.HibernateUtil;

public class Booksdetails {
	
	
	
	public Bookdetails queryBookdetails(String bookname)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("select b.bookname,b.comments,b.nickname,b.ratings,b.timestamp from Bookdetails b where b.bookname = :bookname");
			query.setString("bookname", bookname);
			
			Bookdetails obj=null;
			List lst = query.list();
			Iterator it = lst.iterator();
			while(it.hasNext())
			 {
				obj = (Bookdetails) it.next();
				 
			 }
			session.getTransaction().commit();
			
			return obj;
		} catch (RuntimeException e) {
			session.getTransaction().rollback();
			throw e;
		}
		
		
	}
	
	public String[] getBookdetailsforauthor(List rlist)
	{
		Object[] obj=null;
		String[] strlist = new String[10];
		String strBook=null;
		int i=0;
		Iterator it = rlist.iterator();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		
		while(it.hasNext())
		{
			//taking first 10 rows
			if(i==10)
				break;
			strBook= (String) it.next();
			strBook=strBook.trim();
			Query query = session.createQuery("select b.bookname,b.authorname,b.title,b.releasedate from Book b where b.bookname = :bookname");
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

		session.getTransaction().commit();

		return strlist;
		
	}

	


}
