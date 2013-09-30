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

package com.econcept.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_roles")
public class UserRole implements java.io.Serializable
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 721830185488417281L;

	@Id
	@Column(name = "user_role_id", nullable=false, unique=true, length=60)
	private String user_role_id;
	
	@Column(name = "user_id", nullable=false, length=60)
	private String user_id;
	
	@Column(name = "user_authority_id", nullable=false, length=60)
	private String user_authority_id;
	
	//@OneToOne(fetch =FetchType.LAZY, mappedBy = "user_authority_id",  cascade=CascadeType.ALL, targetEntity=UserAuthority.class)
	private UserAuthority mUserAuthority;
	
	public String getUserRoleID() 
	{
		return user_role_id;
	}  // String getUserRoleID

	public void setUserRole(String pUserRoleID) 
	{
		user_role_id = pUserRoleID;
	}  // void setUserRoleID

	public String getUserID() 
	{
		return user_id;
	}  // String getUserID

	public void setUserID(String pUserID) 
	{
		user_id = pUserID;
	}  // void setUserID

	public String getUserAuthorityID() 
	{
		return user_authority_id;
	}  //  String getUserAuthorityID

	public void setUserAuthorityID(String pUserAuthorityID) {
		user_authority_id = pUserAuthorityID;
	}  //  void setUserAuthorityID

	public void setUserAuthority(UserAuthority pUserAuthority) 
	{
		mUserAuthority=pUserAuthority;
	}  // UserAuthority getUserAuthority
	
	public UserAuthority getUserAuthority() 
	{
		return mUserAuthority;
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
