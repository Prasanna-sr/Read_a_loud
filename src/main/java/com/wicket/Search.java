package com.wicket;
 
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import com.hibernate.data.Person;
 
public class Search extends WebPage {
 
	public Search(final PageParameters parameters) {
 
		add(new FeedbackPanel("feedback"));
 
		final TextField<String> keyword = new TextField<String>("keyword", Model.of(""));
		keyword.setRequired(true);
		
		String cat = parameters.get("category").toString();
		String un = parameters.get("username").toString();
		
		String err = parameters.get("errorpage").toString();
		System.out.println("ERR:"+err);
		if (err != null && err.equals("true"))
		{
			String errmsg = getString("keywordError", null, "Invalid keyword");
//			String errmsg="error";
			error(errmsg);
		}

		System.out.println("BBBBBBBB;"+cat+":"+un);
		 
		Link link8=new Link("logout"){
			@Override
			public void onClick()
			{
				setResponsePage(Hello.class);
			}
		};
		add(link8);
		
		Form<?> form = new Form<Void>("searchForm") {
 
			@Override
			protected void onSubmit() {
 
				final String keywordValue = keyword.getModelObject();
				
				PageParameters pageParameters = new PageParameters();
				System.out.println("KEYWORD:"+keywordValue.toString());
				pageParameters.add("keyword", keywordValue);
				pageParameters.add("username",parameters.get("username") );
				pageParameters.add("category", parameters.get("category"));
				
			/*	Person temp = new Person();
				validator tem = new validator();
				String cat = parameters.get("category").toString();
				String un = parameters.get("username").toString();*/

				String kw = keywordValue.toString();
				if (kw.contains("\\s") )
				{
					String errmsg = getString("keywordError", null, "Please enter valid keyword");
					error(errmsg);
				}

				
				setResponsePage(Mainpage.class, pageParameters);
 
			}
 
		};
 
		add(form);
		form.add(keyword);
		
 
	}
	
}

