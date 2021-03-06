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
	 * @return the date the file was uploaded
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date - the date the file was uploaded
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the name of the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file - the name of the file
	 */
	public void setFile(String file) {
		this.file = file;
	}

	
	
	@Override
	public String toString() {
		return "File [date=" + date + ", file=" + file + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
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
		File other = (File) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		return true;
	}
	
	
	
	
	
	
}
