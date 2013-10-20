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

import com.econcept.entity.User;

/**
 * 
 * @author Kai - Ting (Danil) Ko
 * <p>AccountDAO Interface to provide database access methods for account entity</p>
 * 
 */

public interface UserDAO
{
	/**
	 * Create user and inputed into persistence storage
	 * @param pUser User object to be removed
	 * @return User the object user added into persistence storage or null if fail
	 * @Exception Exception when insert account into persistence storage
	 */
	public User addUser(User pUser) throws Exception;
	
	/**
	 * Retrieve all users within persistence storage according to input information
	 * @param pUser User object to represent object to search for
	 * @return Accounts objects within persistence storage or null if there is nothing
	 * @throws ClassCastException If object returns from persistence storage is not account object, this Exception will be thrown
	 */
	public List <User> getUsers(User pUser) throws ClassCastException;
	
	/**
	 * Update user with inputed information
	 * @param pAccount User object within persistence storage that contains modified information
	 * @return User the object user is updated into persistence storage or null if fail
	 * @throws ClassCastException If object returns from persistence storage is not account object, this Exception will be thrown
	 */
	public void updateUser(User pUser) throws ClassCastException;
	
	/**
	 * Delete user that has inputed id within persistence storage
	 * @param pAccountID The account id for the account to be deleted from persistence storage
	 */
	public void deleteUser(User pUser);
}  // class UserDAO
