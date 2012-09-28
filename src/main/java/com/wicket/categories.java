package com.wicket;
 
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValueConversionException;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.junit.Before;

import com.hibernate.*;
 
public class categories extends WebPage {
 
	public categories(final PageParameters parameters) {
				
				
				Link link1=new Link("search1")
				{
					@Override
					public void onClick(){
						
						
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("username",parameters.get("username"));
						pageParameters.add("category", "Reality");
						setResponsePage(Search.class,pageParameters);
						
					}
				};
				add(link1);
				Link link2=new Link("search2")
				{
					@Override
					public void onClick(){
						
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("username",parameters.get("username"));
						pageParameters.add("category","Adventure");
						setResponsePage(Search.class,pageParameters);
						
					}
				};
				add(link2);
				
				Link link3=new Link("search3")
				{
					@Override
					public void onClick(){
						
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("username",parameters.get("username"));
						pageParameters.add("category","Fantasy");
						setResponsePage(Search.class,pageParameters);
						
					}
				};
				add(link3);
				Link link4=new Link("search4")
				{
					@Override
					public void onClick(){
						
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("username",parameters.get("username"));
						pageParameters.add("category","Romance");
						setResponsePage(Search.class,pageParameters);
						
					}
				};
				add(link4);
			
				Link link5=new Link("search5")
				{
					@Override
					public void onClick(){
						
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("username",parameters.get("username"));
						pageParameters.add("category","Fiction");
						setResponsePage(Search.class,pageParameters);
						
					}
				};
				add(link5);
				
				Link link6=new Link("search6")
				{
					@Override
					public void onClick(){
						
						PageParameters pageParameters = new PageParameters();
						pageParameters.add("username",parameters.get("username"));
						pageParameters.add("category","Kidssection");
						setResponsePage(Search.class,pageParameters);
						
					}
				};
				add(link6);
					
//				if(parameters.get("type").toString().equals("author")){
					Link link7=new Link("author"){
						
						@Override
						public void onClick(){
							
							PageParameters pageParameters = new PageParameters();
							pageParameters.add("username",parameters.get("username"));
							
							setResponsePage(Authorpage.class,pageParameters);
							}
							
					};
							
					add(link7);
//				}
				/*else{
					Link link9=new Link("author"){
				
					public boolean isVisible()
					{
						return false;
					}
					@Override
					public void onClick(){
						
					}
				};
				add(link9);*/
//					}
					Link link8=new Link("logout"){
						@Override
						public void onClick()
						{
							setResponsePage(Hello.class);
						}
					};
					add(link8);

 
			}
 
		
 
	
}

