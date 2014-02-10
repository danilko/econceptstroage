package com.econcept.provider;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.econcept.dao.UserAccountDAO;
import com.econcept.entity.UserAccount;

@Service
public class UserProvider implements UserDetailsService
{
	@Resource
	private UserAccountDAO mUserAccountDAO;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserProvider.class);
	
	private final static int PASSWORD_MINIMUM_LENGTH = 8;
	private final static int PASSWORD_MAXIMUM_LENGTH = 60;
	
	public List<UserAccount> getUserList()
	{
			return mUserAccountDAO.getUserByUserID(null);
	} // String getFileList
	
	public UserAccount updateUser(UserAccount pUserAccount)
	{
		if(pUserAccount == null)
		{
			return null;
		}  // if
		
		// If it is an invalid user id, return null
		if(pUserAccount.getUserID() == null)
		{
			return null;
		}  // if

		// Only update if the object is valid
		if(mUserAccountDAO.getUserByUserID(pUserAccount.getUserID()) != null)
		{
			// Password cannot be empty
			if(pUserAccount.getPassword() == null)
			{
				return null;
			}  // if
			// Password cannot be less than 8 or greater than 60
			if(pUserAccount.getPassword().length() < PASSWORD_MAXIMUM_LENGTH || pUserAccount.getPassword().length() > PASSWORD_MINIMUM_LENGTH )
			{
				return null;
			}  // if
			
			mUserAccountDAO.updateUser(pUserAccount);
			
			return pUserAccount;
		} // if

		return null;
	}  // void updateUser
	
	public void deleteUser(UserAccount pUserAccount)
	{
		// Only delete if the object is valid
		List<UserAccount> lList = mUserAccountDAO.getUserByUserID(pUserAccount.getUserID());
		
		if(lList.size() > 0)
		{
			mUserAccountDAO.deleteUser(lList.get(0));
		} // if
	}  // void deleteUser

	@Override
	public UserDetails loadUserByUsername(String pUserAccountID){

		// Only update if the object is valid
		List<UserAccount> lList = mUserAccountDAO.getUserByUserID(pUserAccountID);
		
		if(lList.size() > 0)
		{
			return lList.get(0);
		} // if
		
		LOGGER.debug("No match user found");
		
		throw new UsernameNotFoundException("No match user found");
	}  // UserDetails loadUserByUsername

}  // class UserProvider
