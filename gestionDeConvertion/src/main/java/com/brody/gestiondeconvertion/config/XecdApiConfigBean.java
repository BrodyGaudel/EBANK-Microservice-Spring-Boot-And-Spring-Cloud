package com.brody.gestiondeconvertion.config;

import com.google.gson.Gson;

public class XecdApiConfigBean {
	
	private String accountId;
	private String apiKey;
	private String serverPrefix;
	private Integer connectTimeout;
	
	
	

	public XecdApiConfigBean(String accountId, String apiKey, String serverPrefix, Integer connectTimeout) {
		
		this.accountId = accountId;
		this.apiKey = apiKey;
		this.serverPrefix = serverPrefix;
		this.connectTimeout = connectTimeout;
	}
	
	

	public XecdApiConfigBean() {
		super();
	}



	public String getAccountId()
	{
		return accountId;
	}

	public void setAccountId(String accountId)
	{
		this.accountId = accountId;
	}

	public String getApiKey()
	{
		return apiKey;
	}

	public void setApiKey(String apiKey)
	{
		this.apiKey = apiKey;
	}

	public String getServerPrefix()
	{
		return serverPrefix;
	}

	public void setServerPrefix(String serverPrefix)
	{
		this.serverPrefix = serverPrefix;
	}

	@Override
	public String toString()
	{
		return new Gson().toJson(this);
	}

	public Integer getConnectTimeout()
	{
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout)
	{
		this.connectTimeout = connectTimeout;
	}

}
