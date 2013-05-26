package com.econcept.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.econcept.entities.Account;

@Repository
public class AccountDAOImpl implements AccountDAO, CrudRepository<Account, String> 
{
	@PersistenceContext
	private EntityManager lEntityManager;
	
	public void addAccount(Account pAccount) 
	{
		EntityTransaction lEntityTransaction = lEntityManager.getTransaction();
		lEntityTransaction.begin();
		lEntityManager.persist(pAccount);
		lEntityTransaction.commit();
	}  // void addAccount

	public List<Account> getAccount() 
	{
		Query lQuery = lEntityManager.createQuery("SELECT * FROM ACCOUNT;");
		List <Account> lLists = lQuery.getResultList();
			return lLists;
	}  // List<Account> getAccount

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Account arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Account> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Account> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Account> findAll(Iterable<String> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Account> S save(S pAccount) {
		EntityTransaction lEntityTransaction = lEntityManager.getTransaction();
		lEntityTransaction.begin();
		lEntityManager.persist(pAccount);
		lEntityTransaction.commit();
		
		return pAccount;
	}

	@Override
	public <S extends Account> Iterable<S> save(Iterable<S> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAccount(String pID) {
		// TODO Auto-generated method stub
		
	}
}
