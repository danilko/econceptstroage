package com.econcept.provider;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.econcept.entities.FileEntity;

@Service
public class FileProvider {
	public final static String DATA_DIR = "DATA_DIR";

	/**
	 * Retrieve the account id with file list
	 * 
	 * @param pUserID
	 *            The user ID to retrieve file list for
	 * @return List <FileEntity> The files under current user
	 */
	public List<FileEntity> getFileList(String pUserID) {
		try {
			String lFileDataDir = System.getenv(DATA_DIR).toString();

			// Used to prevent openshift gear restarting from idle and not set
			// environment variable properly
			if (lFileDataDir == null) {
				lFileDataDir = System.getenv("OPENSHIFT_DATA_DIR").toString();
			} // if

			// Convert data dir to UNIX like path to eliminate possible errors
			File lFolder = new File((lFileDataDir).replace("\\", "/") + "/"
					+ pUserID);
			File[] lFileList = null;

			// Retrieve all files from file lists
			lFileList = lFolder.listFiles();
			ArrayList<FileEntity> lFileEntities = new ArrayList<FileEntity>();
			if (lFileList != null) {

				// Only go through file list if file list itself is not null
				for (File lFile : lFileList) {
					FileEntity lFileEntity = new FileEntity();
					lFileEntity.setFileName(lFile.getName());
					lFileEntity.setLastModified(lFile.getName());
					lFileEntity.setFileName(lFile.getName());
					lFileEntity.setFileSize(Long.toString(lFile.length()));
				} // for
			} // if
			return lFileEntities;
		} // try
		catch (Exception pException) {
			pException.printStackTrace();
			throw pException;
		} // catch
	} // String getFileList

	public File getFile(String pUserID, String pFileName) {
		// Retrieve the file through user information, convert to UNIX format to
		// eliminate
		// possible errors
		return new File(System.getenv(DATA_DIR).toString().replace("\\", "/")
				+ "/" + pUserID + "/" + "/" + pFileName);
	} // File getFile

	public File saveFileWithURI(String pUserID, FileEntity pFileEntity)
			throws Exception {
		String lFilePath = System.getenv(DATA_DIR).toString()
				.replace("\\", "/")
				+ "/" + pUserID + "/" + "/" + pFileEntity.getFileName();

		// Set the output file and conver to UNIX styles
		File lFile = new File(lFilePath);

		// Set the URL to download files from
		URL lURL = new URL(pFileEntity.getURI());
		FileUtils.copyURLToFile(lURL, lFile);

		return new File(lFilePath);
	} // File saveFileWithURI

	public File saveFileWithFile(String pUserID, FileEntity pFileEntity)
			throws Exception {
		String lFilePath = System.getenv(DATA_DIR).toString()
				.replace("\\", "/")
				+ "/" + pUserID + "/" + "/" + pFileEntity.getFileName();

		// Set the output file and conver to UNIX styles
		File lFile = new File(lFilePath);

		// Set the URL to download files from
		URL lURL = new URL(pFileEntity.getURI());
		FileUtils.copyURLToFile(lURL, lFile);

		return new File(lFilePath);
	} // File saveFileWithFile

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
			File lFile = new File(System.getenv(DATA_DIR).toString()
					.replace("\\", "/")
					+ "/" + pUserID + "/" + "/" + pFileName);

			// Remove the file
			lFile.delete();
		} // catch
		catch (Exception pException) {
			pException.printStackTrace();
			throw pException;
		} // catch
	} // class FileService
}
