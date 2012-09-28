package com.Extractdata;

import java.io.*;
import com.hibernate.data.Book;;
public class GenerateData {

	public String[] strTitle = null;
	public String[] strAuthor = null;
	public String[] strReleaseDate = null;
	public String[] strBook = null;
	/**
	 * @param args
	 * @throws IOException 
	 */

	
	@SuppressWarnings("null")
	public Object[] generatewords() throws IOException
	{
		String path = "C:/Users/Admin/Downloads/226/books";
		File filelocation = new File(path);
		Object obj[] = new Object[4];
		File[] location = filelocation.listFiles();
		
		strTitle = new String[location.length];
		strAuthor = new String[location.length];
		strReleaseDate = new String[location.length];
		strBook = new String[location.length];
		
		int k=0;

		int z=0;
		
		
		for(int i=0;i<location.length;i++)
		{

			// to get book name from location
			String strLoc = location[i].toString(); 
			int iLoc = strLoc.lastIndexOf('\\');
			strBook[i]=strLoc.substring(iLoc+1);

			  FileInputStream fstream = new FileInputStream(location[i]);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
				  
				  if(strLine.contains("START OF THIS PROJECT GUTENBERG"))
				  {
					 
					  break;
					  
				  }
				  
				  if(strLine.contains("Title:"))
				  {
					  strTitle[i] = strLine.replace("Title:", "");
					  if(strLine.contains("(") && strLine.contains(")"))
					  {
					      int rIndex= strTitle[i].indexOf('(');
						  String rString=strTitle[i].substring(rIndex);
						  strTitle[i] = strTitle[i].replace(rString,"");
					  }
					  
				  }
				  if(strLine.contains("Release Date:"))
				  {
					 
					  strReleaseDate[i] = strLine.replace("Release Date:","");
				  if(strLine.contains("[") && strLine.contains("]"))
				  {
				      int rIndex= strReleaseDate[i].indexOf('[');
					  String rString=strReleaseDate[i].substring(rIndex);
					  strReleaseDate[i] = strReleaseDate[i].replace(rString,"");
				  }
				  }
				  

				  if(strLine.contains("Author:"))
				  {
					  strAuthor[i] = strLine.replace("Author:","");
					  if(strLine.contains("(") && strLine.contains(")"))
					  {
					      int rIndex= strAuthor[i].indexOf('(');
						  String rString=strAuthor[i].substring(rIndex);
						  strAuthor[i] = strAuthor[i].replace(rString,"");
					  }
					  if(strLine.contains("[") && strLine.contains("]"))
					  {
					      int rIndex= strAuthor[i].indexOf('[');
						  String rString=strAuthor[i].substring(rIndex);
						  strAuthor[i] = strAuthor[i].replace(rString,"");
					  }
				  }
			  }
			  in.close();
		}
		
		obj[0] = strAuthor;
		obj[1] = strTitle;
		obj[2] = strReleaseDate;
		obj[3]= strBook;
		return obj;
	
	}

	
	public void loadBook()
	{
		Book b = new Book();
		for(int n=0; n<strAuthor.length;n++)
		{
			b.setTitle(strTitle[n]);
			b.setAuthorname(strAuthor[n]);
		}
	}
}
