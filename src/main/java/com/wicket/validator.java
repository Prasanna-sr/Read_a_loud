package com.wicket;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.wicket.validation.CompoundValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.hibernate.*;

import com.Extractdata.GenerateData;
import com.hibernate.app.Books;
import com.hibernate.app.Persons;

import com.hibernate.data.Book;
import com.hibernate.app.Booksdetails;
import com.hibernate.data.Bookdetails;
import com.hibernate.data.Person;
import com.hibernate.util.HibernateUtil;


	
	public class validator extends CompoundValidator<String> {
		 
		private static final long serialVersionUID = 1L;
		Bookdetails bd = new Bookdetails();
		Booksdetails book = new Booksdetails();
	 
		public validator() {
			
		}
		
		public  Bookdetails getBookdetails(String bookname)
		{
			Booksdetails bd = new Booksdetails();
			Bookdetails obj=bd.queryBookdetails(bookname);
		
			return obj;
		}
		
		public String[] getBooksdetailsforauthor(String username)
		{
			Books b = new Books();
			Booksdetails bd = new Booksdetails();
			List<String> lstBook = b.getBooks(username);
			String[] strList = bd.getBookdetailsforauthor(lstBook);
			return strList;
		}
		
		public boolean insertBookdetails(String nickname,String bookid,String comments,String ratings,String date)
		{
			
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				
				System.out.println("Transaction began .. ");
				Transaction tx = session.beginTransaction();
				bd.setbookname(bookid);	
				bd.setnickname(nickname);
				bd.setComments(comments);
				bd.setRatings(ratings);
				bd.setTimestamp(date);
				session.save(bd);	
				session.flush();
				session.clear();
				
				tx.commit();
				System.out.println("Transaction commited for book details successfully ! ");
			} finally {
				//session.close();
			}

			
			return true;
		}
		public  String getBookcontent(String bookname) throws IOException
		{
			String strContent=null;
			StringBuilder sb = new StringBuilder();
			BufferedReader rdr=null;
			rdr = new BufferedReader(new FileReader("testdata/books/"+bookname));
			
			String raw = rdr.readLine();
			while (raw != null) {
				sb = sb.append(raw);
				sb = sb.append("\n");
				raw = rdr.readLine();
			}
			strContent = sb.toString();
			
			return strContent;
		}
		
		
		public  String[] getIndexdetails(String keyword,String category)
		{
			String strList=null;
			HashMap<String,Double> map = new HashMap<String,Double>();
			Books b = new Books();
			List<String> rList = new ArrayList<String>();
			String[] sList;
        	String strInv= b.queryIndex(keyword, category);
        	strList=strInv.replace("[", "").replace("]", "");
			sList = strList.split(",");
			for(String s : sList)
			{
				String[] strValue = s.split(":");
		        
				map.put(strValue[0], Double.valueOf(strValue[1]));
				rList.add(strValue[0]);
			}

			 ValueComparator vc =  new ValueComparator(map);
			 
			TreeMap<String,Double> sortedMap = new TreeMap<String,Double>(vc);
			sortedMap.putAll(map);
			for (String key : sortedMap.keySet()) {
	            System.out.println("key/value: " + key + "/"+sortedMap.get(key));
	        }
			String[] rStr = new String[10];
			rStr=b.queryAuthor(rList);
			int i=0;
			for (String key : sortedMap.keySet()) {
				if(i==10)
					break;
	            System.out.println("key/value: " + key + "/"+sortedMap.get(key));
	            rStr[i] = rStr[i] +","+ sortedMap.get(key);
	            i++;
	            
	        }
				return rStr;
			}

        	

		
		// to validate login
		public boolean validateUser(String username, String password)
		{
			Persons p = new Persons();
			Boolean val = p.queryPerson(username, password);
			return val;
		}
		
		
		public String getusertype(String username)
		{
			
				Persons p = new Persons();
				String type = p.getrole(username);
				return type;
			
		}
		
				
		
		public Person test(String username, String password) {
            Person r = null;
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            
            System.out.println("USERNAME:"+username);
            System.out.println("PASSWORD:"+password);

            try {
                    session.beginTransaction();

                    Query query = session.createQuery("from Person as p where p.firstName = :uname and p.lastName = :pass");
                    query.setParameter("uname", username);
                    query.setParameter("pass", password);
                    
                    List list = query.list();
                    if (!list.isEmpty()) {
                            r = (Person) list.get(0);
                    }
                    session.getTransaction().commit();
                    
            } catch (RuntimeException e) {
                    session.getTransaction().rollback();
                    return null;
                   // throw e;
            }
            return r;
		}
            
            public List getbooks(String username, String category, String keyword){
            	
            	List returnList = new ArrayList();
            	
            	returnList.add("1Welcome to book, John");
            	returnList.add("2Food for thought, Ashwin");
            	returnList.add("3Here we go, Prasanna");
            	
            	returnList.add("4Welcome to book, John");
            	returnList.add("5Food for thought, Ashwin");
            	returnList.add("6Here we go, Prasanna");
            	returnList.add("7Welcome to book, John");
            	returnList.add("8Food for thought, Ashwin");
            	returnList.add("9Here we go, Prasanna");
            	returnList.add("10Welcome to book, John");
            	returnList.add("11Food for thought, Ashwin");
            	returnList.add("12Here we go, Prasanna");
            	
            	return returnList;
            }
	}
	
	class ValueComparator implements Comparator {

		  Map base;
		  public ValueComparator(Map base) {
		      this.base = base;
		  }

		  public int compare(Object a, Object b) {

		    if((Double)base.get(a) < (Double)base.get(b)) {
		      return 1;
		    } else if((Double)base.get(a) == (Double)base.get(b)) {
		      return 0;
		    } else {
		      return -1;
		    }
		  }
		}
            	
            	
    
	


