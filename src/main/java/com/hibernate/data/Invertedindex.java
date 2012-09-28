package com.hibernate.data;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "invertedindex")
public class Invertedindex {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "index_id", updatable = false, nullable = false)
	private Long index_id;
	private String words;
	private String category;
	@Column(columnDefinition="TEXT")
	private String invertedindex;
	
	
	
	@Version
	private int version;

	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	public String getWords() {
		return words;
	}


	public void setWords(String words) {
		this.words = words;
	}


	public String getinvertedIndex() {
		return invertedindex;
	}


	public void setinvertedIndex(String invertedindex) {
		this.invertedindex = invertedindex;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}


