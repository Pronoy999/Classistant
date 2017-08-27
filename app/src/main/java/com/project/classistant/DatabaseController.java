package com.project.classistant;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
     * This inner class creates the database and upgrades it when necessary.
     */
    public class MyHelper extends SQLiteOpenHelper{

        private static final String CREATE_TABLE_TEACHER = "CREATE TABLE "
                +Constant.ATTENDANCE_DATABASE_NAME + "("
                +Constant.UID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Constant.NAME + " VARCHAR(255), "
                +Constant.ROLL_NUMBER +" VARCHAR(255),"
                +Constant.SSID +" VARCHAR(255),"
                +Constant.BSSID + " VARCHAR(255),"
                +Constant.PASSWORD +" VARCHAR(255),"
                +Constant.ATTENDANCE +" INT,"
                +Constant.TOTAL_ATTENDANCE+" INT,"
                +Constant.YEAR_IN +" INT,"
                +Constant.YEAR_OUT +" INT);";

        private static final String CREATE_TABLE_STUDENT = "CREATE TABLE "
                + Constant.STUDENT_DATABASE_NAME + "("
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
