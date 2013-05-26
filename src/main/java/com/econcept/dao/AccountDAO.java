package com.econcept.dao;

import java.util.List;

import com.econcept.entities.Account;

public interface AccountDAO
{
	public void addAccount(Account pAccount);
	public List <Account> getAccount();
	public void deleteAccount(String pID);
}  // class AccountDAO
