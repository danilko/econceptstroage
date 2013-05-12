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



@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UploadService {
	
	@POST
	@Path("/getfilelist")
	public String getFileList(String pMessage) {
		try {
			JSONObject lObject = new JSONObject(pMessage);
			
			String lUser = lObject.getString("user");
			
			File lFolder = new File(System.getenv("DATA_DIR").toString()
					.replace("\\", "/") + "/" + lUser);
			File[] lFileList = lFolder.listFiles();
			StringBuilder lBuilder = new StringBuilder();
			lBuilder.append("[");
			for(int lIndex = 0; lIndex < lFileList.length; lIndex++)
			{
				lBuilder.append("\"");
				lBuilder.append(lFileList[lIndex].getName());
				lBuilder.append("\"");
				
				if(lIndex + 1 < lFileList.length)
				{
					lBuilder.append(",");
				}  // if
			}  // for
			lBuilder.append("]");
			
			return "{\"status\": \"success\",\"statusmessage\": "
			+ lBuilder.toString() + "}";
			
		} catch (Exception pException) {
			pException.printStackTrace();
			return "{\"status\": \"error\",\"statusmessage\": \""
			+ pException.toString().replace("\\", "/")
					.replace("\"", "\\\"") + "\"}";
		} // catch
	}
	
	@GET
	@Path("/getfile")
	@Produces("application/data")
	public Response getFile(
			@QueryParam("user") String pUser,
			@QueryParam("filename") String pFileName
			) {
		try {
			
			File lFile = new File(System.getenv("DATA_DIR").toString()
					.replace("\\", "/") + "/" + pUser + "/"
					+ "/" + pFileName);
			
			ResponseBuilder lResponse = Response.ok((Object) lFile);
			lResponse.header("Context-Disposition", "attachement; filename=save.txt");
			return lResponse.build();
		} catch (Exception pException) {
			pException.printStackTrace();
			return Response.noContent().build();
		} // catch
	}

	@POST
	@Path("/deletefile")
	public String deleteFile(String pMessage) {
		try {
			JSONObject lObject = new JSONObject(pMessage);
			
			String lUser = lObject.getString("user");
			
			String lFileName = lObject.getString("parameter");
			File lFile = new File(System.getenv("DATA_DIR").toString()
					.replace("\\", "/") + "/" + lUser + "/"
					+ "/" + lFileName);

			lFile.delete();

			return "{\"status\": \"success\",\"statusmessage\": \""
					+ lFileName + " Removed Successfuly\"}";
		} catch (Exception pException) {
			pException.printStackTrace();
			return "{\"status\": \"error\",\"statusmessage\": \""
					+ pException.toString().replace("\\", "/")
							.replace("\"", "\\\"") + "\"}";
		} // catch
	}
	
	
	@POST
	@Path("/uploadwithuri")
	public String uploadWithURI(String pMessage) {
		try {
			JSONObject lObject = new JSONObject(pMessage);
			
			String lUser = lObject.getString("user");
			
			String [] lURLSplit = lObject.getString("parameter").split("/");
			String lFileName = lURLSplit [lURLSplit.length - 1];
			File lFile = new File(System.getenv("DATA_DIR").toString()
					.replace("\\", "/") + "/" + lUser + "/"
					+ "/" + lFileName);
			URL lURL = new URL(lObject.getString("parameter"));
			FileUtils.copyURLToFile(lURL, lFile);

			return "{\"status\": \"success\",\"statusmessage\": \""
					+ lFile.getAbsolutePath().replace("\\", "/") + "\"}";
		} catch (Exception pException) {
			pException.printStackTrace();
			return "{\"status\": \"error\",\"statusmessage\": \""
					+ pException.toString().replace("\\", "/")
							.replace("\"", "\\\"") + "\"}";
		} // catch
	}

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String upload(
			@FormDataParam("file") InputStream pUploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition pFileDetail) {
		String lUser = "admin";
		File lFile = new File(System.getenv("DATA_DIR").toString()
				.replace("\\", "/") + "/" + lUser + "/"
				+ "/" + pFileDetail.getFileName());
		try {
			OutputStream lOutputStream = new FileOutputStream(lFile);
			int lRead = 0;
			byte[] lBytes = new byte[1024];
			while ((lRead = pUploadedInputStream.read(lBytes)) != -1) {
				lOutputStream.write(lBytes, 0, lRead);
			} // while

			lOutputStream.flush();
			lOutputStream.close();
			return "{\"status\": \"" + "success" + "\"}";
		}  // try
		catch (Exception pException) {
			return "{\"status\": \"error\",\"statusmessage\": \""
					+ pException.toString().replace("\\", "/")
							.replace("\"", "\\\"") + "\"}";
		}  // catch
	}
}
