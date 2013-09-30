package com.econcept.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileEntity 
{
	@JsonProperty("name")
	String mFileName;
	
	@JsonProperty("size")
	String mFileSize;
	
	@JsonProperty("lastModified")
	String mLastModified;
	
	@JsonProperty("uri")
	String mURI;

	public String getFileName() {
		return mFileName;
	}

	public void setFileName(String pFileName) {
		this.mFileName = pFileName;
	}

	public String getFileSize() {
		return mFileSize;
	}

	public void setFileSize(String pFileSize) {
		this.mFileSize = pFileSize;
	}

	public String getLastModified() {
		return mLastModified;
	}

	public void setLastModified(String pLastModified) {
		this.mLastModified = pLastModified;
	}

	public String getURI() {
		return mURI;
	}

	public void setURI(String pURI) {
		this.mURI = pURI;
	}
	
	
}
