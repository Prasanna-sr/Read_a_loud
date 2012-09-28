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
 
public class Bookpage extends WebPage {
 
	public Bookpage(final PageParameters parameters) {
	
		
		validator api = new validator();
		final StringValue username = parameters.get("username");
		final StringValue category = parameters.get("category");
		if(category == null)
			System.out.println("Category is null");
	    final StringValue keywordValue = parameters.get("keyword");
		
		List queryforbooks = api.getbooks(username.toString(), "fiction", "keyword");
		List bookcatalog = new ArrayList();
		
		if(queryforbooks != null)
		{
			for(int i=0; i < 10/*queryforbooks.size()*/; i++)
			{
				String bookname = (String)queryforbooks.get(i);
				String author = "Charlie";
				bookcatalog.add(new book(bookname,author,null));
			}
		}
		
		@SuppressWarnings("unchecked")
		ListView eachbook = new ListView("eachbook",bookcatalog) {
		
		protected void populateItem(ListItem item)
		{
			final book b = (book)item.getModelObject();
			item.setModel(new CompoundPropertyModel(b));
		    int i =0;
			//item.add(new Label("id",Integer.toString(i++)));
			Link detailsLink = new Link("detailsLink") {
				public void onClick(){
					//ProductDetails details = new ProductDetails();
					PageParameters pageParameters = new PageParameters();
					pageParameters.add("username", username);
					pageParameters.add("category", category);
					pageParameters.add("bookname", b.getName());
					pageParameters.add("author", b.getAuthor());
					setResponsePage(Bookpage.class,pageParameters);
				}
			};
			detailsLink.add(new Label("name"));
			item.add(detailsLink);
			}
		};
 
		add(eachbook);

 
	}
}

