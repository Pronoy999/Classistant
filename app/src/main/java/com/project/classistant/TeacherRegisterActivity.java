package com.project.classistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TeacherRegisterActivity extends AppCompatActivity {
    int otpReceived;
    boolean isEmailValid=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        (findViewById(R.id.submitTeacher)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeData();
            }
        });
    }
    private void takeData(){
        String name,phoneNo,email,password,dept;
        EditText editText;
        editText=(EditText) findViewById(R.id.teacherName);
        name=editText.getText().toString();
        editText=(EditText) findViewById(R.id.teacherPhone);
        phoneNo=editText.getText().toString();
        editText=(EditText) findViewById(R.id.teacherEmail);
        email=editText.getText().toString();
        editText=(EditText) findViewById(R.id.teacherPassword);
        password=editText.getText().toString();
        editText=(EditText) findViewById(R.id.teacherDepartment);
        dept=editText.getText().toString();
        if(!ValidityChecker.checkValidityEmail(email)){
            Message.toastMessage(getApplicationContext(),"Please enter a valid Email ID!","");
            setNull(R.id.teacherEmail);
            return;
        }
        if (!ValidityChecker.checkValidityPassword(password)){
            Message.toastMessage(getApplicationContext(),"Please enter a valid Password!","");
            setNull(R.id.teacherPassword);
            showDialog(Constant.PASSWORD_DIALOG);
            return;
        }
        //
        showDialog(Constant.OTP_DIALOG);
        if(isEmailValid){
            Bundle teacherData=new Bundle();
            teacherData.putString(Constant.TEACHER_NAME,name);
            teacherData.putString(Constant.TEACHER_PHONE,phoneNo);
            teacherData.putString(Constant.TEACHER_EMAIL,email);
            teacherData.putString(Constant.PASSWORD_HASH,password);
            teacherData.putString(Constant.TEACHER_DEPT,dept);
            FileController fileController=new FileController(getApplicationContext());
            fileController.createAccountTeacher(teacherData);//Writing the data in the file.
        }

    }
    private void setNull(int id){
        EditText editText=(EditText) findViewById(id);
        editText.getText().clear();
    }

}

