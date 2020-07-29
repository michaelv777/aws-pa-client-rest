package com.amp.commerce.service.api;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommerceServiceClientBase extends CommerceServiceClientAbstract 
{
	private static Logger LOG = 
			LoggerFactory.getLogger(CommerceServiceClientBase.class);
	
	public CommerceServiceClientBase()
	{
		try
		{
			
		}
		catch( Exception e )
		{
			
		}
	}
	
	protected HashMap<String, String> 
		cSystemConfiguration = new  HashMap<String, String>();

	protected HashMap<String, String> 
		cWorkerConfiguration = new  HashMap<String, String>();

	protected boolean lcRes = true;
	
	public HashMap<String, String> getcSystemConfiguration() {
		return cSystemConfiguration;
	}

	public void setcSystemConfiguration(HashMap<String, String> cSystemConfiguration) {
		this.cSystemConfiguration = cSystemConfiguration;
	}

	public HashMap<String, String> getcWorkerConfiguration() {
		return cWorkerConfiguration;
	}

	public void setcWorkerConfiguration(HashMap<String, String> cWorkerConfiguration) {
		this.cWorkerConfiguration = cWorkerConfiguration;
	}

	
	public boolean isLcRes() {
		return lcRes;
	}

	public void setLcRes(boolean lcRes) {
		this.lcRes = lcRes;
	}

	protected boolean setSystemProperties() 
	{
		String methodName = "";
		
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        methodName = ste.getMethodName();
	        
	        return cRes;
		}
		catch( Exception e)
		{
			LOG.error( methodName + ":" + e.getMessage(), e );
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
	
	//---
	protected boolean setWorkerProperties(Class<? extends CommerceServiceClientBase> clazz)
	{
		@SuppressWarnings("unused")
		String cMethodName = "";
		
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        this.setWorkerProperties();
	        
	        //--set for this class
			Field[] cFields = clazz.getDeclaredFields();
			
			for( int jondex = 0; jondex < cFields.length; ++jondex )
			{
				Field cField = (Field)cFields[jondex];
				
				String cSourceConfigKey = cField.getName();
						 
				if ( this.cWorkerConfiguration.containsKey(cSourceConfigKey))
				{
					String cSourceConfigValue = 
							this.cWorkerConfiguration.get(cSourceConfigKey);
					
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
						  cField.set(this, cSourceConfigValue);
					  }
					  else if ( type.equals(boolean.class ))
					  {
						  boolean cBoolSet = Boolean.parseBoolean(cSourceConfigValue);
						  cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
						  int cIntSet = Integer.parseInt(cSourceConfigValue);
						  cField.setInt(this, cIntSet);	
					  }
					  else if ( type.equals(long.class ))
					  {
						  long cIntSet = Long.parseLong(cSourceConfigValue);
						  cField.setLong(this, cIntSet);	
					  }
				}
			}
			
			return cRes;
		}
		catch( IllegalAccessException e )
		{
			LOG.error( cMethodName + ":" + e.getMessage(), e );
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
		catch( Exception e)
		{
			LOG.error( cMethodName + ":" + e.getMessage(), e );
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}

	//---
	protected boolean setWorkerProperties()
	{
		@SuppressWarnings("unused")
		String cMethodName = "";
		
		boolean cRes = true;
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        //--set for this class
			Field[] cFields = this.getClass().getDeclaredFields();
			
			for( int jondex = 0; jondex < cFields.length; ++jondex )
			{
				Field cField = (Field)cFields[jondex];
				
				String cSourceConfigKey = cField.getName();
						 
				if ( this.cWorkerConfiguration.containsKey(cSourceConfigKey))
				{
					String cSourceConfigValue = 
							this.cWorkerConfiguration.get(cSourceConfigKey);
					
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
						  cField.set(this, cSourceConfigValue);
					  }
					  else if ( type.equals(boolean.class ))
					  {
						  boolean cBoolSet = Boolean.parseBoolean(cSourceConfigValue);
						  cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
						  int cIntSet = Integer.parseInt(cSourceConfigValue);
						  cField.setInt(this, cIntSet);	
					  }
					  else if ( type.equals(long.class ))
					  {
						  long cIntSet = Long.parseLong(cSourceConfigValue);
						  cField.setLong(this, cIntSet);	
					  }
				}
			}
			
			return cRes;
		}
		catch( IllegalAccessException e )
		{
			LOG.error( cMethodName + ":" + e.getMessage(), e );
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
		catch( Exception e)
		{
			LOG.error( cMethodName + ":" + e.getMessage(), e );
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
	
	protected boolean initClassVariables()
	{
		boolean cRes = true;
		
		String cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        
	        cMethodName = ste.getMethodName();
	        
	        this.cSystemConfiguration = new  HashMap<String, String>();

	        this.cWorkerConfiguration = new  HashMap<String, String>();

	        this.setLcRes(cRes);
		
	        return cRes;
		}
		catch( Exception e)
		{
			LOG.error( cMethodName + ":" + e.getMessage(), e );
			
			this.setLcRes(cRes = false);
			
			return cRes;
		}
	}
}
