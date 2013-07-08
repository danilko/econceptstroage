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
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.econcept.entities.Account;

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
public class AccountDAOImpl implements AccountDAO 
{
	private EntityManagerFactory mEntityManagerFactory = Persistence.createEntityManagerFactory("account-unit");
	
	private EntityManager mEntityManager = mEntityManagerFactory.createEntityManager();

	@PersistenceContext
	public void setEntityManager(EntityManager pEntityManager)
	{
		mEntityManager = pEntityManager;
	}  // void setEntityManager
	
	@Transactional(rollbackFor = Throwable.class)
	public void addAccount(Account pAccount) {
		EntityTransaction lEntityTransaction = mEntityManager.getTransaction();
		lEntityTransaction.begin();
		// Persist the object in persistence context
		mEntityManager.persist(pAccount);
		// Commit and apply the change to the database
		lEntityTransaction.commit();
	} // void addAccount
	
	@Override
	public List<Account> getAllAccount() throws ClassCastException {
		// Create Java Persistence Query Language (JPQL) Query to find all accounts
		Query lQuery = mEntityManager.createQuery("SELECT ACCOUNTOBJECT FROM Account ACCOUNTOBJECT");

		List<?> lQueryList = lQuery.getResultList();

		// If query size is 0, return null to indicate no account
		if (lQueryList.size() == 0) {
			return null;
		} // if

		// Create new list to store account
		List<Account> lLists = new ArrayList<Account>();

		// Check items in list and cast to account only if it is an instance of
		// account object
		// Throw exception if there is an cast error
		for (Object lObject : lQueryList) {
			if (lObject instanceof Account) {
				lLists.add((Account) lObject);
			} // if
			else {
				throw new ClassCastException();
			} // else
		} // for

		return lLists;
	} // List<Account> getAllAccount

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void deleteAccountByAccountID(String pAccountID) 
	{
		// Create JPQL Query to find all accounts with account id
		Query lQuery = mEntityManager.
				createQuery("SELECT ACCOUNTOBJECT FROM Account ACCOUNTOBJECT WHERE ACCOUNTOBJECT.mAccountID LIKE :P_ACCOUNT_ID").setParameter("P_ACCOUNT_ID", pAccountID).setMaxResults(1);

		// Retrieve the first account
		Account lAccount = (Account) lQuery.getResultList().get(0);

		EntityTransaction lEntityTransaction = mEntityManager.getTransaction();
		lEntityTransaction.begin();
		// Persist the object in persistence context
		mEntityManager.remove(lAccount);
		// Commit and apply the change to the database
		lEntityTransaction.commit();
	} // void deleteAccount

	@Override
	public Account getAccountByAccountIDAndAccountPassword(String pAccountID,
			String pAccountPassword) throws ClassCastException {
		// Create query to find account by account password and  account id
		// Create JPQL Query to find all accounts with account id
		Query lQuery = mEntityManager.
				createQuery("SELECT ACCOUNTOBJECT FROM Account ACCOUNTOBJECT WHERE ACCOUNTOBJECT.mAccountID LIKE :P_ACCOUNT_ID AND ACCOUNTOBJECT.mAccountPassword LIKE :P_ACCOUNT_PASSWORD").setParameter("P_ACCOUNT_ID", pAccountID).setParameter("P_ACCOUNT_PASSWORD", pAccountPassword).setMaxResults(1);

		// Retrieve the first account
		List<?> lList = lQuery.getResultList();

		if(lList.size() != 0)
		{
			Object lObject = lList.get(0);
			
			if(lObject instanceof Account)
			{
				return (Account) lObject;
			}  // if
			else
			{
				throw new ClassCastException();
			}  // else
		}  // else
		
		return null;
	}  // Account getAccountByIDAndPassword

	@Transactional(rollbackFor = Throwable.class)
	@Override
	public void addAccount(String pAccountID, String pFirstName,
			String pLastName, String pAccountPassword, String pEmail,
			int pStatus) throws Exception {
		// Create an account object based on inputed parameters
		Account lAccount = new Account();
		lAccount.setAccountID(pAccountID);
		lAccount.setPassword(pAccountPassword);
		lAccount.setFirstName(pFirstName);
		lAccount.setEmail(pEmail);
		lAccount.setStatus(pStatus);
		
		EntityTransaction lEntityTransaction = mEntityManager.getTransaction();
		lEntityTransaction.begin();
		// Persist the object in persistence context
		mEntityManager.persist(lAccount);
		// Commit and apply the change to the database
		lEntityTransaction.commit();
	}  // void addAccount

	@Override
	public boolean getConfirmedAccount(String pAccountID,
			String pAccountPassword) throws ClassCastException {
		// Retrieve the account
		Account lAccount = getAccountByAccountIDAndAccountPassword(pAccountID, pAccountPassword);

		if(lAccount != null)
		{
			if(lAccount.getStatus() == 1)
			{
				return true;
			}  // if
		}  // if
		
		return false;
	}  // boolean getConfirmedAccount
} // class AccountDAOImpl
