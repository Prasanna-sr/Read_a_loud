package com.hibernate.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * In this hibernate example we have the person owning the contact relationship.
 * This is a traditional OO type of organization.
 * 
 * 
 * 
 */
@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "person_id", updatable = false, nullable = false)
	private Long id;
	private String firstName;
	private String lastName;
	private String nickName;
	private Date created;
	private String role;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id")
	private Collection<Contact> contacts;

	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "person_name")
	private Collection<Contact> Bookdetails;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Collection<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Collection<Contact> contacts) {
		this.contacts = contacts;
	}

	public void removeContact(Contact c) {
		if (contacts == null)
			return;

		contacts.remove(c);
	}

	public void addContact(Contact c) {
		if (contacts == null)
			contacts = new ArrayList<Contact>();

		contacts.add(c);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(id=");
		sb.append(id);
		sb.append(") ");
		sb.append(firstName);
		sb.append(" ");
		sb.append(lastName);
		sb.append(", ");
		sb.append(nickName);
		sb.append(", ");
		sb.append(role);
		return sb.toString();
	}

	public Collection<Contact> getBookdetails() {
		return Bookdetails;
	}

	public void setBookdetails(Collection<Contact> bookdetails) {
		Bookdetails = bookdetails;
	}
}
