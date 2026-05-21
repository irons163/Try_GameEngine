package com.example.try_gameengine.viewport

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object FileUtil {
    fun readFileFromAssets(context: Context, fileName: String): String {
        val b: ByteArray? = ByteArray(2000)
        var len = 0
        try {
            val inputStream = context.getResources().getAssets().open(fileName)


            var temp = 0
            while ((inputStream.read().also { temp = it }) != -1) {
                b!![len] = temp.toByte()
                len++
            }
            inputStream.close()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        val s = kotlin.text.String(b!!, 0, len)
        return s
    }

    fun getImageInfo(txt: String, `object`: String, part: String): ImageInfo {
        var x = 0
        var y = 0
        var w = 0
        var h = 0
        var layer = 0
        try {
            var jo = JSONObject(txt)
            jo = jo.getJSONObject(`object`)
            jo = jo.getJSONObject(part)
            x = jo.getInt("x")
            y = jo.getInt("y")

            w = jo.getInt("w")
            h = jo.getInt("h")
            layer = jo.getInt("layer")
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return ImageInfo(x, y, w, h, layer)
    }

    fun readFileFromAssetsF(context: Context, fileName: String): String {
        var result = ""
        try {
            val inputReader = InputStreamReader(context.getResources().getAssets().open(fileName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            result = ""
            while ((bufReader.readLine().also { line = it }) != null) {
                if (result != "") result += "\n"
                result += line
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    } //	Raw：
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

