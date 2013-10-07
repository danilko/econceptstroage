package com.econcept.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Context;

import org.apache.commons.io.FileUtils;
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
