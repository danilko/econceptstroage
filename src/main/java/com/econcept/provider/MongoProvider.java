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

package com.econcept.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.econcept.entities.FileEntity;
import com.mongodb.BasicDBObject;

@Service
public class MongoProvider 
{
	@Context
	MongoOperations mMongoOperations;
	
	/**
	 * Retrieve the account id with file list
	 * @param pUserID The user ID to retrieve file list for
	 * @return List <FileEntity> The files under current user
	 */
	public List <FileEntity> getFileList(String pUserID)
	{
		try
		{
			Query lSearchQuery = new Query(Criteria.where("userid").is(pUserID));
			
			// Retrieve all files from file lists
			 List <BasicDBObject> lFileList = mMongoOperations.find(lSearchQuery, BasicDBObject.class);

			ArrayList <FileEntity> lFileEntities = new ArrayList<FileEntity>();
			if(lFileList != null)
			{
				// Only go through file list if file list itself is not null
				for(BasicDBObject lObject : lFileList)
				{
					FileEntity lFileEntity = new FileEntity();
					lFileEntity.setFileName(lObject.getString("name"));
					lFileEntity.setLastModified(lObject.getString("lastModified"));
					lFileEntity.setFileSize(lObject.getString("size"));
				}  // for
			}  //if
			
			return lFileEntities;
		} // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			throw pException;
		} // catch
	} // String getFileList
	
	public File getFile(String pUserID, String pFileName)
	{
		
		return null;
	}  // File getFile
	
	public File saveFileWithURI(String pUserID,  FileEntity pFileEntity) throws Exception
	{
		return null;
	}  //  File saveFileWithURI
	
	public File saveFileWithFile(String pUserID,  FileEntity pFileEntity) throws Exception
	{
		return null;
	}  //  File saveFileWithFile
	
	/**
	 * 
	 * <p>Delete particular file owned by the user</p>
	 * 
     * @param String pFileName The file name to be deleted
     * @param String pAccountID The account ID that owns the account
     * 
	 */
	public void deleteFile(String pUserID, String pFileName) throws Exception
	{
		return;
	}  // class FileService
}
