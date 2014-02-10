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
 * Entity Object one role in Table USER_ROLES in persistence storage
 */

package com.econcept.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="USER_ROLE")
public class UserRole implements java.io.Serializable
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 721830185488417281L;

	@Id
	@Column(name = "USER_ROLE_ID", nullable=false, unique=true)
	private String user_role_id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	@JsonBackReference
	private UserAccount user_account;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_AUTHORITY_ID")
	@JsonManagedReference
	private UserAuthority user_authority;
	
	/**
	 * Get the user role id
	 * @return String The user role id
	 */
	public String getUserRoleID() 
	{
		return user_role_id;
	}  // String getUserRoleID

	/**
	 * Set the user role id
	 * @param pUserRoleID The user role id
	 */
	public void setUserRole(String pUserRoleID) 
	{
		user_role_id = pUserRoleID;
	}  // void setUserRoleID

	@JsonBackReference
	public void setUserAccount(UserAccount pUserAccount)
	{
		user_account = pUserAccount;
	}  // void setUser
	
	@JsonBackReference
	public UserAccount getUserAccount()
	{
		return user_account;
	}  // void getUser
	
	public void setUserAuthority(UserAuthority pUserAuthority) 
	{
		user_authority=pUserAuthority;
	}  // UserAuthority getUserAuthority
	
	public UserAuthority getUserAuthority() 
	{
		return user_authority;
	}  // UserAuthority getUserAuthority
	
	@Override
	public int hashCode()
	{
		if(user_role_id == null)
		{
			return 0;
		}  // if
		
		return user_role_id.hashCode();
	}  // int hashCode
}  // class UserRole
