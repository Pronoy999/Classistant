package com.project.classistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Shubham on 16-07-2017.
 */

public class DatabaseController{
    MyHelper helper;


    /**
     * This method initializes the helper object that is used to access the database.
     * @param context: The context of the activity where the database is opened.
     */
    DatabaseController(Context context){
        helper=new MyHelper(context);
    }


   /* public Bundle getAllData(){

        SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();
    }
    public void putData(){

    }*/

    /**
     * This method lists all the SSIDs and corresponding Passwords of the student in the class
     * @param stream: The stream of the class
     * @param section: The section of the class
     * @return JSONObject containing two JSONArrays(SSIDs and Passwords)
     *  The keys of the JSONArrays are Constant.SSID and Constant.PASSWORD
     */
    public JSONObject getPasswordAndSSID(String stream,String section){
        JSONObject jsonObject=new JSONObject();
        JSONArray ssid=new JSONArray();
        JSONArray password=new JSONArray();
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        String columns[] = {Constant.SSID, Constant.PASSWORD};
        String selectArgs[]={stream,section};
        Cursor cursor=sqLiteDatabase.query(Constant.TEACHER_EMAIL_VALUE,columns,
                Constant.STREAM+"=?" + " AND "+ Constant.SECTION+"=?",selectArgs,null,null,null);
        try {
            while (cursor.moveToNext()) {
                ssid.put(cursor.getString(cursor.getColumnIndex(Constant.SSID)));
                password.put(cursor.getString(cursor.getColumnIndex(Constant.PASSWORD)));
            }
        }
        catch (Exception e)
        {
            Message.logMessages("ERROR: ",e.toString());
        }
        cursor.close();
        try {
            jsonObject.put(Constant.SSID, ssid);
            jsonObject.put(Constant.PASSWORD,password);
        }
        catch (JSONException e){
            Message.logMessages("ERROR: ",e.toString());
        }
        return  jsonObject;
    }

    /**
     * This method returns the number of student in that particular class(stream+section)
     * @param stream:The stream of the class
     * @param section:The section of the class
     * @returns the number of student in that class
     */
   public int getNumberOfStudents(String stream,String section){
       int number=0;
       SQLiteDatabase sqLiteDatabase=helper.getReadableDatabase();
       String query="SELECT "+Constant.UID+" FROM "+Constant.TEACHER_EMAIL_VALUE+" WHERE "
                                +Constant._STREAM+" = "+stream+" AND "+
                                Constant.SECTION+" = "+section;
       Cursor cursor=sqLiteDatabase.rawQuery(query,null);
       while (cursor.moveToNext()){
           number++;
       }
       cursor.close();
       return number;

   }

    /**
     * This method updates the attendance in the local database of teacher by checking with the bssid sent.
     * @param bssid: It accepts the bssid of a student.
     */
   public void putAttendance(String bssid){
       int attendance=0;
       SQLiteDatabase sqLiteDatabase;
       ContentValues contentValues;
       String whereArgs[]= {bssid};
       sqLiteDatabase = helper.getWritableDatabase();
       String columns[] = {Constant.ATTENDANCE};
       String selectionArgs[] = {bssid};
       Cursor cursor = sqLiteDatabase.query(Constant.TABLE_NAME, columns, Constant.BSSID + " =?", selectionArgs, null, null, null, null);
       if (cursor == null) {
           Message.logMessages("Error: ", "Wrong BSSID sent");
           //TODO: add to log file: Wrong BSSID sent.
           return ;
       }
       while (cursor.moveToNext()) {
           attendance = cursor.getInt(cursor.getColumnIndex(Constant.ATTENDANCE));//column index of Constant.ATTENDANCE is 6
       }
       cursor.close();
       contentValues = new ContentValues();
       contentValues.put(Constant.ATTENDANCE, attendance + 1);
       Message.logMessages("updateAttdBssid:", "Attendance++ done");
       sqLiteDatabase.update(Constant.TABLE_NAME,contentValues,Constant.BSSID+"=?",whereArgs);
   }

