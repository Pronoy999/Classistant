package com.project.classistant;

/**
 * A class for all the constants used in the code.
 */

abstract class Constant {
    final static String URL_EMAIL_CONFIRM = "http://classistant.esy.es/MyPhpProject2/confirmer.php";
    //CONTENT VALUES CONSTANT
    final static String URL_QUERY="http://classistant.esy.es/MyPhpProject2/Query.php";
    final static String TYPE="type";
    final static String TYPE_INSERT_STUDENT_METADATA="insert_student_metadata";
    final static String TYPE_UPDATE="update";
    final static String PASSWORD_HASH="password_hash";
    final static String DATE_BIRTH_STUDENT="dob";
    final static String COLLEGE_NAME="college";
    final static String NAME_STUDENT="name";

    final static String LOGIN_FILENAME="LoginFile.txt";
    final static String ACCOUNT="ACCOUNT";
    //Student CONSTANTS
    final static String ACCOUNT_FILENAME="ACCOUNT_DETAILS";
    final static String ACCOUNT_STUDENT="STUDENT_ACCOUNT";
    final static String ACCOUNT_TEACHER="TEACHER_ACCOUNT";
    final static String STUDENT_NAME="Student_Name";
    final static String STUDENT_ROLL="Student_Roll";
    final static String STUDENT_EMAIL="Student_Email";
    final static String STUDENT_PASSWORD="Student_Password";
    final static String STUDENT_STREAM="Student_Stream";
    final static String STUDENT_START_YR="StartYear_Student";
    final static String STUDENT_END_YR="EndYear_Student";

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
    public static final String UID = "_id";
    public static final String NAME = "Name";
    public static final String BSSID = "BSSID";
    public static final String SSID = "SSID";
    public static final String ROLL_NUMBER = "Roll_Number";
    public static final String ATTENDANCE = "Attendance";
    public static final String PASSWORD = "Password";
    public static final String YEAR_IN="Year_In";
    public static final String YEAR_OUT="Year_Out";
    public static DatabaseController databaseController;/* Initialize this in the home activity.
                                                        Use this object to call any method of database.*/
    public static final int PASSWORD_DIALOG=10;
}
