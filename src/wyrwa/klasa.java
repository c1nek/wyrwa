package wyrwa;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;  
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

//import org.apache.commons.*;


public class klasa {

	static String[] lines;
	
	 public static void main(String[] args) throws IOException   
	    {  
	        ReadFile rf = new ReadFile();  
	        String inputPath = "G:/wyrwa/input.txt";
	        PrintWriter writer = null;
	        String countryName = null;
	        try {
				writer = new PrintWriter(new FileWriter("G:/wyrwa/output.txt"));
			} catch (FileNotFoundException | UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        
	          
	        try  
	        {  
	           lines = rf.readLines(inputPath);  
	          
	            for (String line : lines)   
	            {  
	                System.out.println(line);  
	            }  
	        }  
	        catch(IOException e)  
	        {  
	        	
	        }  
	        
	        for(int i = 0; i< lines.length; i++){

				writer.println(getCountryName(lines[i]) +"\t" + getNeigh(getCountryCode(lines[i])));
				
	        }
	        writer.close();
	    }  
	
	 public static String getNeigh(String countryCode){
		 String result = null;
		 String queryResult = null;
		 String Qureyurl = "http://api.geonames.org/neighboursJSON?country=";
		String queryuser = "&formatted=true&username=c_inek";	 
		String url = Qureyurl + countryCode + queryuser;
		System.out.println(url); 
		queryResult = Query(url);
		result = ParseJSONA(queryResult);
		
		
		return result;
			
	 }
	 
	 private static String Query(String q) {

	        String qResult = null;

	        HttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(q);

	        try {
	            HttpEntity httpEntity = httpClient.execute(httpGet).getEntity();

	            if (httpEntity != null) {
	                InputStream inputStream = httpEntity.getContent();
	                Reader in = new InputStreamReader(inputStream);
	                BufferedReader bufferedreader = new BufferedReader(in);
	                StringBuilder stringBuilder = new StringBuilder();

	                String stringReadLine = null;

	                while ((stringReadLine = bufferedreader.readLine()) != null) {
	                    stringBuilder.append(stringReadLine + "\n");
	                }

	                qResult = stringBuilder.toString();

	            }

	        } catch (ClientProtocolException e) {

	            e.printStackTrace();
	        } catch (IOException e) {

	            e.printStackTrace();
	        }
	        return qResult;
	    }

	 private static String ParseJSONA(String json){
	 
		 String result = null;
		 String neigh = null;
		 json.trim();
	        try
	        {
	        JSONObject JsonObject = new JSONObject(json);
	        
	        	int results = JsonObject.getInt("totalResultsCount");
	     	        	System.out.println(results); 
	        	
	        	if(results == 0){
	        		return null;
	        	}
	        	else{
	        		
	        		JSONArray mJsonArray = JsonObject.getJSONArray("geonames");
	        		JSONObject mJsonObject = new JSONObject();
	        		for(int i =0; i< mJsonArray.length(); i++){
	        			mJsonObject = mJsonArray.getJSONObject(i);
	        		    neigh = mJsonObject.getString("name");
	        		    if(i==0){
	        		    	result = neigh;
	        		    }
	        		    else{
	        			result = result+"\t"+neigh;    
	        		    }
	        		}
	        	}
	        }
	        catch (JSONException e)
	        {
	        e.printStackTrace();
	        }
	        System.out.println(result); 
	        return result;
	    }
 
	 public static String getCountryCode(String s){
		 String CountryCode = s.substring(0, 2);
		 return CountryCode;
	 }
	 
	 public static String getCountryName(String s){
		 String CountryCode = s.substring(3, s.length());
		 return CountryCode;
	 }
}
	
