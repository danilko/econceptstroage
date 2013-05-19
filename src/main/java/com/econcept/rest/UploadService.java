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


package com.econcept.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * 
 * @author Kai - Ting (Danil) Ko
 * <h1>UploadService</h1>
 * <p>To provide rest end points for upload, delete, get file, get file lists according to user input</p>
 *
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UploadService
{

	/**
	 * <h1>getFileList</h1>
	 * <p>Get the all files that owned by the user according to the user information</p>
	 * 
	 * @param pMessage <p>The JSON message string that contains user information, 
	 *            and the file name for the file to retrieve</p>
	 *           <p>JSON parameters and their values</p>
	 *           <p>user type = string</p>
	 *           <p>The username for the user</p>
	 *           <p>parameter type = string</p>
	 *           <p>The file name for the file to be deleted</p>
	 * @return String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The username for the user</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/getfilelist")
	public String getFileList(String pMessage)
	{
		try
		{
			JSONObject lObject = new JSONObject(pMessage);

			String lUser = lObject.getString("user");

			// Convert data dir to UNIX like path to eliminate possible errors
			File lFolder = new File(System.getenv("DATA_DIR").toString()
					.replace("\\", "/")
					+ "/" + lUser);
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

	/**
	 * <h1>getFile</h1>
	 * <p>Retrieve the particular file according to user information</p>
	 * 
	 * @param @QueryParam("user") String pUser The user name for the user
	 * @param @QueryParam("filename") String pFileName The file name for the user         
	 * @return HTTP Response that contains the downloaded object
	 */
	@GET
	@Path("/getfile")
	@Produces("application/data")
	public Response getFile(@QueryParam("user") String pUser,
			@QueryParam("filename") String pFileName)
	{
		try
		{

			// Retrieve the file through user information, convert to UNIX format to eliminate
			// possible errors
			File lFile = new File(System.getenv("DATA_DIR").toString()
					.replace("\\", "/")
					+ "/" + pUser + "/" + "/" + pFileName);

			// Build the response and add file as part of the response
			ResponseBuilder lResponse = Response.ok((Object) lFile);
			lResponse.header("Content-Disposition",
					"attachment; filename=\"" + pFileName + "\"");
			
			return lResponse.build();
		}  // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			return Response.noContent().build();
		} // catch
	}  // Response getFile

	/**
	 * 
	 * <p>deleteFile</p>
	 * <p>Delete particular file owned by the user</p>
	 * 
	 * @param pMessage <p>The JSON message string that contains user information, 
	 *            and the file name for the file to retrieve</p>
	 *           <p>JSON parameters and their values</p>
	 *           <p>user type = string</p>
	 *           <p>The username for the user</p>
	 *           <p>parameter type = string</p>
	 *           <p>The file name for the file to be deleted</p>
	 * @return String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The username for the user</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/deletefile")
	public String deleteFile(String pMessage)
	{
		try
		{
			// Convert string into a JSON string
			JSONObject lObject = new JSONObject(pMessage);

			// Retrieve username
			String lUser = lObject.getString("user");

			// Set the file path, convert to UNIX format
			String lFileName = lObject.getString("parameter");
			File lFile = new File(System.getenv("DATA_DIR").toString()
					.replace("\\", "/")
					+ "/" + lUser + "/" + "/" + lFileName);

			// Remove the file
			lFile.delete();

			return "{\"status\": \"success\",\"statusmessage\": \"" + lFileName
					+ " Removed Successfuly\"}";
		}  // catch 
		catch (Exception pException)
		{
			pException.printStackTrace();
			return "{\"status\": \"error\",\"statusmessage\": \""
					+ pException.toString().replace("\\", "/")
							.replace("\"", "\\\"") + "\"}";
		} // catch
	}

	/**
	 * 
	 * <p>uploadwithuri</p>
	 * <p>Upload a file using user inputed uri and owned by the user</p>
	 * 
	 * @param pMessage <p>The JSON message string that contains user information, 
	 *            and the file name for the file to retrieve</p>
	 *           <p>JSON parameters and their values</p>
	 *           <p>user type = string</p>
	 *           <p>The username for the user</p>
	 *           <p>parameter type = string</p>
	 *           <p>The uri for the file to be downloaded</p>
	 * @return String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The username for the user</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/uploadwithuri")
	public String uploadWithURI(String pMessage)
	{
		try
		{
			// Convert to JSON object
			JSONObject lObject = new JSONObject(pMessage);

			String lUser = lObject.getString("user");

			// Analysis uri to try to find file name
			String[] lURLSplit = lObject.getString("parameter").split("/");
			String lFileName = lURLSplit[lURLSplit.length - 1];
			
			// Set the output file and conver to UNIX styles
			File lFile = new File(System.getenv("DATA_DIR").toString()
					.replace("\\", "/")
					+ "/" + lUser + "/" + "/" + lFileName);
			
			// Set the URL to download files from
			URL lURL = new URL(lObject.getString("parameter"));
			FileUtils.copyURLToFile(lURL, lFile);

			// Return the operation status as JSON format
			return "{\"status\": \"success\",\"statusmessage\": \""
					+ lFile.getAbsolutePath().replace("\\", "/") + "\"}";
		}  // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			return "{\"status\": \"error\",\"statusmessage\": \""
					+ pException.toString().replace("\\", "/")
							.replace("\"", "\\\"") + "\"}";
		} // catch
	}  // String uploadWithURI

	/**
	 * 
	 * <p>uploadwithuri</p>
	 * <p>Upload a file using user inputed uri and owned by the user</p>
	 * 
	 * @param pMessage <p>The JSON message string that contains user information, 
	 *            and the file name for the file to retrieve</p>
	 *           <p>JSON parameters and their values</p>
	 *           <p>user type = string</p>
	 *           <p>The username for the user</p>
	 *           <p>parameter type = string</p>
	 *           <p>The uri for the file to be downloaded</p>
	 * @return String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The username for the user</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String upload(
			@FormDataParam("file") InputStream pUploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition pFileDetail)
	{
		// User is admin now, need to change to be able to read user info from form
		String lUser = "admin";
		// Set the file output path and convert to UNIX format
		File lFile = new File(System.getenv("DATA_DIR").toString()
				.replace("\\", "/")
				+ "/" + lUser + "/" + "/" + pFileDetail.getFileName());
		try
		{
			// Retrieve output stream through multipart data
			OutputStream lOutputStream = new FileOutputStream(lFile);
			int lRead = 0;
			// Set the upload threshold to 1 KB for every read
			byte[] lBytes = new byte[1024];
			// Write while read from form data
			while ((lRead = pUploadedInputStream.read(lBytes)) != -1)
			{
				lOutputStream.write(lBytes, 0, lRead);
			} // while

			lOutputStream.flush();
			lOutputStream.close();
			return "{\"status\": \"" + "success" + "\"}";
		} // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			return "{\"status\": \"error\",\"statusmessage\": \""
					+ pException.toString().replace("\\", "/")
							.replace("\"", "\\\"") + "\"}";
		} // catch
	}  // try
}  // class UploadService
