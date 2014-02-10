package com.econcept.dao.impl.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.econcept.dao.UserFileDAO;
import com.econcept.entity.UserFile;

public class UserFileDAOBaseImpl implements UserFileDAO
{
	public final static String DATA_DIR = "DATA_DIR";

	private final static Logger LOGGER = LoggerFactory.getLogger(UserFileDAOBaseImpl.class);
	/**
	 * Retrieve the account id with file list
	 * 
	 * @param pUserID
	 *            The user ID to retrieve file list for
	 * @return List <UserFile> The files under current user
	 */
	public List<UserFile> getFileList(String pUserID) {
		try {
			// Convert data dir to UNIX like path to eliminate possible errors
			File lFolder = new File(getValue(DATA_DIR) + "/"
					+ pUserID);
			File[] lFileList = null;

			// Retrieve all files from file lists
			lFileList = lFolder.listFiles();
			ArrayList<UserFile> lFileEntities = new ArrayList<UserFile>();
			if (lFileList != null) {

				// Only go through file list if file list itself is not null
				for (File lFile : lFileList) {
					UserFile lUserFile = new UserFile(lFile);
					lFileEntities.add(lUserFile);
				} // for
			} // if
			return lFileEntities;
		} // try
		catch (Exception pException) {
			LOGGER.debug(pException.toString());
			throw pException;
		} // catch
	} // String getFileList

	public File getFile(String pUserID, String pFileName) {
		// Retrieve the file through user information, convert to UNIX format to
		// eliminate
		// possible errors
		return new File(getValue(DATA_DIR)
				+ "/" + pUserID + "/" + "/" + pFileName);
	} // File getFile

	public UserFile saveFileWithURI(String pUserID, UserFile pUserFile) throws Exception {
		String [] lFilePathTokens = pUserFile.getURI().split("/");
		String lFilePath = getValue(DATA_DIR)
				+ "/" + pUserID + "/" + "/" + lFilePathTokens[lFilePathTokens.length - 1];

		// Set the output file and conver to UNIX styles
		File lFile = new File(lFilePath);

		// Set the URL to download files from
		URL lURL = new URL(pUserFile.getURI());
		FileUtils.copyURLToFile(lURL, lFile);

		return new UserFile(new File(lFilePath));
	} // File saveFileWithURI

	public UserFile saveFileWithInputStream(String pUserID, String pFileName, InputStream pInputStream) throws Exception 
			{
		String lFilePath = null;
		
		OutputStream lOutputStream = null;
		
		try
		{
			// Retrieve output stream through multipart data
			lOutputStream = new FileOutputStream(lFilePath);
			
			lFilePath = getValue(DATA_DIR)
					+ "/" + pUserID + "/" + "/" + pFileName;
			
			System.out.println(pFileName);
			

			int lRead = 0;
			// Set the upload threshold to 1 KB for every read
			byte[] lBytes = new byte[1024];
			
			// Write while read from form data
			while ((lRead = pInputStream.read(lBytes)) != -1)
			{
				lOutputStream.write(lBytes, 0, lRead);
			} // while

			lOutputStream.flush();
			
			return new UserFile(new File(lFilePath));
		} // try
		catch(Exception pException)
		{
			LOGGER.debug(pException.toString());
			throw new Exception("Error in file operation");
		}  // catch
		finally
		{
			if(lOutputStream != null)
			{
				try
				{
					lOutputStream.close();
				}  // try
				catch(Exception pException)
				{
					LOGGER.debug(pException.toString());
					throw new Exception("Error in file operation");
				}  // catch
			}  // if
			
			if(pInputStream != null)
			{
				try
				{
					pInputStream.close();
				}  // try
				catch(Exception pException)
				{
					LOGGER.debug(pException.toString());
					throw new Exception("Error in file operation");
				}  // catch
			}  // if
		}  // finally
	} // UserFile saveFileWithInputStream

	/**
	 * 
	 * <p>
	 * Delete particular file owned by the user
	 * </p>
	 * 
	 * @param String
	 *            pFileName The file name to be deleted
	 * @param String
	 *            pAccountID The account ID that owns the account
	 * 
	 */
	public void deleteFile(String pUserID, String pFileName) throws Exception {
		try {
			File lFile = new File(getValue(DATA_DIR)
					+ "/" + pUserID + "/" + "/" + pFileName);

			// Remove the file
			lFile.delete();
		} // catch
		catch (Exception pException) {
			LOGGER.debug(pException.toString());
			throw pException;
		} // catch
	}  // void deleteFile
	
	public String getValue(String pValueKey)
	{
		String lValue = System.getenv(pValueKey);
		
		return lValue.replace("\\", "/");
	}  // String getValue
} // class FileProvider
