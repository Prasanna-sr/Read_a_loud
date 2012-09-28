package com.wicket;
 
import com.hibernate.data.*;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import java.util.*;
import com.hibernate.data.Person;
 
public class Mainpage extends WebPage {
	
	int i=1;
	public Mainpage(final PageParameters parameters) {
		
		String ex = null;
		validator api = new validator();
		final StringValue username = parameters.get("username");
		final StringValue category = parameters.get("category");
		
		
		if(category == null)
		{	System.out.println("Category is null");}
	    final StringValue keyword = parameters.get("keyword");
	    String[] list = null;
	    try{
	    	list = api.getIndexdetails(keyword.toString(),category.toString());
		}
	    catch(Exception e)
		{
			ex=e.toString();
			System.out.println("Wrong keyword: error");
		}
	    if(list == null || list.length == 0 || ex !=null)
		{
			parameters.add("errorpage", "true");
			setResponsePage(Search.class, parameters);
		}
		
		
			List bookcatalog = new ArrayList();
			Link link8=new Link("logout"){
			@Override
			public void onClick()
			{
				setResponsePage(Hello.class);
			}
		};
		add(link8);
		
		
		if(list.length>0 || list!=null)
		{
			for(int i=0; i <list.length; i++)
			{
				if(list[i]!=null)
				{
				String[] strRow = list[i].split(",");
				String bookname = strRow[2];
				String author = strRow[1];
				 String bookid=strRow[0];
				bookcatalog.add(new book(bookname,author,bookid));
				}
			}
		}
		
		ListView eachbook = new ListView("eachbook",bookcatalog) {
		
		protected void populateItem(ListItem item)
		{
			final book b = (book)item.getModelObject();
			item.setModel(new CompoundPropertyModel(b));
		   
		item.add(new Label("wid",Integer.toString(i++)));
			Link detailsLink = new Link("detailsLink") {
				public void onClick(){
					//ProductDetails details = new ProductDetails();
					PageParameters pageParameters = new PageParameters();
					pageParameters.add("username", username);
					pageParameters.add("category", category);
					pageParameters.add("bookname", b.getName());
					pageParameters.add("author", b.getAuthor());
					pageParameters.add("bookid", b.getBookid());
					setResponsePage(CommentsPage.class,pageParameters);
				}
			};
			detailsLink.add(new Label("name"));
			item.add(detailsLink);
			
		}
		
		};
 
		add(eachbook);
	//	form.add(username,password);
		}
	
}

