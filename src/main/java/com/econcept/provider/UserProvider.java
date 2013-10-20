package com.econcept.provider;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.econcept.dao.UserDAO;
import com.econcept.entities.User;

@Service
public class UserProvider implements UserDetailsService
{
	@Resource
	private UserDAO mUserDAO;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserProvider.class);
	
	private final static int PASSWORD_MINIMUM_LENGTH = 8;
	private final static int PASSWORD_MAXIMUM_LENGTH = 60;
	
	public List<User> getUserList()
	{
			return mUserDAO.getUsers(null);
	} // String getFileList
	
	public User updateUser(User pUser)
	{
		if(pUser == null)
		{
			return null;
		}  // if
		
		// If it is an invalid user id, return null
		if(pUser.getUserID() == null)
		{
			return null;
		}  // if

		// Only update if the object is valid
		if(mUserDAO.getUsers(pUser).size() == 1)
		{
			// Password cannot be empty
			if(pUser.getPassword() == null)
			{
				return null;
			}  // if
			// Password cannot be less than 8 or greater than 60
			if(pUser.getPassword().length() < PASSWORD_MINIMUM_LENGTH || pUser.getPassword().length() > PASSWORD_MINIMUM_LENGTH )
			{
				return null;
			}  // if
			
			mUserDAO.updateUser(pUser);
			
			return pUser;
		} // if

		return null;
	}  // void updateUser
	
	public void deleteUser(User pUser)
	{
		mUserDAO.deleteUser(pUser);
	}  // void deleteUser

	@Override
	public UserDetails loadUserByUsername(String pUserName){
		User lUser = new User();
		lUser.setUserName(pUserName);
		
		List<User> lList = mUserDAO.getUsers(lUser);
		
		if(lList != null)
		{
			// Get the first one
			if(lList.size() > 0)
			{
				return (User)lList.get(0);
			}  // if
		}  // if
		
		LOGGER.debug("No match user found");
		
		throw new UsernameNotFoundException("No match user found");
	}  // UserDetails loadUserByUsername

}  // class UserProvider
