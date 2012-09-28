package com.wicket;

import java.io.Serializable;

import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

public class book implements Serializable{
	private String name;
	private String author;
	private String bookid;
	
	public book(String name, String author,String bookid){
		
		this.name = name;
		this.author = author;
		this.setBookid(bookid);
		
	}
	/*public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}*/
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getBookid() {
		return bookid;
	}
	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	

}
