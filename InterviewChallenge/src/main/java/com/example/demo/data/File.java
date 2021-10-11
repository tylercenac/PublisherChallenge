package com.example.demo.data;

import java.util.Date;

public class File {

	private Date date;
	private String file;
	
	public File() {
		super();
	}
	
	/**
	 * @param date
	 * @param file
	 */
	public File(Date date, String file) {
		this();
		this.setDate(date);
		this.setFile(file);
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "File [date=" + date + ", file=" + file + "]";
	}
	
	
	
	
}