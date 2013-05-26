package com.econcept.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Account extends AbstractPersistable<Long>
{
	private static final long serialVersionUID = -6854123090525521467L;

	@Id
	@Column(name = "accountid")
	private String mAccountID;
	
	@Column(name = "firstname")
	private String mFirstName;
	
	@Column(name = "lastname")
	private String mLastName;
	
	@Column(name = "password")
	private String mPassword;
	
	@Column(name = "email")
	private String mEmail;
	
	@Column(name = "status")
	private String mStatus;
	
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
		return mPassword;
	}  // String getPassword
	
	public void setPassword(String pPassword) 
	{
		mPassword = pPassword;
	}  // void setPassword
	
	public String getEmail() 
	{
		return mEmail;
	}  // String getEmail
	
	public void setEmail(String pEmail) 
	{
		mEmail = pEmail;
	}  // setEmail
	
	public String getStatus() 
	{
		return mStatus;
	}  // getStatus
	
	public void setStatus(String pStatus) 
	{
		mStatus = pStatus;
	}  // void setStatus
}  // class Account
