package com.project.classistant;

import android.content.Context;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by pronoymukherjee on 04/04/17.
 */

public class FileController {
    Context context;
    public  FileController(Context context){
        this.context=context;
    }
    protected void CreateAccountStudent(Bundle studentInfo){
        String data="";
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(Constant.ACCOUNT_FILENAME, Context.MODE_PRIVATE);
            data=Constant.STUDENT_NAME+":";
            data+=studentInfo.getString(Constant.STUDENT_NAME)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.STUDENT_ROLL+":";
            data+=studentInfo.getString(Constant.STUDENT_ROLL)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.STUDENT_EMAIL+":"+studentInfo.getString(Constant.STUDENT_EMAIL)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.STUDENT_PASSWORD+":"+studentInfo.getString(Constant.STUDENT_PASSWORD)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.STUDENT_STREAM+":"+studentInfo.getString(Constant.STUDENT_STREAM)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.STUDENT_SECTION+":"+studentInfo.getString(Constant.STUDENT_SECTION)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.STUDENT_START_YR+":"+studentInfo.getString(Constant.STUDENT_START_YR)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.STUDENT_END_YR+":"+studentInfo.getString(Constant.STUDENT_END_YR)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.COLLEGE_NAME+":"+studentInfo.getString(Constant.COLLEGE_NAME)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.DATE_BIRTH_STUDENT+":"+studentInfo.getString(Constant.DATE_BIRTH_STUDENT)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            fileOutputStream.close();
            JSONObject studentValues=new JSONObject();
            JSONArray where=new JSONArray();
            JSONArray values=new JSONArray();
            where.put(Constant.NAME);
            where.put(Constant.LOGIN_EMAIL);
            where.put(Constant.PASSWORD_HASH);
            where.put(Constant.ROLL_NUMBER);
            where.put(Constant.DATE_BIRTH_STUDENT);
            where.put(Constant.START_YEAR);
            where.put(Constant.END_YEAR);
            where.put(Constant.COLLEGE_NAME);
            //where.put(Constant.BSSID);
            where.put(Constant.STREAM);
            where.put(Constant.SECTION);
            studentValues.put(Constant.TYPE,Constant.TYPE_INSERT);
            studentValues.put(Constant.TABLE_NAME,Constant.TABLE_STUDENT_METADATA);
            values.put(studentInfo.getString(Constant.STUDENT_NAME));
            values.put(studentInfo.getString(Constant.STUDENT_EMAIL));
            values.put(studentInfo.getString(Constant.STUDENT_PASSWORD));
            values.put(studentInfo.getString(Constant.STUDENT_ROLL));
            values.put(studentInfo.getString(Constant.DATE_BIRTH_STUDENT));
            values.put(studentInfo.getString(Constant.STUDENT_START_YR));
            values.put(studentInfo.getString(Constant.STUDENT_END_YR));
            values.put(studentInfo.getString(Constant.COLLEGE_NAME));
            //values.put(studentInfo.getString(Constant.BSSID));
            values.put(studentInfo.getString(Constant.STUDENT_STREAM));
            values.put(studentInfo.getString(Constant.STUDENT_SECTION));
            studentValues.put(Constant.WHERE,where);
            studentValues.put(Constant.VALUE,values);
            JSONObject object=QueryCreator.createQuery(studentValues);
            syncCloud(studentValues);//uploading the data to CLOUD.
        }
        catch (IOException e){
            Message.logMessages("IOException: ",e.toString());
        }
        catch (Exception e){
            Message.logMessages("EXCEPTION: ",e.toString());
        }

    }
    protected void createLoginDetails(String account,String email,String passwordHash){
        try{
            FileOutputStream fileOutputStream=context.openFileOutput(Constant.LOGIN_FILENAME,Context.MODE_PRIVATE);
            fileOutputStream.write((Constant.ACCOUNT+":"+account+";").getBytes());
            fileOutputStream.write((Constant.STUDENT_EMAIL+":"+email+";").getBytes());
            fileOutputStream.write((Constant.STUDENT_PASSWORD+":"+passwordHash+";").getBytes());
            fileOutputStream.close();
            JSONObject Login=new JSONObject();
            JSONObject where=new JSONObject();//where clause.
            Login.put(Constant.TYPE,Constant.TYPE_INSERT);
            Login.put(Constant.TABLE_NAME,Constant.LOGIN_METADATA);
            where.put(Constant.ACCOUNT,account);
            where.put(Constant.LOGIN_EMAIL,email);
            where.put(Constant.PASSWORD_HASH,passwordHash);
            Login.put(Constant.WHERE,where);
            syncCloud(Login); //insert into LoginMetadata table.
        }
        catch (IOException e){
            Message.logMessages("IOException: ",e.toString());
        }
        catch (Exception e){
            Message.logMessages("EXCEPTION: ",e.toString());
        }
    }
    protected boolean checkLogin(String account,String email,String passwordHash){
        File file=new File(Constant.LOGIN_FILENAME);
        try {
            if (file.exists()) {
                FileInputStream fileInputStream = context.openFileInput(Constant.LOGIN_FILENAME);
                Scanner scanner = new Scanner(fileInputStream);
                String data = "";
                while (scanner.hasNext()) {
                    data += scanner.nextLine();
                }
                fileInputStream.close();
                String _parts[] = data.split(";");
                String acc_part = _parts[0];
                String _account=acc_part.split(":")[1];
                String email_part=_parts[1];
                String _email=email_part.split(":")[1];
                String password_part=_parts[2];
                String _passwordHash=password_part.split(":")[1];
                if(account.equalsIgnoreCase(_account) && email.equals(_email) && passwordHash.equals(_passwordHash))
                    return true;
                else if (!passwordHash.equals(_passwordHash)){
                    Message.toastMessage(context,"Incorrect Password!","long");
                    return false;
                }
            }
            else {
                JSONObject loginCheck=new JSONObject();
                loginCheck.put(Constant.TYPE,Constant.TYPE_SELECT);
                loginCheck.put(Constant.LOGIN_EMAIL,email);
                loginCheck.put(Constant.PASSWORD_HASH,passwordHash);
                loginCheck.put(Constant.ACCOUNT,account);
                String cloudData=getDataCloud(loginCheck);
                if(!cloudData.equals("")){
                    JSONObject reply=new JSONObject(cloudData);
                    if(reply.getBoolean(Constant.IS_VALID))
                        return true;
                    else{
                        Message.toastMessage(context,reply.getString(Constant.MSG),"long");
                        return false;
                    }
                }
            }
        }
        catch (IOException e){
            Message.logMessages("IOException: ",e.toString());
        }
        catch (Exception e){
            Message.logMessages("EXCEPTION: ",e.toString());
        }
        return false;
    }
    private void syncCloud(JSONObject student){
        try{
            HTTPHandler httpHandler=new HTTPHandler(Constant.URL_QUERY,100000,true,true,"POST");
            httpHandler.HttpPost(student);
        }
        catch (IOException e){
            Message.logMessages("ERROR: ",e.toString());
        }
    }
    private String getDataCloud(JSONObject jsonObject){
        String reply="";
        try{
            HTTPHandler httpHandler=new HTTPHandler(Constant.URL_QUERY,10000,true,true,"POST");
            httpHandler.HttpPost(jsonObject);
            reply=httpHandler.getReplyData();
            httpHandler.closeConnection();
        }
        catch (IOException e){
            Message.logMessages("ERROR: ",e.toString());
        }
        return reply;
    }
}

