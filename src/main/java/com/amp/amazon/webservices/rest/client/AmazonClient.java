package com.amp.amazon.webservices.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.bind.JAXB;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.amp.amazon.webservices.rest.BrowseNodeLookupResponse;
import com.amp.amazon.webservices.rest.ItemLookupResponse;
import com.amp.amazon.webservices.rest.ItemSearchResponse;
import com.amp.amazon.webservices.rest.SimilarityLookupResponse;


public class AmazonClient 
{
	public static final String SERVICE = "AWSECommerceService";
	public static final String VERSION = "2013-08-01";//"2011-08-01";
	
	public static final String PARAM_ENDPOINT   = "Endpoint";
	public static final String PARAM_ACCESS_SECRET_KEY_ID  = "AWSAccessSecretKey";
	public static final String PARAM_ACCESS_KEY_ID  = "AWSAccessKeyId";
	public static final String PARAM_ASSOCIATE_TAG  = "AssociateTag";
	public static final String PARAM_VERSION        = "Version";
	public static final String PARAM_SERVICE        = "Service";
	public static final String PARAM_SEARCH_INDEX   = "SearchIndex";
	public static final String PARAM_RESPONSE_GROUP = "ResponseGroup";
	public static final String PARAM_KEYWORDS 		= "Keywords";
	public static final String PARAM_SORT 			= "Sort";
	public static final String PARAM_ITEM_ID 		= "ItemId";
	public static final String PARAM_MERCHANT_ID    = "MerchantId";
	public static final String PARAM_BROWSE_NODE    = "BrowseNode";
	public static final String PARAM_BROWSE_NODE_ID = "BrowseNodeId";
	
	public static final String PARAM_VALUE_MERCHANT_ID = "All";
	
	public static final String OPERATION_ITEM_SEARCH = "ItemSearch";
	public static final String OPERATION_ITEM_LOOKUP = "ItemLookup";
	public static final String OPERATION_BROWSE_NODE_LOOKUP = "BrowseNodeLookup";
	public static final String OPERATION_SIMILARITY_LOOKUP = "SimilarityLookup";
	
	private HttpClient client = new HttpClient();
	{
		HttpConnectionParams params = client.getHttpConnectionManager().getParams();
		params.setConnectionTimeout(50000);
		params.setSoTimeout(50000);
		
	}
	
	private String serviceString = PARAM_SERVICE + "=" + SERVICE + "&" + PARAM_VERSION + "=" + VERSION + "&";
	private AmazonClient self = this;
	
	//---------
	public abstract class Op<T> 
	{
		public static final String PARAM_OPERATION = "Operation";
		
		public String op;

		public Class<T> responseType;
		
		protected Op(String op, Class<T> responseType) 
		{
			super();
			
			this.op = op;
			
			this.responseType = responseType;
		}

		public T execute(Map<String, String> query) 
		{
			query.put(PARAM_OPERATION, op);
			
			return self.getObject(query, responseType);
		}
		
		public T execute(String query) {
			return self.getObject(PARAM_OPERATION+"=" + op + "&" + query, responseType);
		}
	}
	//--------------------------------
	private Op<ItemLookupResponse> itemLookup = op(OPERATION_ITEM_LOOKUP, ItemLookupResponse.class);
	
	private Op<ItemSearchResponse> itemSearch = op(OPERATION_ITEM_SEARCH, ItemSearchResponse.class);
	
	private Op<SimilarityLookupResponse> itemSimilarity = op(OPERATION_ITEM_SEARCH, SimilarityLookupResponse.class);
	
	private Op<BrowseNodeLookupResponse> browseNodeLookup = op(OPERATION_BROWSE_NODE_LOOKUP, BrowseNodeLookupResponse.class);
	
	
	private <T> Op<T> op(String op, Class<T> c) 
	{
		return new Op<T>(op, c) {};
	}
	//--------------------------------

    private SignedRequestsHelper helper;
    
