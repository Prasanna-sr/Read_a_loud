package com.hibernate.app;


//import gash.hibernate.data.Person;
//import gash.hibernate.util.GeneratePeople;
import com.hibernate.data.Invertedindex;
import com.hibernate.data.Person;
import com.hibernate.app.Books;
import com.hibernate.util.GeneratePeople;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.Comparator;

import com.hibernate.util.HibernateUtil;
import com.Extractdata.GenerateData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.hibernate.data.*;
import com.indexing.Document;
import com.indexing.KeyWord;
import com.indexing.inverted.Loader;
import com.indexing.inverted.Registry;
import com.indexing.stopwords.StopWords;
import com.indexing.stopwords.StopWordsFile;

public class StartUpLoader {
	static Registry data;
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Start up loader started .. ");

		//to load books
		bookGenerate();
		
		//to load contacts
		contactGenerate(10000);
		generateauthorpersons();
		
		//to load inverted indexes
		invertedindexGenerate();
		
		
		System.out.println("Start up loader completed !");
		
}
	
	public static void test()
	{
		Bookdetails bd = new Bookdetails();
		Person p = new Person();
		
		bd.setbookname("8888.txt");
		p.setNickName("");
		
		
	}
	
	public static String[] getBooksdetailsforauthor(String username)
	{
		Books b = new Books();
		Booksdetails bd = new Booksdetails();
		List<String> lstBook = b.getBooks(username);
		String[] strList = bd.getBookdetailsforauthor(lstBook);
		return strList;
	}
	
	
		public static void getBookdetails(String bookname)
		{
			Booksdetails bd = new Booksdetails();
			Bookdetails obj=bd.queryBookdetails(bookname);
			
		}
		public static String getBookcontent(String bookname) throws IOException
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
	
	
	// to get inverted index details and books details based on rank
			public static String[] getIndexdetails(String keyword,String category)
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

	        	
			

	
	public static void contactGenerate(int count) {

		System.out.println("Data inserting for persons ...");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			GeneratePeople gen = new GeneratePeople();
			Transaction tx = session.beginTransaction();
			for (int n = 0; n < count; n++) {
				Person p = gen.createUser();
				session.save(p);
				if (n % 100 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
			System.out.println("data inserted for persons successfully");	
		} finally {
			// session.close();
		}
	}

	public static void invertedindexGenerate() throws Exception
	{
		Session session=null;
		long st = System.nanoTime();
		int category=0;
		int count =0;
		Invertedindex index = new Invertedindex();
		Registry r = new Registry();
		String[] categories = {"Fiction","Adventure","Fantasy","Reality","Romance","Kidssection"};
		Properties conf = new Properties();
		conf.setProperty(Registry.sStopWords, "resources/stopwords-long.txt");
		conf.setProperty(Registry.sDataStorage, "TBD");
		conf.setProperty(Registry.sIndexStorage, "TBD");

		data = new Registry(conf);
		
		// load data
		File dir = new File("testdata/books");
		File[] location = dir.listFiles();
		int noFiles = location.length;
		
		
		int j=0;
		int countBatch = 0;
		while(noFiles>0)
		{
			//determines no of files for each batch
			int no = 250;
			
			File[] f = new File[no];
			int i=0;
			while(i<no)
			{
				if(noFiles==0)
					break;
				f[i]=location[j];
				j++;
				i++;
				noFiles--;
			}
			
		data.register(f);

		HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>(); 
		hm = r.getindex();
		
		System.out.println("Data inserting into book table ...");
		if(category<3)
		{
			session = HibernateUtil.getSessionFactory2().getCurrentSession();
		}
		else
		{
			session = HibernateUtil.getSessionFactory().getCurrentSession();
		}
		
		Transaction tx = session.beginTransaction();
		
		for (Map.Entry<String, ArrayList<String>> e : hm.entrySet()) {
			
			index.setWords(e.getKey());
			index.setinvertedIndex(e.getValue().toString());
			index.setCategory(categories[category]);
			session.save(index);
			session.flush();
			session.clear();
			countBatch++;
			count++;
		}
		tx.commit();
		System.out.println("batch count "+ countBatch);
		//reset counter
		countBatch=0;
		
		category++;
		System.out.println("data inserted for " + count +" records successfully");
		if(noFiles==0)
			break;			
		}
		System.out.println("All indexes persisted successfully in database ");
		long et = System.nanoTime();
		System.out.println("---> generated & persisted inverted index in " + (et - st) + " ns or in " +(et - st)/1000000000 + " seconds" );
	
	}

	public static void generateauthorpersons() throws IOException
	{
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try
		{
		HashSet <String>hs = new HashSet <String>();
		Person p = new Person();
		GenerateData gd = new GenerateData();
		Object[] obj= gd.generatewords();
		String[] strAuthor = (String[]) obj[0];
		System.out.println("Transaction began .. ");
		Transaction tx = session.beginTransaction();
		
		for(String st : strAuthor)
		{
			st=st.trim();
			hs.add(st);	
		}
		
		Iterator iterator;
		iterator = hs.iterator(); 
		  while (iterator.hasNext()){
			String st = iterator.next().toString();
			st = st.trim();
			if(st.length()>5)
			{
				while(st.contains("."))
				{
				int rIndex= st.indexOf('.');
				
				  String rString=st.substring(0,rIndex+1);
				  st = st.replace(rString,"");
				}
			st= st.trim();
			
			String[] strAuth= st.split(" ");
			if(strAuth.length>1)
			{
				
				if(strAuth[0].length()<3)
				{
					break;
				}
				
				if(strAuth[1].length()<3)
				{
					break;
				}
			
				p.setFirstName(strAuth[0]);
				p.setLastName(strAuth[1]);
				p.setNickName(strAuth[0]+"_"+strAuth[1]);
				p.setCreated(new Date());
				p.setRole("Author");
				
				session.save(p);
				session.flush();
				session.clear();

			}
			}
		}
		  
		  tx.commit();
		  System.out.println("Transaction successfully ..! ");
		}
		finally{
		
		}
		
		  
	}
		
	public static void bookGenerate() throws IOException {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			String[] strAuthor;
			String[] strTilte;
			String[] strReleasedate;
			String[] strBook;
			Book b = new Book();
			GenerateData gd = new GenerateData();
			Object[] obj= gd.generatewords();
			strAuthor = (String[]) obj[0];
			strTilte = (String[]) obj[1];
			strReleasedate= (String[]) obj[2];
			strBook= (String[]) obj[3];
			System.out.println("Transaction began .. ");
			Transaction tx = session.beginTransaction();
			gd.loadBook();
			int n=0;
			for(n=0; n<strAuthor.length;n++)
			{
				String st = strAuthor[n];	
				
					st= st.trim();
					if(st.length()>5)
					{
					while(st.contains("."))
					{
						int rIndex= st.indexOf('.');
						String rString=st.substring(0,rIndex+1);
						st = st.replace(rString,"");
					}
						st= st.trim();
						String[] strAuth= st.split(" ");
						if(strAuth.length>1)
						{
							
							if(strAuth[0].length()<3)
							{
								b.setTitle(strTilte[n]);
								b.setAuthorname(strAuthor[n]);
								b.setBookname(strBook[n]);
								b.setReleasedate(strReleasedate[n]);
								b.setNickname("fname"+"_"+"lname");
								session.save(b);
								session.flush();
								session.clear();
								continue;
							}
							
							if(strAuth[1].length()<3)
							{
								b.setTitle(strTilte[n]);
								b.setAuthorname(strAuthor[n]);
								b.setBookname(strBook[n]);
								b.setReleasedate(strReleasedate[n]);
								b.setNickname(strAuth[0]+"_"+"lname");
								session.save(b);
								session.flush();
								session.clear();
								continue;
							}
							b.setTitle(strTilte[n]);
							b.setAuthorname(strAuthor[n]);
							b.setBookname(strBook[n]);
							b.setReleasedate(strReleasedate[n]);
							b.setNickname(strAuth[0]+"_"+strAuth[1]);
							session.save(b);
							session.flush();
							session.clear();
		
							
						}
						else
						{
							b.setTitle(strTilte[n]);
							b.setAuthorname(strAuthor[n]);
							b.setBookname(strBook[n]);
							b.setReleasedate(strReleasedate[n]);
							b.setNickname("fname"+"_"+"lname");
							session.save(b);
							session.flush();
							session.clear();
							
						}
					}
	
				 
			}
			System.out.println("Transaction commited successfully for "+n+" rows");
			tx.commit();
		} finally {
			//session.close();
		}
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
