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

package com.econcept.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="USER_ACCOUNT")
public class UserAccount implements UserDetails
{
	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = -4286123905019916530L;
	
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	private String user_id;
	
	@Column(name = "FIRST_NAME", unique = true, nullable = false)
	private String first_name;
	
	@Column(name = "LAST_NAME", unique = true, nullable = false)
	private String last_name;
	
	@Column(name = "USER_PASSWORD", unique = true, nullable = false)
	private String user_password;
	
	@Column(name = "EMAIL", unique = true, nullable = false)
	private String email;
	
	@Column(name = "USER_NON_LOCKED", nullable = false)
	private String user_non_locked;
	
	@Column(name = "CREDENTIALS_EXPIRED", nullable = false)
	private String credentials_expired;
	
	@Column(name = "USER_NON_EXPIRED", nullable = false)
	private String user_non_expired;
	
	@Column(name = "USER_CREATED_DATE", nullable = false)
	private String user_created_date;
	
	@Column(name = "USER_LAST_MODIFIED_DATE", nullable = false)
	private String user_last_modified_date;
	
	@OneToMany(mappedBy="user_account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set <UserRole> user_roles;
	
	/**
	 * Empty Constructor
	 */
	public UserAccount()
	{
	}  // User
	
	/**
	 * Initialize basic variables
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
	public UserAccount(String pUserID, String pUserPassword, String pFirstName, String pLastName, String pEmail, String pAccountNonLocked, String pCredentialsNonExpired, String pAccountNonExpired, String pAccountCreatedDate, String pAccountLastModifiedDate)
	{
		user_id=pUserID;
		user_password=pUserPassword;
		first_name=pFirstName;
		last_name=pLastName;
		email=pEmail;
		user_non_locked=pAccountNonLocked;
		user_non_expired=pAccountNonLocked;
		credentials_expired=pCredentialsNonExpired;
		user_created_date=pAccountCreatedDate;
		user_last_modified_date=pAccountLastModifiedDate;
		user_roles=new HashSet<UserRole>(0);
	}  // User

	public String getUserID() 
	{
		return user_id;
	}  // String getUserID

	public void setUserID(String pUserID) 
	{
		user_id = pUserID;
	}  // void setUserID
	
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
	
	@JsonIgnore
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
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> lAuthList = new ArrayList<GrantedAuthority>();
		
		for(UserRole lUserRole : user_roles)
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

	public String getCredentialsExpired() 
	{
		return credentials_expired;
	}  // String getCredentialsExpired 
	
	public void setDataCredentialsExpired(String pCredentialsExpired) 
	{
		credentials_expired = pCredentialsExpired;
	}  // void setCredentialsNonExpired
	
	@Override
	public boolean isCredentialsNonExpired() 
	{
		if(credentials_expired.equalsIgnoreCase("N"))
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
	
	@JsonManagedReference
	public Set <UserRole> getUserRoles()
	{
		return user_roles;
	}  // Set <UserRole> getUserRoles
	
	@JsonManagedReference
	public void setUserRoles(Set <UserRole> pUserRoles)
	{
		user_roles = pUserRoles;
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

	@Override
	public String getUsername() 
	{
		return user_id;
	}
	
	public void setUsername(String pUserID) 
	{
		user_id=pUserID;
	}
}  // class Account
