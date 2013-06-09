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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;

import com.econcept.entities.Account;

/**
 * 
 * @author Kai - Ting (Danil) Ko
 * <p>AccountDAO Interface to provide database access methods for account entity</p>
 * 
 */

public interface AccountDAO
{
	/**
	 * Create account and inputed into persistence storage
	 * @param pAccount The account to be added into PersistenceStorage
	 * @Exception Exception when insert account into persistence storage
	 */
	public void addAccount(String pAccountID, String pFirstName, String pLastName, String pAccountPassword, String pEmail,int mStatus) throws Exception;
	
	/**
	 * Retrieve all accounts within persistence storage
	 * @return Accounts objects within persistence storage or null if there is nothing
	 * @throws ClassCastException If object returns from persistence storage is not account object, this Exception will be thrown
	 */
	public List <Account> getAllAccount() throws ClassCastException;
	
	/**
	 * Retrieve account with inputed account id and account password
	 * @return Accounts object within persistence storage or null if there is nothing
	 * @throws ClassCastException If object returns from persistence storage is not account object, this Exception will be thrown
	 */
	public Account getAccountByAccountIDAndAccountPassword(String pAccountID, String pAccountPassword) throws ClassCastException;
	
	/**
	 * Check whether account with inputed parameters is exist and is confirmed
	 * @param pAccountID The account id for the account to be confirmed
	 * @param String pAccountPassword The account password
	 * @return True if the account is exist and is confirmed or false if otherwise
	 * @throws ClassCastException If object returns from persistence storage is not account object, this Exception will be thrown
	 */
	public boolean getConfirmedAccount(String pAccountID, String pAccountPassword) throws ClassCastException;

	
	/**
	 * Delete account that has inputed id within persistence storage
	 * @param pAccountID The account id for the account to be deleted from persistence storage
	 */
	public void deleteAccountByAccountID(String pAccountID);
	
	/**
	 * Set the inputed entity manager as the entity manager to perform the operation
	 * @param pEntityManager The entity manager to perform the operation
	 */
	public void setEntityManager(EntityManager pEntityManager);
	
	/**
	 * Get the inputed entity manager as the entity manager to perform the operation
	 * @return EntityManager The entity manager to perform the operation
	 */
	public EntityManager getEntityManager();
}  // class AccountDAO