    /**
     * This method puts the attendance by roll number.
     * This is done when the teacher puts the attendance manually after viewing the list of absent students
     * @param rollNumber:It accepts the Roll Number of a student.
     */
   public void putAttendanceByRoll(String rollNumber){
       int attendance=0;
       SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();
       ContentValues contentValues;
       String whereArgs[]={rollNumber};
       String columns[]={Constant.ATTENDANCE};
       String selectionArgs[]={rollNumber};
       Cursor cursor = sqLiteDatabase.query(Constant.TABLE_NAME, columns, Constant.ROLL_NUMBER + " =?", selectionArgs, null, null, null, null);
       while(cursor.moveToNext()){
           attendance=cursor.getInt(cursor.getColumnIndex(Constant.ATTENDANCE));
           //TODO: add to log file: Wrong BSSID sent.
       }
       cursor.close();
       contentValues=new ContentValues();
       contentValues.put(Constant.ATTENDANCE,attendance+1);
       Message.logMessages("updateAttRoll:","Attendance updated");
       sqLiteDatabase.update(Constant.TABLE_NAME,contentValues,Constant.ROLL_NUMBER+"=?",whereArgs);
   }

    /**
     * This inner class creates the database and upgrades it when necessary.
     */
    public class MyHelper extends SQLiteOpenHelper{

        private final String CREATE_TABLE_TEACHER = "CREATE TABLE "
                +Constant.TEACHER_EMAIL_VALUE + "("
                +Constant.UID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Constant.NAME + " VARCHAR(255), "
                +Constant.ROLL_NUMBER +" VARCHAR(255),"
                +Constant._STREAM+" VARCHAR(255),"
                +Constant.SECTION+" VARCHAR(255),"
                +Constant.SSID +" VARCHAR(255),"
                +Constant.BSSID + " VARCHAR(255),"
                +Constant.PASSWORD +" VARCHAR(255),"
                +Constant.ATTENDANCE +" INT,"
                +Constant.TOTAL_ATTENDANCE+" INT,"
                +Constant.YEAR_IN +" INT,"
                +Constant.YEAR_OUT +" INT);";

        private  String CREATE_TABLE_STUDENT = "CREATE TABLE "
                + Constant.STUDENT_EMAIL_VALUE + "("
                + Constant.UID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constant.SUBJECT+ " VARCHAR(255), "
                + Constant.TEACHER_ID+ " VARCHAR(255), "
                + Constant.TEACHER_NAME + " VARCHAR(255), "
                + Constant.TEACHER_EMAIL + " VARCHAR(255), "
                + Constant.TEACHER_PHONE+ " VARCHAR(255), "
                + Constant.TOTAL_ATTENDANCE+ " INT, "
                + Constant.ATTENDANCE+ " INT); ";



        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Constant.ATTENDANCE_DATABASE_NAME;


        MyHelper(Context context) {
            super(context, Constant.ATTENDANCE_DATABASE_NAME, null, Constant.DATABASE_VERSION);
            Message.logMessages("MyHelper: ","Database Object Created");
        }

       /**
        * This method creates the database.
        * @param sqLiteDatabase : This is the database object.
        */
       @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                //Execute a single SQL statement that is NOT a SELECT or
                // any other SQL statement that returns data.

                if(Constant.ACCOUNT.equals("Teacher")) {
                    sqLiteDatabase.execSQL(CREATE_TABLE_TEACHER);
                    Message.logMessages("onCreate :", "onCreate() called");
                }
                else {
                    sqLiteDatabase.execSQL(CREATE_TABLE_STUDENT);
                    Message.logMessages("onCreate :", "onCreate() called");
                }
            } catch (SQLException e) {
                Message.logMessages("Error in onCreate :",""+e);
            }
        }

       /**
        * This method is called when the database is ti be upgraded to a new version.
        * @param sqLiteDatabase: Old database object.
        * @param oldVersion: Old database version.
        * @param newVersion: New database version.
        */
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            try {
                Message.logMessages("onUpgrade :","onUpgrade() called");
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Message.logMessages("Error in onUpgrade :",""+e);
            }
        }
    }
}
