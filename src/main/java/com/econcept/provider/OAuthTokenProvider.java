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


package com.econcept.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.rs.security.oauth2.common.AccessTokenRegistration;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.OAuthPermission;
import org.apache.cxf.rs.security.oauth2.common.ServerAccessToken;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.grants.code.AuthorizationCodeDataProvider;
import org.apache.cxf.rs.security.oauth2.grants.code.AuthorizationCodeRegistration;
import org.apache.cxf.rs.security.oauth2.grants.code.ServerAuthorizationCodeGrant;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;
import org.apache.cxf.rs.security.oauth2.tokens.bearer.BearerAccessToken;
import org.apache.openjpa.util.UnsupportedException;

public class OAuthTokenProvider implements AuthorizationCodeDataProvider
{
	private Client mClient;
	private ServerAuthorizationCodeGrant mGrant;
	private ServerAccessToken mToken;
	

	
	@Override
	public List<OAuthPermission> convertScopeToPermissions(Client pClient,
			List<String> pScopes) 
	{
		List <OAuthPermission> lPermissions = new ArrayList<OAuthPermission>();
		
		for(String lScope : pScopes)
		{
			OAuthPermission lPermission = new OAuthPermission(lScope, null);
			lPermissions.add(lPermission);
		}  // for
		
		return lPermissions;
	}  // List<OAuthPermission> convertScopeToPermissions

	@Override
	public ServerAccessToken createAccessToken(AccessTokenRegistration pRegistration)
			throws OAuthServiceException 
	{
		mClient = pRegistration.getClient();
		mToken = new BearerAccessToken(mClient, 3600L);
		
		mToken.setScopes(convertScopeToPermissions(mClient, pRegistration.getApprovedScope()));
		mToken.setSubject(pRegistration.getSubject());
		mToken.setGrantType(pRegistration.getGrantType());
		
		return mToken;
	}  // ServerAccessToken createAccessToken

	@Override
	public ServerAccessToken getAccessToken(String pTokenID)
			throws OAuthServiceException 
	{
		if (mToken != null) 
		{
			if (mToken.getTokenKey().equals(pTokenID))
			{
				return mToken;
			} // if
		} // if
		
		return null;
	}  // ServerAccessToken getAccessToken
	
	@Override
	public Client getClient(String pClientID) throws OAuthServiceException 
	{
		if (mClient != null) 
		{
			if (mClient.getClientId().equals(pClientID))
			{
				return mClient;
			} // if
		} // if
		
		return null;
	}  // Client getClient

	@Override
	public ServerAccessToken getPreauthorizedToken(Client pClient, List<String> pRequestedScopes, UserSubject pUserSubject, String pGrantType) 
			throws OAuthServiceException 
	{
		throw new UnsupportedException();
	}  // ServerAccessToken getPreauthorizedToken

	@Override
	public ServerAccessToken refreshAccessToken(Client pClient, String pRefreshToken, List<String> pRequestedScopes) throws OAuthServiceException 
	{
		throw new UnsupportedException();
	}  // ServerAccessToken refreshAccessToken

	@Override
	public void removeAccessToken(ServerAccessToken pToken)
			throws OAuthServiceException 
	{
		pToken = null;
	}  // void removeAccessToken

	@Override
	public ServerAuthorizationCodeGrant createCodeGrant(
			AuthorizationCodeRegistration pRegistration) throws OAuthServiceException 
	{
		mGrant = new ServerAuthorizationCodeGrant(mClient, 7200L);
		mGrant.setSubject(pRegistration.getSubject());
		mGrant.setRedirectUri(pRegistration.getRedirectUri());
		mGrant.setApprovedScopes(pRegistration.getApprovedScope());
		
		return mGrant;
	}  // Server ServerAuthorizationCodeGrant createCodeGrant

	@Override
	public ServerAuthorizationCodeGrant removeCodeGrant(String pCode)
			throws OAuthServiceException 
	{
		mGrant = null;
		return null;
	}  // ServerAuthorizationCodeGrant removeCodeGrant

}  // class OAuthTokenProvider
