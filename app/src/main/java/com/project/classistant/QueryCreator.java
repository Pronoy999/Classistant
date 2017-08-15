package com.project.classistant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pronoymukherjee on 15/08/17.
 */

public class QueryCreator {
    protected static JSONObject createQuery(JSONObject jsonObject){
        JSONObject returnJSON=new JSONObject();
        String query="";
        try {
            switch (jsonObject.getString(Constant.TYPE)) {
                case Constant.TYPE_INSERT:
                    query=makeStringQuery(Constant.TYPE_INSERT,jsonObject.getString(Constant.TABLE_NAME),jsonObject.getJSONArray(Constant.WHERE),jsonObject.getJSONArray(Constant.VALUE));
                    break;
                case Constant.TYPE_UPDATE:

            }
            returnJSON.put(Constant.QUERY,query);
        }
        catch (JSONException e){
            Message.logMessages("ERROR: ",e.toString());
        }
        return returnJSON;
    }
    private static String makeStringQuery(String type,String table,JSONArray where,JSONArray value){
        int wherePos=0,valPos=0;
        String query="";
        try {
            if (type.equals(Constant.TYPE_INSERT)) {
                 query = type + " INTO " + table + " (" +
                        where.get(wherePos++).toString()+", "+//Name
                        where.get(wherePos++).toString()+", "+// email
                        where.get(wherePos++).toString()+", "+//password_hash
                        where.get(wherePos++).toString()+", "+//roll_number
                        where.get(wherePos++).toString()+", "+//dob
                        where.get(wherePos++).toString()+", "+//start_yr
                        where.get(wherePos++).toString()+", "+//end_yr
                        where.get(wherePos++).toString()+", "+//clg_name
                        where.get(wherePos++).toString()+", "+//stream
                        where.get(wherePos).toString()+") "+  //section
                        "VALUES ("+
                        "'"+value.get(valPos++).toString()+"', "+//Name
                        "'"+value.get(valPos++).toString()+"', "+//email
                        "'"+value.get(valPos++).toString()+"', "+//password_hash
                        "'"+value.get(valPos++).toString()+"', "+//roll_number
                        "'"+value.get(valPos++).toString()+"', "+//dob
                        "'"+value.get(valPos++).toString()+"', "+//start_yr
                        "'"+value.get(valPos++).toString()+"', "+//end_yr
                        "'"+value.get(valPos++).toString()+"', "+//clg_name
                        "'"+value.get(valPos++).toString()+"', "+//stream
                        "'"+value.get(valPos).toString()+"')";   //section
            }
        }
        catch (JSONException e){
            Message.logMessages("ERROR: ",e.toString());
        }
        return query;
    }
}
