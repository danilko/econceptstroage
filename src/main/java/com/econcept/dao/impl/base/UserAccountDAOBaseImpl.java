package com.econcept.dao.impl.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.econcept.dao.UserAccountDAO;
import com.econcept.entity.UserAccount;

public class UserAccountDAOBaseImpl implements UserAccountDAO 
{
	private final static Logger LOGGER = LoggerFactory.getLogger(UserAccountDAOBaseImpl.class);

	@PersistenceContext
	private EntityManager mEntityManager;

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public UserAccount addUser(UserAccount pUser) {
		if (pUser != null) {
			return null;
		} // if

		mEntityManager.persist(pUser);

		return pUser;
	} // void addUser

	@Override
	@Transactional(rollbackFor = Throwable.class)
	public void deleteUser(UserAccount pUserAccount) {
		mEntityManager.remove(pUserAccount);
	} // void deleteUser

	@Override
	public void updateUser(UserAccount pUserAccount) throws ClassCastException {
		if (pUserAccount != null) {
			return;
		} // if

		mEntityManager.persist(pUserAccount);
	} // void updateUser
	
	@Override
	public List<UserAccount> getUserByUserID(String pUserAccountID)
{
		LOGGER.debug("USER_ID PASSED IN" + pUserAccountID);
		
		try {
			// Create query to find account by account password and account id
			// Create JPQL Query to find all accounts with account id
			String lBasedQuery = "SELECT user_object FROM UserAccount user_object WHERE user_object.user_id LIKE :pUserID";

			Query lQuery = mEntityManager.createQuery(lBasedQuery);
		
			if (pUserAccountID != null) 
			{
				lQuery = lQuery.setParameter("pUserID", pUserAccountID);
			} // if
			else
			{
				lQuery = mEntityManager.createQuery(lBasedQuery);
				lQuery = lQuery.setParameter("pUserID", "pUserID");
			}  // else

			List<?> lQueryList = lQuery.getResultList();

			// If query size is 0, return null to indicate no account
			if (lQueryList.size() == 0) {
				LOGGER.debug("NOT FOUND ANY ACCOUNT....");
				return null;
			} // if

			
			
			// Create new list to store account
			List<UserAccount> lLists = new ArrayList<UserAccount>(0);

			// Check items in list and cast to account only if it is an instance
			// of
			// account object
			// Throw exception if there is an cast error
			for (Object lObject : lQueryList) {
				if (lObject instanceof UserAccount) {
					lLists.add((UserAccount) lObject);
				} // if
				else {
					throw new ClassCastException();
				} // else
			} // for

			return lLists;
		} catch (Exception pException) {
			LOGGER.debug(pException.toString());
			throw pException;
		} // catch
}

}
