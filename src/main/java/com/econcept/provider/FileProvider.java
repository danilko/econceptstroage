package com.econcept.provider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.econcept.entities.FileEntity;

@Service
public class FileProvider {
	public final static String ACCOUNT_ID="account_id";
	public final static String DATA_DIR="DATA_DIR";
	
	/**
	 * Retrieve the account id with file list
	 * @param pUserID The user ID to retrieve file list for
	 * @return List <FileEntity> The files under current user
	 */
	public List <FileEntity> getFileList(String pUserID)
	{
		try
		{
			String lFileDataDir = System.getenv(DATA_DIR).toString();

			// Used to prevent openshift gear restarting from idle and not set environment variable properly
			if(lFileDataDir == null)
			{
				lFileDataDir = System.getenv("OPENSHIFT_DATA_DIR").toString();
			}  // if
			
			// Convert data dir to UNIX like path to eliminate possible errors
			File lFolder = new File((lFileDataDir)
					.replace("\\", "/")
					+ "/" + pUserID);
			
			// Retrieve all files from file lists
			File[] lFileList = lFolder.listFiles();

			ArrayList <FileEntity> lFileEntities = new ArrayList<FileEntity>();
			if(lFileList != null)
			{
			// Only go through file list if file list itself is not null
			for(File lFile : lFileList)
			{
				FileEntity lFileEntity = new FileEntity();
				lFileEntity.setFileName(lFile.getName());
				lFileEntity.setLastModified(lFile.getName());
				lFileEntity.setFileName(lFile.getName());
				lFileEntity.setFileName(lFile.getName());
			}  // for
			}
			return lFileEntities;
		} // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			throw pException;
		} // catch
	} // String getFileList
	
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
		try
		{
			File lFile = new File(System.getenv(DATA_DIR).toString()
					.replace("\\", "/")
					+ "/" + pUserID + "/" + "/" + pFileName);

			// Remove the file
			lFile.delete();
		}  // catch 
		catch (Exception pException)
		{
			pException.printStackTrace();
			throw pException;
		} // catch
	}  // class FileService
}
