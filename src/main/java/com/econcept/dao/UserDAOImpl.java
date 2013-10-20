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

package com.econcept.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.econcept.entity.User;

/**
 * 
 * @author Kai - Ting (Danil) Ko <h1>AccountDAOImpl</h1>
 *         <p>
 *         Implements AccountDAO Interface to provide database access methods
 *         for account entity
 *         </p>
 * 
 */

@Repository
public class UserDAOImpl implements UserDAO 
{
	private final static Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
	
	@PersistenceContext
	private EntityManager mEntityManager;
	
	@Override
	@Transactional(rollbackFor = Throwable.class)
	public User addUser(User pUser) 
	{
		if(pUser != null)
		{
			return null;
		}  // if
		
		mEntityManager.persist(pUser);
		
		return pUser;
	} // void addUser

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void deleteUser(User pUser) 
	{
		mEntityManager.remove(pUser);
	} // void deleteUser



	@Override
	public void updateUser(User pUser) throws ClassCastException {
		if(pUser != null)
		{
			return;
		}  // if
		
		mEntityManager.persist(pUser);
	}  // void updateUser

	@Override
	public List<User> getUsers(User pUser) throws ClassCastException {
		try
		{
		// Create query to find account by account password and  account id
		// Create JPQL Query to find all accounts with account id
		String lBasedQuery = "SELECT user_object FROM User user_object ";
		
		Query lQuery = null;
		
		if(pUser != null)
		{
			String lParameter = pUser.getUserName();
			if(lParameter != null)
			{
				lBasedQuery = lBasedQuery + "WHERE user_object.user_name LIKE :pUserName ";
				lQuery = mEntityManager.
						createQuery(lBasedQuery);
				lQuery = lQuery.setParameter("pUserName", lParameter);
			}  // if
			else if(pUser.getUserID() != null)
			{
				lParameter = pUser.getUserID();
				if(lParameter != null)
				{
					lBasedQuery = lBasedQuery +  "AND user_object.user_id LIKE :pUserID";
					lQuery = mEntityManager.
						createQuery(lBasedQuery);
					lQuery = lQuery.setParameter("pUserID", lParameter);
				}  // if
			}  // else if
		}  // if
		else
		{
			lQuery = mEntityManager.createQuery(lBasedQuery);
		}  // else
			
		List<?> lQueryList = lQuery.getResultList();

		// If query size is 0, return null to indicate no account
		if (lQueryList.size() == 0) {
			return null;
		} // if
		
		// Create new list to store account
		List<User> lLists = new ArrayList<User>(0);

		// Check items in list and cast to account only if it is an instance of
		// account object
		// Throw exception if there is an cast error
		for (Object lObject : lQueryList) {
			if (lObject instanceof User) {
				lLists.add((User) lObject);
			} // if
			else {
				throw new ClassCastException();
			} // else
		} // for
		
		return lLists;
		}
		catch(Exception pException)
		{
			LOGGER.debug(pException.toString());
			throw pException;
		}  // catch
	}  // List<User> getUsers
} // class AccountDAOImpl
