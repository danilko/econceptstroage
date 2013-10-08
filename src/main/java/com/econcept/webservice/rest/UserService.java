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

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.springframework.security.core.context.SecurityContextHolder;

import com.econcept.entities.User;
import com.econcept.provider.UserProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author Kai - Ting (Danil) Ko
 * <h1>AccountEndpoint</h1>
 * <p>To provide rest end points for account management</p>
 *
 */

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService 
{	
	@Resource
	UserProvider mUserService;
	
	/**
	 * Get the UserName from the SecurityContext
	 * @return String The UserName in the SecurityContext 
	 */
	private User getUser()
	{
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}  // String getUserName
	
	@GET
	public Response getCXFSToken()
	{
		return Response.status(Status.OK).entity("[]").build();
	}  // Response getCXFSToken
	
	@Path("/authenticate")
	@POST
	public Response authenticate()
	{
		ResponseBuilder lBuilder = null;
		
		try
		{
			User lUser = getUser();

			ObjectMapper lMapper = new ObjectMapper();
			
			lBuilder = Response.status(Status.OK).entity(lMapper.writeValueAsString(lUser));
		}  // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			
			lBuilder = Response.status(Status.INTERNAL_SERVER_ERROR).entity(pException);
		} // catch
		
		return lBuilder.build();
	}  // Response authenticate()
	
	/**
	 * 
	 * <p>Update/Create account within persistence storage</p>
	 * 
	 * @param @PathParam("accountid") String pAccountID The account id to be updated/created
	 * @param pMessage <p>The JSON message string that contains user information, 
	 *            and the file name for the file to retrieve</p>
	 *           <p>JSON parameters and their values</p>
	 *           <p>account_id type = string</p>
	 *           <p>The account id for the account</p>
	 *           <p>account_password type = string</p>
	 *           <p>The account password for the account</p>
	 *           <p>email type = string</p>
	 *           <p>The email for the account</p>
	 *           <p>last_name type = string</p>
	 *           <p>The last_name for the account</p>
	 *           <p>first_name type = string</p>
	 *           <p>The first name for the account</p>
	 * @return Response HTTP Status with String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The status for the operation</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/{user_id}")
	public Response updateAccount(@PathParam("user_id") String pAccountID, String pMessage)
	{
		ResponseBuilder lBuilder;
		
		try
		{

			lBuilder = Response.status(Status.OK).entity("[]");
		} // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			
			lBuilder = Response.status(Status.INTERNAL_SERVER_ERROR).entity(pException);
		} // catch
		
		return lBuilder.build();
	} // String updateAccount
	
	/**
	 * 
	 * <p>Update account within persistence storage</p>
	 * 
	 * @param @PathParam("accountid") String pAccountID The account ID for the account to be removed
	 * @return Response HTTP Status with String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The status for the operation</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	
	@DELETE
	@Path("/{user_id}")
	public Response deleteUser(@PathParam("account_id") String pUserID)
	{
		ResponseBuilder lBuilder;
		
		try
		{
			User lUser = new User();
			lUser.setUserID(pUserID);
			
			mUserService.deleteUser(lUser);
			
			lBuilder = Response.status(Status.OK).entity("[]");
		} // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			lBuilder = Response.status(Status.INTERNAL_SERVER_ERROR).entity(pException);
		} // catch
		
		return lBuilder.build();
	} // Response deleteUser
}  // class AccountService
