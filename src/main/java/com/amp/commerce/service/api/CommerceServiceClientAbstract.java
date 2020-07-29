/**
 * 
 */
package com.amp.commerce.service.api;

import java.util.HashMap;

/**
 * @author MVEKSLER
 *
 */
public abstract class CommerceServiceClientAbstract implements CommerceServiceClientI 
{
	public  String itemSearchXml(HashMap<String, String> params) { return "default";}
	
	public  String itemLookupXml(HashMap<String, String> params) { return "default";}
	
	public  String similarityXml(HashMap<String, String> params) { return "default";}
	
	public  String browseNodeLookupXml(HashMap<String, String> params) { return "default";}
}
