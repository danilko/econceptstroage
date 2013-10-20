package com.econcept.provider;

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
import org.springframework.stereotype.Service;

import com.econcept.entities.FileEntity;

@Service
public class FileProvider {
	public final static String DATA_DIR = "DATA_DIR";

	private final static Logger LOGGER = LoggerFactory.getLogger(FileProvider.class);
	/**
	 * Retrieve the account id with file list
	 * 
	 * @param pUserID
	 *            The user ID to retrieve file list for
	 * @return List <FileEntity> The files under current user
	 */
	public List<FileEntity> getFileList(String pUserID) {
		try {
			// Convert data dir to UNIX like path to eliminate possible errors
			File lFolder = new File(getValue(DATA_DIR) + "/"
					+ pUserID);
			File[] lFileList = null;

			// Retrieve all files from file lists
			lFileList = lFolder.listFiles();
			ArrayList<FileEntity> lFileEntities = new ArrayList<FileEntity>();
			if (lFileList != null) {

				// Only go through file list if file list itself is not null
				for (File lFile : lFileList) {
					FileEntity lFileEntity = new FileEntity(lFile);
					lFileEntities.add(lFileEntity);
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

	public FileEntity saveFileWithURI(String pUserID, FileEntity pFileEntity)
			throws Exception {
		String [] lFilePathTokens = pFileEntity.getURI().split("/");
		String lFilePath = getValue(DATA_DIR)
				+ "/" + pUserID + "/" + "/" + lFilePathTokens[lFilePathTokens.length - 1];

		// Set the output file and conver to UNIX styles
		File lFile = new File(lFilePath);

		// Set the URL to download files from
		URL lURL = new URL(pFileEntity.getURI());
		FileUtils.copyURLToFile(lURL, lFile);

		return new FileEntity(new File(lFilePath));
	} // File saveFileWithURI

	public FileEntity saveFileWithInputStream(String pUserID, String pFileName, InputStream pInputStream)
			throws Exception 
			{
		String lFilePath = null;
		try
		{
			lFilePath = getValue(DATA_DIR)
					+ "/" + pUserID + "/" + "/" + pFileName;
			
			System.out.println(pFileName);
			
			// Retrieve output stream through multipart data
			OutputStream lOutputStream = new FileOutputStream(lFilePath);
			int lRead = 0;
			// Set the upload threshold to 1 KB for every read
			byte[] lBytes = new byte[1024];
			
			// Write while read from form data
			while ((lRead = pInputStream.read(lBytes)) != -1)
			{
				lOutputStream.write(lBytes, 0, lRead);
			} // while

			lOutputStream.flush();
			lOutputStream.close();
			
			pInputStream.close();
			
			return new FileEntity(new File(lFilePath));
		} // try
		catch(Exception pException)
		{
			LOGGER.debug(pException.toString());
			throw pException;
		}  // catch
	} // FileEntity saveFileWithInputStream

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
