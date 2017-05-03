package com.project.classistant;

import android.content.Context;

import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;


/**
 * Created by pronoymukherjee on 04/04/17.
 */

public class FileController {
    Context context;
    public  FileController(Context context){
        this.context=context;
    }
    public void create_loginFile(String account){
        try{
            OutputStreamWriter outputStreamWriter= new OutputStreamWriter(context.openFileOutput(Constant.LoginFile,Context.MODE_PRIVATE));
            outputStreamWriter.write(account+" logged in,");
            outputStreamWriter.close();
        }
        catch (Exception e){
            Message.logMessages("ERROR ",e.toString());
        }
    }
}

