package com.project.classistant;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


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
     * De-establishes the connection
     */
    public void closeConnection(){
        this.connection.disconnect();
    }

    /**
     * Makes a HTTP POST request containing the JSONObject to be posted
     * NOTE: This establishes the connection. No need to use HTTPHandler.makeConnection() later.
     * */
    void HttpPost(JSONObject jpost){
        try(OutputStream os = this.connection.getOutputStream()) {
            os.write(encodeForPost(jpost).getBytes());
            os.close();
        }
        catch (IOException e){
            Message.logMessages("ERROR: ",e.toString());
        }
    }

    /**
     * Private method for encoding
     * */
    private String encodeForPost(JSONObject json) throws UnsupportedEncodingException {
        return URLEncoder.encode("json", "UTF-8")+"="+json.toString();
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
    String getReplyData(){
        StringBuilder retString = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.connection.getInputStream(), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null)
                retString.append(line);
            reader.close();
        }
        catch (IOException e){
            Message.logMessages("ERROR: ",e.toString());
        }
        return retString.toString();
    }
}