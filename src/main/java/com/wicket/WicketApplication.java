package com.wicket;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.*;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.mycompany.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{  
	
	@Override
	public Class<? extends Page> getHomePage() {
		return Hello.class; //return default page
	}

}
