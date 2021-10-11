package com.example.demo.data;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.simple.JSONArray;

@Entity
@Table(name="publishersdb")
public class PublisherEntity {

	@Id
	private String code;
	private String name;
	private String file;
	private boolean active;
	private JSONArray categories;
	

	public PublisherEntity() {
		super();
	}
	
	/**
	 * @param code
	 * @param name
	 * @param file
	 * @param active
	 * @param categories
	 */
	public PublisherEntity(String code, String name, String file, boolean active, JSONArray categories) {
		this();
		this.setCode(code);
		this.setName(name);
		this.setFile(file);
		this.setActive(active);
		this.setCategories(categories);
	}
	
	
	/**
	 * @return the code representing the publisher
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code - the code representing the publisher to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name of the publisher
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name - the name of the publisher to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name of the file
	 */
	public String getFile() {
		return file;
	}
	/**
	 * @param file - the name of the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}
	/**
	 * @return whether or not the publisher is active
	 */
	public boolean isActive() {
		return active;
	}
	/**
	 * @param active - whether or not the publisher is active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	/**
	 * @return categories for a publisher
	 */
	public JSONArray getCategories() {
		return categories;
	}
	/**
	 * @param categories for a publisher
	 */
	public void setCategories(JSONArray categories) {
		this.categories = categories;
	}
	
	@Override
	public String toString() {
		return "PublisherEntity [code=" + code + ", name=" + name + ", file=" + file + ", active=" + active
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + ((categories == null) ? 0 : categories.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PublisherEntity other = (PublisherEntity) obj;
		if (active != other.active)
			return false;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
