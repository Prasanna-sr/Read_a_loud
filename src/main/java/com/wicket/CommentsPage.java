package com.wicket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.value.ValueMap;

import com.hibernate.data.Bookdetails;



public final class CommentsPage extends WebPage {
    /** A global list of all comments from all users across all sessions */
    private static final List<Bookdetails> commentList = Collections.synchronizedList(new ArrayList<Bookdetails>());
    String category = null;
    String username;
    String bookname;
    String author;
    String bookid;
    String selectedrating;
    validator api = new validator();
     List ratings = Arrays.asList(new String[] { new String("1"), new String("2"), new String("3"), new String("4"), new String("5") });
    
    /**
     * Constructor that is invoked when page is invoked without a session.
     * @throws IOException 
     */
    public CommentsPage(PageParameters params) throws IOException {
    	category = params.get("category").toString();
    	username= params.get("username").toString();
    	bookname= params.get("bookname").toString();
    	author= params.get("author").toString();
    	bookid= params.get("bookid").toString();
    	
    	 Link link8=new Link("logout"){
 			@Override
 			public void onClick()
 			{
 				setResponsePage(Hello.class);
 			}
 		};
 		add(link8);
         	 
 
    	
        // Add comment form
        add(new CommentForm("commentForm"));      
        add(new Label("title", bookname));
        add(new Label("author", author));
        String strContent = api.getBookcontent(bookid.toString());
      //  params.get("bookname");
        TextArea<String> content1 = new TextArea("content",	Model.of(strContent));
         add(content1);
        
        // Add commentListView of existing comments
        add(new PropertyListView<Bookdetails>("comments", commentList) {    
        	
        	@Override
            public void populateItem(final ListItem<Bookdetails> listItem) {
               
        		listItem.add(new Label("timestamp"));
                listItem.add(new MultiLineLabel("comments"));
                
            }
        }).setVersioned(false);
        
        System.out.println("ONE");
    }

    /**
     * A form that allows a user to add a comment.
     */
    public final class CommentForm extends Form<ValueMap> {
        public CommentForm(final String id) throws IOException {
            // Construct form with no validation listener
            super(id, new CompoundPropertyModel<ValueMap>(new ValueMap()));
           
            // this is just to make the unit test happy
            setMarkupId("commentForm");
            add(new DropDownChoice("integer", ratings));
            add(new TextArea<String>("comments").setType(String.class));
            
            
        }
        
        /**
         * Show the resulting valid edit
         */
        @Override
        public final void onSubmit() {
            ValueMap values = getModelObject();
            Bookdetails comment = new Bookdetails();
            
            comment.setTimestamp(new Date().toString());
            comment.setComments((String)values.get("comments"));
            selectedrating = (String)values.get("integer");
            System.out.println("RATING:"+selectedrating);
            if(selectedrating == null)
            	selectedrating = "1";
            api.insertBookdetails(username,bookid,(String)values.get("comments"),selectedrating,new Date().toString());
            commentList.add(0, comment);
            System.out.println("THREE");
            // Clear out the text component
            values.put("comments", "");
        }
    }

    /**
     * Clears the comments.
     */
    
    public static void clear() {
        commentList.clear();
    }
    
}