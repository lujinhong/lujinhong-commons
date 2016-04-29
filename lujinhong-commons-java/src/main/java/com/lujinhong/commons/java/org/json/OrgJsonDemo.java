/**
 * 
 */
package com.lujinhong.commons.java.org.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

/**
* date: 2016年3月15日 上午11:27:16
* @author LUJINHONG lu_jin_hong@163.com
* Function: TODO ADD FUNCTION.
* last modified: 2016年3月15日 上午11:27:16
*/

public class OrgJsonDemo {

	public static String generateJsonString(){
		JSONObject studentJSONObject = new JSONObject();
		try {
			studentJSONObject.put("id",5);
			studentJSONObject.put("name", "Jason");
			studentJSONObject.put("phone","13579246810");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return studentJSONObject.toString();
		
	}
	
    private static String generateJsonString2(){  
        JSONStringer jsonStringer = new JSONStringer();  
        try {  
            jsonStringer.object();  
            jsonStringer.key("name");  
            jsonStringer.value("Jason");  
            jsonStringer.key("id");  
            jsonStringer.value(20130001);  
            jsonStringer.key("phone");  
            jsonStringer.value("13579246810");  
            jsonStringer.endObject();  
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
        return jsonStringer.toString();
    }
    
    private static String JSONText = "{\"id\":20130001,\"phone\":\"13579246810\",\"name\":\"Jason\"}";  
    
    public static String getJSONContent(){  
        String name = null;  
        int id = 0;  
        String phone = null;  
        try {  
            JSONObject studentJSONObject = new JSONObject(JSONText);   
            name = studentJSONObject.getString("name");  
            id = studentJSONObject.getInt("id");  
            phone = studentJSONObject.getString("phone");  
              
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
        return name + "  " + id + "   " + phone;  
    }  
    
    public static String getJSONContent2(){  
        JSONTokener jsonTokener = new JSONTokener(JSONText);   
        JSONObject studentJSONObject;  
        String name = null;  
        int id = 0;  
        String phone = null;  
        try {  
            studentJSONObject = (JSONObject) jsonTokener.nextValue();  
            name = studentJSONObject.getString("name");  
            id = studentJSONObject.getInt("id");  
            phone = studentJSONObject.getString("phone");  
              
        } catch (JSONException e) {  
            e.printStackTrace();  
        }  
        return name + "  " + id + "   " + phone;  
    }  
  

	
	
	public static void main(String[] args){
		System.out.println(generateJsonString());
		System.out.println(generateJsonString2());
		System.out.println(getJSONContent());
		System.out.println(getJSONContent2());
	}
}
