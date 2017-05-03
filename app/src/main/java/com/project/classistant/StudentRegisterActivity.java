package com.project.classistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class StudentRegisterActivity extends AppCompatActivity {
    boolean isEmailValid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        findViewById(R.id.submitDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeData();
            }
        });
    }
    protected void takeData(){
        String name,roll,email,stream;
        int startYear,endYear;
        EditText editText;
        editText=(EditText) findViewById(R.id.studentName);
        name=editText.getText().toString();
        editText=(EditText) findViewById(R.id.studentRollNumber);
        roll=editText.getText().toString();
        editText=(EditText) findViewById(R.id.studentEmail);
        email=editText.getText().toString();
        editText=(EditText) findViewById(R.id.stream);
        stream=editText.getText().toString();
        editText=(EditText) findViewById(R.id.startYear);
        startYear=Integer.parseInt(editText.getText().toString());
        editText=(EditText) findViewById(R.id.endYear);
        endYear=Integer.parseInt(editText.getText().toString());
        if(!checkEmailId(email)){
            Message.toastMessage(getApplicationContext(),"Please Enter a valid Email Id!","");
            return;
        }
        //send the OTP to the Email Code.
        showDialog(Constant.OTP_Dialog);
        if(isEmailValid) {
            Bundle studentDetails = new Bundle();
            studentDetails.putString(Constant.studentName, name);
            studentDetails.putString(Constant.studentRoll, roll);
            studentDetails.putString(Constant.studentEmail, email);
            studentDetails.putString(Constant.studentStream, stream);
            studentDetails.putInt(Constant.studentStartYear, startYear);
            studentDetails.putInt(Constant.studentEndYear, endYear);
            FileController fileController = new FileController(getApplicationContext());
            fileController.CreateAccountStudent(studentDetails);
            ActivityChanger.changeActivity(StudentRegisterActivity.this,"AddSubjectStudent");
        }
    }
    protected boolean checkEmailId(String email){
        int i,l=email.length();
        boolean isvalid=false;
        for(i=l-1;i>=0;i--){
            if(email.charAt(i)=='.'){
                isvalid=true;break;}
        }
        if(isvalid) {
            isvalid=false;
            for (int j = i; j >= 0; j--) {
                if (email.charAt(j) == '@') {
                    isvalid=true;break;
                }
            }
        }
        if(isvalid)
            return true;
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==Constant.OTP_Dialog){
            AlertDialog.Builder builder=new AlertDialog.Builder(StudentRegisterActivity.this);
            LayoutInflater inflater= StudentRegisterActivity.this.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.otp_layout,null))
                    .setPositiveButton(R.string.otpConfirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Check the validity of OTP
                            isEmailValid=true;
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            isEmailValid=false;
                        }
                    });
        }
        return super.onCreateDialog(id);
    }
}
