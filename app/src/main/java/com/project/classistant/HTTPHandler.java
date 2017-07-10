package com.project.classistant;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Class to manage all HTTP requests to server.
 */

class HTTPHandler {

    private HttpURLConnection connection;
    final static int HTTP_OK = HttpURLConnection.HTTP_OK; //http status 200

    /**
     * Initialises the attributes of the connection
     * */
    HTTPHandler(String Url, int timeOut, boolean doInput, boolean doOutput, String reqMethod) throws IOException{
        URL url = new URL(Url);
        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(timeOut);
        connection.setReadTimeout(timeOut);
        connection.setDoInput(doInput);
        connection.setDoOutput(doOutput);
        connection.setRequestMethod(reqMethod);
    }
    /**
     * Establishes the connection
     * (Use this when a connection is to be made without sending additional data through POST or GET)
     * */
    public void makeConnection() throws IOException{
        this.connection.connect();
    }

    /**
     * Makes a HTTP POST request containing the content value data to be posted
     * NOTE: This establishes the connection. No need to use HTTPHandler.makeConnection() later.
     * */
    void HttpPost(ContentValues post)throws IOException{
        OutputStream os = this.connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(encodeForPost(post));
        writer.flush();
        writer.close();
        os.close();
    }

    private String encodeForPost(ContentValues values) throws UnsupportedEncodingException {
        Iterator<String> itr = values.keySet().iterator();
        StringBuilder request = new StringBuilder();
        if(itr.hasNext()) {
            String key = itr.next();
            request.append(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(values.get(key).toString(), "UTF-8"));
            while (itr.hasNext()) {
                key = itr.next();
                request.append("&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(values.get(key).toString(), "UTF-8"));
            }
        }
        return request.toString();
    }
    /**
     * Returns the response code of the connection
     * HTTP_OK is initialized as OK status
     * */
    int getResponseCode()throws IOException{
        return this.connection.getResponseCode();
    }

    /**
     * Returns the reply string of data after the server request
     * */
    String getReplyData()throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"));
        StringBuilder retString = new StringBuilder();
        String line;

        while ((line = reader.readLine())!=null)
            retString.append(line);
        reader.close();
        return retString.toString();
    }
}
