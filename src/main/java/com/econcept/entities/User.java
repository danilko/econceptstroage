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
 * Entity Object to represent one role in Table USERS in persistence storage
 */

package com.econcept.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="users")
public class User implements UserDetails
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -4286123905019916530L;
	
	@Column(name = "user_name", unique = true, nullable = false, length=60)
	private String user_name;
	
	@Id
	@Column(name = "user_id", unique = true, nullable = false, length=60)
	private String user_id;
	
	@Column(name = "first_name", unique = true, nullable = false, length=60)
	private String first_name;
	
	@Column(name = "last_name", unique = true, nullable = false, length=60)
	private String last_name;
	
	@Column(name = "user_password", unique = true, nullable = false, length=60)
	private String user_password;
	
	@Column(name = "email", unique = true, nullable = false, length=60)
	private String email;
	
	@Column(name = "user_non_locked", nullable = false, length=1)
	private String user_non_locked;
	
	@Column(name = "credentials_non_expired", nullable = false, length=1)
	private String credentials_non_expired;
	
	@Column(name = "user_non_expired", nullable = false, length=1)
	private String user_non_expired;
	
	@Column(name = "user_created_date", nullable = false, length=8)
	private String user_created_date;
	
	@Column(name = "user_last_modified_date", nullable = false, length=8)
	private String user_last_modified_date;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user_id", cascade=CascadeType.ALL, targetEntity=UserRole.class)
	private Set <UserRole> mUserRoles;
	
	/**
	 * Empty Constructor
	 */
	public User()
	{
		mUserRoles=new HashSet<UserRole>(0);
	}  // User
	
	/**
	 * Initialize basic variables
	 * @param pUserName
	 * @param pPassword
	 * @param pFirstName
	 * @param pLastName
	 * @param pEmail
	 * @param pAccountNonLocked
	 * @param pCredentialsNonExpired
	 * @param pAccountNonExpired
	 * @param pAccountCreatedDate
	 * @param pAccountLastModifiedDate
	 */
	public User(String pID, String pUserName, String pUserPassword, String pFirstName, String pLastName, String pEmail, String pAccountNonLocked, String pCredentialsNonExpired, String pAccountNonExpired, String pAccountCreatedDate, String pAccountLastModifiedDate)
	{
		user_name=pUserName;
		user_password=pUserPassword;
		first_name=pFirstName;
		last_name=pLastName;
		email=pEmail;
		user_non_locked=pAccountNonLocked;
		user_non_expired=pAccountNonLocked;
		credentials_non_expired=pCredentialsNonExpired;
		user_created_date=pAccountCreatedDate;
		user_last_modified_date=pAccountLastModifiedDate;
		mUserRoles=new HashSet<UserRole>(0);
	}  // User

	public String getUserID() 
	{
		return user_id;
	}  // String getUserID

	public void setUserID(String pUserID) 
	{
		user_id = pUserID;
	}  // void setUserID
	
	public String getUsername() 
	{
		return user_name;
	}  // String getUsername

	public String getUserName() 
	{
		return user_name;
	}  // String getUserName
	
	public void setUserName(String pUserserName) 
	{
		user_name = pUserserName;
	}  // void setUserName
	
	public String getFirstName() 
	{
		return first_name;
	}  // String getFirstName
	
	public void setFirstName(String pFirstName) 
	{
		first_name = pFirstName;
	}  // void setFirstName
	
	public String getLastName() 
	{
		return last_name;
	}  // String getLastName
	
	public void setLastName(String pLastName) 
	{
		last_name = pLastName;
	}  // void setLastName
	
	public String getPassword() 
	{
		return user_password;
	}  // String getPassword
	
	public String getUserPassword() 
	{
		return user_password;
	}  // String getUserPassword
	
	public void setUserPassword(String pUserPassword) 
	{
		user_password = pUserPassword;
	}  // void setUserPassword
	
	public String getEmail() 
	{
		return email;
	}  // String getEmail
	
	public void setEmail(String pEmail) 
	{
		email = pEmail;
	}  // void setEmail
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> lAuthList = new ArrayList<GrantedAuthority>();
		
		for(UserRole lUserRole : mUserRoles)
		{
			lAuthList.add(lUserRole.getUserAuthority());
		}  // for
		
		return lAuthList;
	}  // Collection<? extends GrantedAuthority> getAuthorities()

	@Override
	public boolean isAccountNonExpired() 
	{
		if(user_non_expired.equalsIgnoreCase("Y"))
		{
			return true;
		}  // if
		
		return false;
	}  // boolean isAccountNonExpired

	public String getUserNonLocked() 
	{
		return user_non_locked;
	}  // String getUserNonLocked
	
	public void setUserNonLocked(String pUserNonLocked) 
	{
		user_non_locked = pUserNonLocked;
	}  // void setUserNonLocked
	
	
	public boolean isAccountNonLocked() 
	{
		if(user_non_locked.equalsIgnoreCase("Y"))
		{
			return true;
		}  // if
		
		return false;
	}  // boolean isAccountNonLocked

	public String getCredentialsNonExpired() 
	{
		return credentials_non_expired;
	}  // String getCredentialsNonExpired 
	
	public void setCredentialsNonExpired(String pCredentialsNonExpired) 
	{
		credentials_non_expired = pCredentialsNonExpired;
	}  // void setCredentialsNonExpired
	
	public boolean isCredentialsNonExpired() 
	{
		if(credentials_non_expired.equalsIgnoreCase("Y"))
		{
			return true;
		}  // if
		
		return false;
	}  //  boolean isCredentialsNonExpired
	
	public boolean isEnabled() 
	{
		if(user_non_locked.equalsIgnoreCase("Y"))
		{
			return true;
		}  // if
		
		return false;
	}  // boolean isEnabled 
	
	public Set <UserRole> getUserRoles()
	{
		return mUserRoles;
	}  // Set <UserRole> getUserRoles
	
	public void setUserRoles(Set <UserRole> pUserRoles)
	{
		mUserRoles = pUserRoles;
	}  // void setUserRoles
	
	@Override
	public int hashCode()
	{
		if(user_id == null)
		{
			return 0;
		}  // if
		
		return user_id.hashCode();
	}  // int hashCode

}  // class Account
