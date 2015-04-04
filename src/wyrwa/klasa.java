package wyrwa;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;  
import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;  
import java.util.List;

import org.json.JSONArray;
import org.json.*;

import org.apache.commons.*;


public class klasa {

	static String[] lines;
	
	 public static void main(String[] args)   
	    {  
	        ReadFile rf = new ReadFile();  
	        String inputPath = "G:/wyrwa/input.txt";
	        PrintWriter writer = null;
	        try {
				writer = new PrintWriter("G:/wyrwa/output.txt", "UTF-8");
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
	        
	        for(int i =5; i< lines.length; i++){
	        	
				writer.println(getNeigh(getCountryCode(lines[i])));
	        }
	    }  
	 
	 
	 public static String getNeigh(String countryCode){
		 String result = null;
		 String queryResult = null;
		 String Qureyurl = "http://api.geonames.org/neighboursJSON?country=";
		String queryuser = "&username=c_inek";	 
		String url = Qureyurl + countryCode + queryuser;
		System.out.println(url); 
		queryResult = Query(url);
		ParseJSONA(queryResult);
		
		return result;
			
	 }
	 
	 public static String Query(String q) {

	        String qResult = null;
	        
	        try {
	        	URL url = new URL(q);
		        URLConnection conn = url.openConnection();
		        InputStream in = conn.getInputStream();
		        qResult = getStringFromInputStream(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        System.out.println(qResult);
	        return qResult;
	    }

	 private static String ParseJSONA(String json){
	 
		 String result = null;
		 
		 //long results = 0;
	        try
	        {
	        JSONObject JsonObject = new JSONObject(json);
	        	int results = JsonObject.getInt("totalResultsCount");
	        	
	        	if(results == 0){
	        		return null;
	        	}
	        	else{
	        		String neigh = null;
	        		JSONArray mJsonArray = new JSONArray("geonames");
	        		JSONObject mJsonObject = new JSONObject();
	        		for(int i =0; i< mJsonArray.length(); i++){
	        			mJsonObject = mJsonArray.getJSONObject(i);
	        		    neigh = mJsonObject.getString("name");
	        			result = result+"\t"+neigh;
	        			
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
	 
	 private static String getStringFromInputStream(InputStream is) {
		 
			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();
	 
			String line;
			try {
	 
				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
	 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	 
			return sb.toString();
	 
		}
}
