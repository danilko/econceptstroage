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

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.sling.commons.json.JSONObject;

import com.econcept.dao.AccountDAOImpl;
import com.econcept.entities.Account;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountService {
	public final static String CONFIRM="CONFIRM";
	public final static String UNCONFIRM="UNCONFIRM";
	
	public final static String ACCOUNT_ID="account_id";
	public final static String ACCOUNT_PASSWORD="account_password";
	
	/**
	 * 
	 * <p>Get all accounts within persistence storage</p>
	 * 
	 * @param pMessage <p>The JSON message string that contains user information, 
	 *            and the file name for the file to retrieve</p>
	 *           <p>JSON parameters and their values</p>
	 *           <p>account_id type = string</p>
	 *           <p>The account id for the account</p>
	 *           <p>account_password type = string</p>
	 *           <p>The password for the account</p>
	 * @return String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The status for the operation</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/accountlist")
	public String getUserList(String pMessage)
	{
		try
		{
			AccountDAOImpl lImpl = new AccountDAOImpl();
			
			JSONObject lReturnUserListObject = new JSONObject();
			lReturnUserListObject.append("account_list", lImpl.getAllAccount());
			
			return "{\"status\": \"success\",\"statusmessage\": "
					+ lReturnUserListObject.toString() + "}";
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
	 * 
	 * <p>Get all accounts within persistence storage</p>
	 * 
	 * @param pMessage <p>The JSON message string that contains user information, 
	 *            and the file name for the file to retrieve</p>
	 *           <p>JSON parameters and their values</p>
	 *           <p>account_id type = string</p>
	 *           <p>The account id for the account</p>
	 *           <p>account_password type = string</p>
	 *           <p>The account password for the account</p>
	 *           <p>operation type = string</p>
	 *           <p>The operation for to perform for the user credential (ADD, DELETE, CONFIRM, UPDATE)</p>
	 *           <p>Additional parameters for operation ADD</p>
	 *           <p>email type = string</p>
	 *           <p>The email for the account</p>
	 *           <p>last_name type = string</p>
	 *           <p>The last_name for the account</p>
	 *           <p>first_name type = string</p>
	 *           <p>The first name for the account</p>
	 * @return String in JSON format
	 *           <p>JSON parameters and their values</p>
	 *           <p>status type = string</p>
	 *           <p>The status for the operation</p>
	 *           <p>statusmessage type = string/string array</p>
	 *           <p>The status message to log detail information about operation</p>
	 */
	@POST
	@Path("/account")
	public String updateAccount(String pMessage)
	{
		try
		{
			JSONObject lJSONObject = new JSONObject(pMessage);
			String lOperation = lJSONObject.getString("operation");
			
			StringBuilder lStringBuilder = new StringBuilder();
			lStringBuilder.append("{\"status\": \"success\",\"statusmessage\": ");
			
			AccountDAOImpl lImpl = new AccountDAOImpl();
			
			// If the operation is confirm.  check if the account exists and is confirmed
			if(lOperation.equals(CONFIRM))
			{
				// Check if status is 
				if(lImpl.getConfirmedAccount(
						lJSONObject.getString(ACCOUNT_ID),
						lJSONObject.getString(ACCOUNT_PASSWORD)))
				{
					lStringBuilder.append("\"" + CONFIRM + "\"");
				}  // if
				else
				{
					lStringBuilder.append("\"" + UNCONFIRM + "\"");
				}  // else
			}  // if
			// If the operation is ADD, add the account into the persistence storage
			else if(lOperation.equals("ADD"))
			{
				lImpl.addAccount(
						lJSONObject.getString(ACCOUNT_ID),
						lJSONObject.getString("first_name"),
						lJSONObject.getString("last_name"),
						lJSONObject.getString("account_password"),
						lJSONObject.getString("email"),
						Account.ACCOUNT_STATUS_UNCONFIRM);
			}  // if
			// If the operation is UPDATE, add the account into the persistence storage
			else if(lOperation.equals("UPDATE"))
			{
				//TODO Add update mechanism
			}  // if
			// If the operation is DELETE, remove the account from the persistence storage
			else if(lOperation.equals("DELETE"))
			{
				lImpl.deleteAccountByAccountID(lJSONObject.getString(ACCOUNT_ID));
			}  // else
			
			lStringBuilder.append("}");
			
			return lStringBuilder.toString();
		} // try
		catch (Exception pException)
		{
			pException.printStackTrace();
			return "{\"status\": \"error\",\"statusmessage\": \""
					+ pException.toString().replace("\\", "/")
							.replace("\"", "\\\"") + "\"}";
		} // catch
	} // String getFileList
}  // class AccountService
