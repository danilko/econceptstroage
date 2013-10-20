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
 * Entity Object one role in Table USER_AUTHORITIES Table in persistence storage
 */

package com.econcept.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="user_authorities")
public class UserAuthority implements GrantedAuthority
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 5867799148510937708L;

	@Id
	@Column(name="user_authority_id", nullable=false, unique=true, length=60)
	private String user_authority_id;
	
	@Column(name="user_authority_name", nullable=false, unique=true, length=60)
	private String user_authority_name;

	public String getUserAuthorityID() 
	{
		return user_authority_id;
	}  // String getUserAuthorityID

	public void setUserauthorityID(String pUserAuthorityID) 
	{
		user_authority_id= pUserAuthorityID;
	}  // setUserauthorityID

	public String getUserauthorityName() 
	{
		return user_authority_name;
	}  // String getUserauthorityName

	public void setUserAuthorityName(String pUserAuthorityName) 
	{
		user_authority_name = pUserAuthorityName;
	}  // void setUserAuthorityName

	public String getAuthority() {
		return user_authority_name;
	}  // String getAuthority
	
	@Override
	public int hashCode()
	{
		if(user_authority_id == null)
		{
			return 0;
		}  // if
		
		return user_authority_id.hashCode();
	}  // int hashCode
}  // class UserAuthority
