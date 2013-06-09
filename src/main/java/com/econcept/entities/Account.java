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

/**
 * @author Kai - Ting (Danil) Ko
 * Entity Object to represent the ACCOUNT Table in persistence storage
 */

package com.econcept.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account
{
	// ACCOUNT STATUS
	public final static int ACCOUNT_STATUS_UNCONFIRM = 0;
	public final static int ACCOUNT_STATUS_CONFIRM = 1;
	public final static int ACCOUNT_STATUS_LOCKED = 2;
	
	// ACCOUNT PERMISSION LEVEL
	public final static int ACCOUNT_PERMISSION_ADMIN = 0;
	public final static int ACCOUNT_PERMISSION_VENDOR= 1;
	public final static int ACCOUNT_PERMISSION_CUSTOMER = 2;
	
	@Id
	@Column(name = "ACCOUND_ID")
	private String mAccountID;
	
	@Column(name = "FIRST_NAME")
	private String mFirstName;
	
	@Column(name = "LAST_NAME")
	private String mLastName;
	
	@Column(name = "ACCOUNT_PASSWORD")
	private String mAccountPassword;
	
	@Column(name = "EMAIL")
	private String mEmail;
	
	@Column(name = "ACCOUNT_STATUS")
	private int mStatus;
	
	@Column(name = "ACCOUNT_PERMISSION")
	private int mPermission;
	
	public String getAccountID() 
	{
		return mAccountID;
	}  // String getAccountID
	
	public void setAccountID(String pAccountID) 
	{
		mAccountID = pAccountID;
	}  // void setAccountID
	
	public String getFirstName() 
	{
		return mFirstName;
	}  // String getFirstName
	
	public void setFirstName(String pFirstName) 
	{
		mFirstName = pFirstName;
	}  // void setFirstName
	
	public String getLastName() 
	{
		return mLastName;
	}
	
	public void setLastName(String pLastName) 
	{
		mLastName = pLastName;
	}  // void setLastName
	
	public String getPassword() 
	{
		return mAccountPassword;
	}  // String getPassword
	
	public void setPassword(String pAccountPassword) 
	{
		mAccountPassword = pAccountPassword;
	}  // void setPassword
	
	public String getEmail() 
	{
		return mEmail;
	}  // String getEmail
	
	public void setEmail(String pEmail) 
	{
		mEmail = pEmail;
	}  // void setEmail
	
	public int getStatus() 
	{
		return mStatus;
	}  // int getStatus
	
	public void setStatus(int pStatus) 
	{
		mStatus = pStatus;
	}  // void setStatus
	
	public int getPermission() 
	{
		return mPermission;
	}  // int getPermission
	
	public void setPermission(int pPermission) 
	{
		mPermission = pPermission;
	}  // void setPermission
}  // class Account
