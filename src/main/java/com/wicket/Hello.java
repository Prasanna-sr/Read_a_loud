package com.wicket;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import com.hibernate.data.Person;

public class Hello extends WebPage {

	public Hello(final PageParameters parameters) {

		add(new FeedbackPanel("feedback"));

		final TextField<String> username = new TextField<String>("username",
				Model.of(""));
		username.setRequired(true);

		final PasswordTextField password = new PasswordTextField("password",
				Model.of(""));
		password.setLabel(Model.of("Password")); 


		Form<?> form = new Form<Void>("userForm") {

			@Override
			protected void onSubmit() {

				final String usernameValue = username.getModelObject();
				final String passwordValue = password.getModelObject();

				PageParameters pageParameters = new PageParameters();
				pageParameters.add("username", usernameValue);

				Person temp = new Person();

				boolean result = false;
				validator tem = new validator();
				try{
					result = tem.validateUser(usernameValue,passwordValue);
				}
				catch(Exception e)
				{
					System.out.println("Failed to authenticate");
					setResponsePage(Hello.class, pageParameters);

				}
				System.out.println("RESULT: "+result);
				if (!result)
				{
					String errmsg = getString("loginError", null, "Unable to sign you in");

					error(errmsg);
				}
				else
					setResponsePage(categories.class, pageParameters);
			}

		};

		add(form);
		form.add(username,password);


	}
}

