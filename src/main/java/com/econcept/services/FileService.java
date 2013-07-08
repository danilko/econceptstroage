package com.econcept.services;

import java.io.File;

import org.springframework.stereotype.Service;

@Service
public class FileService {
	public final static String ACCOUNT_ID="account_id";
	public final static String DATA_DIR="DATA_DIR";

	/**
	 * Retrieve the account id with file list
	 * @param pAccountID The account ID to retrieve file list for
	 * @return The result in JSON format
	 */
	public static String getFileList(String pAccountID)
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
					+ "/" + pAccountID);
			
			// Retrieve all files from file lists
			File[] lFileList = lFolder.listFiles();

			// Start build JSON array for file list
			StringBuilder lBuilder = new StringBuilder();
			lBuilder.append("[");

			// Only go through file list if file list itself is not null
			if (lFileList != null)
			{
				for (int lIndex = 0; lIndex < lFileList.length; lIndex++)
				{
					// Add double quotes to surround each file name
					lBuilder.append("\"");
					lBuilder.append(lFileList[lIndex].getName());
					lBuilder.append("\"");

					// If next element is available, add comma
					if (lIndex + 1 < lFileList.length)
					{
						lBuilder.append(",");
					} // if
				} // for
			} // if
			lBuilder.append("]");
			
			return "{\"status\": \"success\",\"statusmessage\": "
					+ lBuilder.toString() + "}";
		} // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			return "{\"status\": \"error\",\"statusmessage\": \""
					+ pException.toString().replace("\\", "/")
							.replace("\"", "\\\"") + "\"}";
		} // catch
	} // String getFileList
}
