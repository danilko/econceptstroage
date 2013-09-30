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


package com.econcept.webservice.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.springframework.security.core.context.SecurityContextHolder;

import com.econcept.entities.FileEntity;
import com.econcept.provider.FileProvider;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 
 * @author Kai - Ting (Danil) Ko
 * <h1>FileEndpoint</h1>
 * <p>To provide rest end points for upload, delete, get file, get file lists according to user input</p>
 *
 */

@Path("/file")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FileService
{
	public final static String DATA_DIR="DATA_DIR";
	
	@Resource
	private FileProvider mFileService;
	
	/**
	 * Get the UserName from the SecurityContext
	 * @return String The UserName in the SecurityContext 
	 */
	private String getUserName()
	{
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}  // String getUserName
	
	/**
	 * 
	 * <p>Get the all files that owned by the user according to the user information</p>
	 * 
	 * @return Response HTTP Status with String in FileEntity JSON format
	 *         
	 */
	@GET
	@Produces("application/json")
	public Response getFileList()
	{
		ResponseBuilder lBuilder = null;
		try
		{
			ObjectMapper lMapper = new ObjectMapper();
			lBuilder = Response.status(Status.ACCEPTED).entity(lMapper.writeValueAsString(mFileService.getFileList(getUserName())));
		} // try
		catch (Exception pException)
		{
			throw new WebApplicationException(pException);
		} // catch
		
		return lBuilder.build();
	} // String getFileList

	/**
	 * 
	 * <p>Retrieve the particular file according to user information</p>
	 * 
	 * @param @PathParam("filename") String pFileName The file name to be retrieved         
	 * @return HTTP Response that contains the download object
	 * 
	 */
	@GET
	@Path("/{filename}")
	@Produces("application/data")
	public Response getFile(@PathParam("filename") String pFileName)
	{
		ResponseBuilder lBuilder = null;
		
		try
		{
			// Retrieve the file through user information, convert to UNIX format to eliminate
			// possible errors
			File lFile = new File(System.getenv(DATA_DIR).toString()
					.replace("\\", "/")
					+ "/" + getUserName() + "/" + "/" + pFileName);

			// Build the response and add file as part of the response
			lBuilder = Response.status(Status.ACCEPTED).entity((Object) lFile);
			lBuilder.header("Content-Disposition",
					"attachment; filename=\"" + pFileName + "\"");
		}  // try
		catch (Exception pException)
		{
			throw new WebApplicationException(pException);
		} // catch
		
		return lBuilder.build();
	}  // Response getFile

	/**
	 * 
	 * <p>Delete particular file owned by the user</p>
	 * 
     * @param @PathParam("filename") String pFileName The file name to be deleted
	 * @return Response HTTP Status 200 if
	 */
	@DELETE
	@Produces("application/json")
	@Path("/{filename}")
	public Response deleteFile(@PathParam("filename") String pFileName)
	{
		ResponseBuilder lBuilder = null;
		
		try
		{
			// Delete the file and return status
			mFileService.deleteFile(getUserName(), pFileName);
			lBuilder = Response.status(Status.ACCEPTED);
		}  // try
		catch (Exception pException)
		{
			throw new WebApplicationException(pException);
		} // catch
		
		return lBuilder.build();
	}  // Response deleteFile

	/**
	 * 
	 * <p>Upload a file using user inputed uri and owned by the user</p>
	 * 
	 * @param pMessage <p>The JSON message string that contains user information, 
	 *            and the file name for the file to retrieve</p>
	 *           <p>JSON parameters and their values</p>
	 *           <p>uri type = string</p>
	 *           <p>The uri for the file to be downloaded</p>
	 * @return Response HTTP Status with String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The status result for the operation</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/uri")
	@Produces("application/json")
	public Response uploadWithURI(String pMessage)
	{
		ResponseBuilder lBuilder = null;
		
		try
		{
			// Convert to JSON object
			ObjectMapper lMapper = new ObjectMapper();
			FileEntity lFileEntity = lMapper.readValue(pMessage, FileEntity.class);
			
			String lFilePath = System.getenv(DATA_DIR).toString()
					.replace("\\", "/")
					+ "/" + getUserName() + "/" + "/" + lFileEntity.getFileName();
			
			// Set the output file and conver to UNIX styles
			File lFile = new File(lFilePath);
			
			// Set the URL to download files from
			URL lURL = new URL(lFileEntity.getURI());
			FileUtils.copyURLToFile(lURL, lFile);
			
			lFile = new File(lFilePath);
			
			lFileEntity.setFileSize(Long.toString(lFile.getTotalSpace()));
			lFileEntity.setLastModified(Long.toString(lFile.lastModified()));
			lFileEntity.setURI(null);
			
			// Set upload status
			lBuilder = Response.status(Status.ACCEPTED).entity(lMapper.writeValueAsString(lFileEntity));
		}  // try
		catch (Exception pException)
		{
			throw new WebApplicationException(pException);
		} // catch
		
		return lBuilder.build();
	}  // Repose uploadWithURI

	/**
	 * 
	 * <p>Upload a file using user inputed uri and owned by the user</p>
	 * @param pAccountID
	 *           <p>Form Parameters for account id</p>
	 *           <p>txtAccountID type=form-data</p>
	 *           <p>The mulitpart form data that represents the uploaded data</p>
	 * @param pAttachment 
	 *           <p>Form Parameters to upload file as multipart and cast as Attachment to retrieve file information</p>
	 *           <p>fileUploadFile type=form-data</p>
	 *           <p>The mulitpart form data that represents the uploaded data</p>
	 * @param pAttachment 
	 *           <p>Form Parameters to upload file as multipart and cast as InputStream to retrieve file content as input stream</p>
	 *           <p>fileUploadFile type=form-data</p>
	 *           <p>The mulitpart form data that represents the uploaded data</p>
	 * @return Response HTTP Status with String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The status result for the operation</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/upload")
	@Produces("application/json")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response upload(
			@Multipart("fileUploadFile") Attachment pAttachment,
			@Multipart("fileUploadFile") InputStream pUploadedFileInputStream)
	{

		ResponseBuilder lBuilder = null;
		
		// Set the file output path and convert to UNIX format
		File lFile = new File(System.getenv(DATA_DIR).toString()
				.replace("\\", "/")
				+ "/" + getUserName() + "/" + "/" + pAttachment.getContentDisposition().getParameter("filename"));
		try
		{
			// Retrieve output stream through multipart data
			OutputStream lOutputStream = new FileOutputStream(lFile);
			int lRead = 0;
			// Set the upload threshold to 1 KB for every read
			byte[] lBytes = new byte[1024];
			// Write while read from form data
			while ((lRead = pUploadedFileInputStream.read(lBytes)) != -1)
			{
				lOutputStream.write(lBytes, 0, lRead);
			} // while

			lOutputStream.flush();
			lOutputStream.close();
			
			pUploadedFileInputStream .close();
			
			lBuilder = Response.status(Status.ACCEPTED);
		} // try
		catch (Exception pException)
		{
			throw new WebApplicationException(pException);
		} // catch

		return lBuilder.build();
	}  // Response upload
}  // class UploadService
