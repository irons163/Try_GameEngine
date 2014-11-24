package com.example.try_gameengine.viewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class FileUtil {
	
	public static String readFileFromAssets(Context context, String fileName){
		 byte b[] = new byte[2000];
		 int len = 0;
		try {
			InputStream inputStream = context.getResources().getAssets().open(fileName);
			 
		          
		        int temp=0;         
		        while((temp=inputStream.read())!=-1){   
		            b[len]=(byte)temp;  
		            len++;  
		        }  
		        inputStream.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String s = new String(b,0,len);
		return s;
	}
	
	public static ImageInfo getImageInfo(String txt, String object, String part){
		int x = 0;
		int y = 0;
		int w = 0;
		int h = 0;
		int layer = 0;
		try {
			JSONObject jo= new JSONObject(txt);
			jo = jo.getJSONObject(object);
			jo = jo.getJSONObject(part);
			x = jo.getInt("x");
			y = jo.getInt("y");;
			w = jo.getInt("w");
			h = jo.getInt("h");
			layer = jo.getInt("layer");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ImageInfo(x, y, w, h, layer);
	}
	
	public static String readFileFromAssetsF(Context context, String fileName){
		 String result = "";
		try { 
             InputStreamReader inputReader = new InputStreamReader( context.getResources().getAssets().open(fileName) ); 
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            result="";
            while((line = bufReader.readLine()) != null){
            	if(!result.equals(""))
            		result += "\n";
            	result += line;
            }
            
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
		
		return result;
	}
	
//	Raw¡G
//	 
//	   public String getFromRaw(){ 
//	            try { 
//	                InputStreamReader inputReader = new InputStreamReader( getResources().openRawResource(R.raw.test1));
//	                BufferedReader bufReader = new BufferedReader(inputReader);
//	                String line="";
//	                String Result="";
//	                while((line = bufReader.readLine()) != null)
//	                    Result += line;
//	                return Result;
//	            } catch (Exception e) { 
//	                e.printStackTrace(); 
//	            }             
//	    } 
//	assets
//
//	    public String getFromAssets(String fileName){ 
//	            try { 
//	                 InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open(fileName) ); 
//	                BufferedReader bufReader = new BufferedReader(inputReader);
//	                String line="";
//	                String Result="";
//	                while((line = bufReader.readLine()) != null)
//	                    Result += line;
//	                return Result;
//	            } catch (Exception e) { 
//	                e.printStackTrace(); 
//	            }
//	    } 
}

