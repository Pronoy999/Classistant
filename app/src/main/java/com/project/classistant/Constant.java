package com.project.classistant;

/**
 * A class for all the constants used in the code.
 */

abstract class Constant {
    final static String URL_EMAIL_CONFIRM = "http://classistant.esy.es/DataBaseManager/confirmer.php";
    //JSON VALUES CONSTANT
    final static String URL_QUERY="http://classistant.esy.es/DataBaseManager/query.php";
    final static String TYPE="type";
    final static String TABLE_STUDENT_METADATA="insert_student_metadata";
    final static String TABLE_LOGINMETADATA="insert_login_metadata";

    final static String TYPE_CREATE="CREATE";
    final static String TYPE_INSERT="INSERT";
    final static String TYPE_UPDATE="UPDATE";
    final static String TYPE_SELECT="SELECT";
    final static String VALUE="values";
    final static String QUERY="query";
    final static String QUERY_REPLY="query_reply";
    final static int CHOICE_SYNC_CLOUD=1;
    final static int CHOICE_GET_CLOUDATA=2;
    //Login Constants
    final static String LOGIN_FILENAME="LoginFile.dat";
    final static String ACCOUNT="account";
    final static String ACCOUNT_FILENAME="ACCOUNT_DETAILS.dat";
    final static String ACCOUNT_STUDENT="STUDENT_ACCOUNT";
    final static String ACCOUNT_TEACHER="TEACHER_ACCOUNT";
    final static String ACCOUNT_LOGGEDIN="";
    final static String LOGIN_EMAIL="email";
    final static String IS_VALID="isValid";
    final static String ERROR_MSG="err_msg";
    final static String WHERE="where";
    //Student CONSTANTS
    final static String STUDENT_NAME="Student_Name";
    final static String STUDENT_ROLL="Student_Roll";
    final static String STUDENT_EMAIL="Student_Email";
    final static String STUDENT_PASSWORD="Student_Password";
    final static String STUDENT_STREAM="Student_Stream";
    final static String STUDENT_SECTION="Student_Section";
    final static String STUDENT_START_YR="StartYear_Student";
    final static String STUDENT_END_YR="EndYear_Student";
    final static String TOTAL_ATTENDANCE="Total_Attendance";

    // Teacher CONSTANTS
    final static String TEACHER_NAME="Teacher_Name";
    final static String TEACHER_EMAIL="Teacher_email";
    final static String TEACHER_PASSWORD="Teacher_Password";
    final static String TEACHER_PHONE="Teacher_phone";
    final static String TEACHER_COLLEGE_NAME="Teacher_college_name";
    final static String TEACHER_DEPT="Teacher_Dept";

    final static int OTP_DIALOG=32;
    final static String ATTENDANCE_DATABASE_NAME="Attendance_Database";
    final static String LOGIN_DATABASE_NAME="Login_Database";
    static int DATABASE_VERSION=1;
    //Database Constants
    static final String TABLE_NAME="table_name";
    static final String LOGIN_METADATA="LOGIN_METADATA";
    static final String TEACHER_METADATA="TEACHER_METADATA";
    static final String STUDENT_METADATA="STUDENT_METADATA";
    static final String TEACHER_TABLE="TEACHER_TABLE";
    static final String STUDENT_TABLE="STUDENT_TABLE";

    static final String UID = "_id";
    static final String NAME = "name";
    static final String BSSID = "bssid";
    static final String SSID = "ssid";

    static final String PASSWORD = "Password";
    static final String YEAR_IN="Year_In";
    static final String YEAR_OUT="Year_Out";

    //Constants for Student database--
    static final String STUDENT_DATABASE_NAME="Student_DB";
    static final String SUBJECT="Subjects";
    static final String TEACHER_ID="Teacher_Id";
    static final String ATTENDANCE = "Attendance";
    final static String PASSWORD_HASH="password_hash";
    final static String DATE_BIRTH_STUDENT="dob";
    final static String COLLEGE_NAME="college";
    final static String START_YEAR="start_year";
    final static String END_YEAR="end_year";
    final static String STREAM="stream";
    final static String SECTION="section";
    static final String ROLL_NUMBER = "Roll_Number";


    static DatabaseController databaseController;/* Initialize this in the home activity.
                                                        Use this object to call any method of database.*/
    static final int PASSWORD_DIALOG=10;
}
