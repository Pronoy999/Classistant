package com.project.classistant;

/**
 * A class for all the constants used in the code.
 */

abstract class Constant {
    final static String URL_EMAIL_CONFIRM = "http://classistant.esy.es/MyPhpProject2/confirmer.php";
    //JSON VALUES CONSTANT
    final static String URL_QUERY="http://classistant.esy.es/MyPhpProject2/Query.php";
    final static String TYPE="type";
    final static String TYPE_INSERT_STUDENT_METADATA="insert_student_metadata";
    final static String TYPE_INSERT_LOGINMETADATA="insert_login_metadata";
    final static String TYPE_UPDATE="update";
    final static String TYPE_SELECT="select";
    final static String PASSWORD_HASH="password_hash";
    final static String DATE_BIRTH_STUDENT="dob";
    final static String COLLEGE_NAME="college";
    final static String NAME_STUDENT="name";
    final static String VALUE="values";
    //Login Constants
    final static String LOGIN_FILENAME="LoginFile.dat";
    final static String ACCOUNT="ACCOUNT";
    final static String ACCOUNT_FILENAME="ACCOUNT_DETAILS.dat";
    final static String ACCOUNT_STUDENT="STUDENT_ACCOUNT";
    final static String ACCOUNT_TEACHER="TEACHER_ACCOUNT";
    final static String ACCOUNT_LOGGEDIN="";
    final static String LOGIN_EMAIL="email";
    //Student CONSTANTS
    final static String STUDENT_NAME="Student_Name";
    final static String STUDENT_ROLL="Student_Roll";
    final static String STUDENT_EMAIL="Student_Email";
    final static String STUDENT_PASSWORD="Student_Password";
    final static String STUDENT_STREAM="Student_Stream";
    final static String STUDENT_SECTION="Section";
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
    static final String UID = "_id";
    static final String NAME = "Name";
    static final String BSSID = "BSSID";
    static final String SSID = "SSID";
    static final String ROLL_NUMBER = "Roll_Number";

    static final String PASSWORD = "Password";
    static final String YEAR_IN="Year_In";
    static final String YEAR_OUT="Year_Out";

    //Constants for Student database--
    static final String STUDENT_DATABASE_NAME="Student_DB";
    static final String SUBJECT="Subjects";
    static final String TEACHER_ID="Teacher_Id";
    static final String ATTENDANCE = "Attendance";


    static DatabaseController databaseController;/* Initialize this in the home activity.
                                                        Use this object to call any method of database.*/
    public static final int PASSWORD_DIALOG=10;
}
