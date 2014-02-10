/**
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Kai-Ting (Danil) Ko
 * 
 * Permission is hereby granted, free of charge, 
 * to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including 
 * without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom 
 * the Software is furnished to do so, subject to the 
 * following conditions:
 * 
 * The above copyright notice and this permission notice 
 * shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY 
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS 
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

package com.econcept.entity;

import java.io.File;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserFile
{
	@JsonProperty("fileName")
	String mFileName;
	
	@JsonProperty("fileSize")
	String mFileSize;
	
	@JsonProperty("lastModified")
	String mLastModified;
	
	@JsonProperty("uri")
	String mURI;

	public UserFile()
	{
	}  // FileEntity
	
	public UserFile(File pFile)
	{
		mFileName = pFile.getName();
		mLastModified = Long.toString(pFile.lastModified());
		mFileSize = Long.toString(pFile.length());
	}  // FileEntity
	
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
}  // class UserFile