	private String associateTag;    	
    /*
     * Use one of the following end-points, according to the region you are
     * interested in:
     * 
     *      US: ecs.amazonaws.com 
     *      CA: ecs.amazonaws.ca 
     *      UK: ecs.amazonaws.co.uk 
     *      DE: ecs.amazonaws.de 
     *      FR: ecs.amazonaws.fr 
     *      JP: ecs.amazonaws.jp
     * 
     */
    //public static String ENDPOINT = "ecs.amazonaws.com";
	//public static String ENDPOINT = "ecs.amazonaws.ca";
	//public static String ENDPOINT = "webservices.amazon.ca";
	public static String ENDPOINT = "webservices.amazon.com";
	//--------------------------------
    public AmazonClient(String accessKeyId, String secretKey, String associateTag) 
    {
        /*
         * Set up the signed requests helper 
         */
        try 
        {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, accessKeyId, secretKey);
        } 
        catch (Exception e) 
        {
        	throw new RuntimeException(e);
        }
        this.associateTag = associateTag;
	}
    //--------------------------------
    public AmazonClient(String accessKeyId, String secretKey, String associateTag, String endpoint) 
    {
        /*
         * Set up the signed requests helper 
         */
        try 
        {
        	if ( endpoint != null && !endpoint.trim().equals(""))
        	{
        		AmazonClient.ENDPOINT = endpoint;
        	}
        	
            helper = SignedRequestsHelper.getInstance(ENDPOINT, accessKeyId, secretKey);
        } 
        catch (Exception e) 
        {
        	throw new RuntimeException(e);
        }
        this.associateTag = associateTag;
	}
    //--------------------------------
    public AmazonResponse get(String query) 
    {
    	String r;
    	
		try 
		{
			r = IOUtils.toString(getResourceSigned(query));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
    	return new AmazonResponse(r);
    }
    //--------------------------------
    public AmazonResponse get(Map<String,String> query) 
    {
    	String r;
    	
		try 
		{
			r = IOUtils.toString(getResourceSigned(query));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
    	return new AmazonResponse(r);
    }
    //--------------------------------
	public <T> T getObject(String query, Class<T> c) 
	{
		try 
        {
	    	InputStream is = getResourceSigned(query);
	    	
	    	return JAXB.unmarshal(is, c);
        }
		catch (Exception e) 
        {
			return null;
        }
    }
	
	public <T> T getObject(Map<String,String> params, Class<T> c) 
	{
		try 
        {
			InputStream is = getResourceSigned(params);
			
			return JAXB.unmarshal(is, c);
        }
		catch (Exception e) 
        {
			e.printStackTrace();
			
			return null;
        }
    }
	
	//--------------------------------
    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    public Document getXml(InputStream is) 
    {
        try 
        {
            SAXReader reader = new SAXReader();
            Document document = reader.read(is);
            return document;
            
        } 
        catch (Exception e) 
        {
            throw new RuntimeException(e);
        }
        //return title;
    }
    //--------------------------------
    public Document getXml(Map<String,String> params) 
    {
    	 try
         {
	    	InputStream is = getResourceSigned(params);
	    	
	    	return getXml(is);
         }
    	 catch( Exception e)
         {
         	return null;
         }
        //return title;
    }
    
    public Document getXml(String query) 
    {
        try
        {
        	InputStream is = getResourceSigned(query);
    	
        	return getXml(is);
        }
        catch( Exception e)
        {
        	return null;
        }
        //return title;
    }
    //--------------------------------
	public static String documentToString(Document document) 
	{
		StringWriter sw = new StringWriter();
		
		XMLWriter writer = new XMLWriter(sw, OutputFormat.createPrettyPrint());
		
		try 
		{
			writer.write(document);
		} 
		catch (IOException e) 
		{
			throw new RuntimeException(e);
		}
		
		return sw.toString();
	}
	//--------------------------------
    private InputStream getResourceSigned(String query) 
    {
    	String u = helper.sign(serviceString + PARAM_ASSOCIATE_TAG + "=" + this.associateTag + "&"+ query);
    	
    	return getResource(u);
    }
    //--------------------------------
    private InputStream getResourceSigned(Map<String, String> params) 
    {
    	params.put(PARAM_SERVICE, SERVICE);
    	params.put(PARAM_VERSION, VERSION);
    	params.put(PARAM_ASSOCIATE_TAG, this.associateTag);
    	String u = helper.sign(params);
    	return getResource(u);
    }        
    //--------------------------------
	private InputStream getResource(String u) 
	{
		try 
		{    			
			GetMethod method = new GetMethod(u);
			
			client.executeMethod(method);
			
			InputStream is = method.getResponseBodyAsStream();
			
			return is;
		} 
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	}    

	
	public Op<ItemLookupResponse> lookup() 
	{
		return itemLookup;
	}

	public Op<ItemSearchResponse> search() 
	{
		return itemSearch;
	}

	public Op<SimilarityLookupResponse> similarity() 
	{
		return itemSimilarity;
	}
	
	public Op<BrowseNodeLookupResponse> browseNodeLookup() 
	{
		return browseNodeLookup;
	}

	public static class AmazonResponse 
	{
		private String responseBody;
			
	    public AmazonResponse(String responseBody) 
	    {
			super();
			
			this.responseBody = responseBody;
		}

		public Document getXml() 
		{
	        try 
	        {
	            SAXReader reader = new SAXReader();
	            
	            Document document = reader.read(new StringReader(responseBody));
	            
	            return document;
	            
	        } 
	        catch (Exception e) 
	        {
	            throw new RuntimeException(e);
	        }
	    }
		
		public String getPrettyXml() 
		{
			return documentToString(getXml());
		}
		
		public <T> T getObject(Class<T> c) 
		{
	    	return JAXB.unmarshal(new StringReader(responseBody), c);
	    }

		
		public String getResponseBody() 
		{
			return responseBody;
		}
		
	}

}