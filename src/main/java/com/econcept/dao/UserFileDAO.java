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

package com.econcept.dao;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.econcept.entity.UserFile;

public interface UserFileDAO 
{
	/**
	 * Retrieve the account id with file list
	 * 
	 * @param pUserID
	 *            The user ID to retrieve file list for
	 * @return List <UserFile> The files under current user
	 */
	public List<UserFile> getFileList(String pUserID);

	public File getFile(String pUserID, String pFileName);

	public UserFile saveFileWithURI(String pUserID, UserFile pUserFile) throws Exception;

	public UserFile saveFileWithInputStream(String pUserID, String pFileName, InputStream pInputStream) throws Exception;
	
	/**
	 * 
	 * <p>
	 * Delete particular file owned by the user
	 * </p>
	 * 
	 * @param String
	 *            pFileName The file name to be deleted
	 * @param String
	 *            pUserID The user id owns the file
	 * 
	 */
	public void deleteFile(String pUserID, String pFileName) throws Exception ;
	
	/**
	 * 
	 * <p>
	 * Delete particular file owned by the user
	 * </p>
	 * 
	 * @param String
	 *            pFileName The file name to be deleted
	 * @param String
	 *            pUserID The user id owns the file
	 * 
	 */
	public String getValue(String pValueKey);
}  //  interface UserFileDAO 
