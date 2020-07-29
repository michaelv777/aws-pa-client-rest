/**
 * 
 */
package com.amp.commerce.service.api;

import java.util.HashMap;

/**
 * @author MVEKSLER
 *
 */
public interface CommerceServiceClientI 
{
	public String itemSearchXml(HashMap<String, String> params);
	
	//public List<Items> itemSearchList(HashMap<String, String> params);
	
	public String itemLookupXml(HashMap<String, String> params);
	
	//public List<Items> itemLookupList(HashMap<String, String> params);
	
	public String similarityXml(HashMap<String, String> params);
	
	//public List<Items> similarityList(HashMap<String, String> params);
	
	public String browseNodeLookupXml(HashMap<String, String> params);
	
	//public List<BrowseNodes> browseNodeLookupList(HashMap<String, String> params);
}
