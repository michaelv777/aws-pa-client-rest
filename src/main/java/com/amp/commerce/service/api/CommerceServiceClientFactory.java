/**
 * 
 */
package com.amp.commerce.service.api;

/**
 * @author MVEKSLER
 *
 */
public class CommerceServiceClientFactory 
{
	public static synchronized CommerceServiceClientBase buildCommerceServiceClient(
									Class<? extends CommerceServiceClientBase> clazz)
	{
		
		try
		{
			if ( null == clazz )
			{
				return new CommerceServiceClientBase();
			}
			else
			{
				return (CommerceServiceClientBase)clazz.newInstance();
			}
		}
		catch( InstantiationException ie )
		{
			return new CommerceServiceClientBase();
		}
		catch( IllegalAccessException ile )
		{
			return new CommerceServiceClientBase();
		}
		catch( Exception e )
		{
			return new CommerceServiceClientBase();
		}
	}
}
