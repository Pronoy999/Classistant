package com.project.classistant;

import android.content.Context;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
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
            data=Constant.STUDENT_START_YR+":"+studentInfo.getString(Constant.STUDENT_START_YR)+";";
            fileOutputStream.write(data.getBytes());
            data="";
            data=Constant.STUDENT_END_YR+":"+studentInfo.getString(Constant.STUDENT_END_YR)+";";
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
            //TODO: Insert this to Student_MetaData Table.
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
            //TODO: Insert the email and password to Login_table.
        }
        catch (IOException e){
            Message.logMessages("IOException: ",e.toString());
        }
        catch (Exception e){
            Message.logMessages("EXCEPTION: ",e.toString());
        }
    }
    protected boolean checkLoginFile(String account,String email,String passwordHash){
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
                    Message.toastMessage(context,"Incorrect Password!","");
                    return false;
                }
            }
            else {
                //TODO: Check from the Login_Table.
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
}

