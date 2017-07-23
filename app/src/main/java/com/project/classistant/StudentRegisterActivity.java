package com.project.classistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

public class StudentRegisterActivity extends AppCompatActivity {
    boolean isEmailValid;int OTPReceived;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        findViewById(R.id.submitDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeData();
            }
        });
    }
    protected void takeData(){
        String name,roll,email,stream,password;
        int startYear,endYear;
        EditText editText;
        editText=(EditText) findViewById(R.id.studentName);
        name=editText.getText().toString();
        editText=(EditText) findViewById(R.id.studentRollNumber);
        roll=editText.getText().toString();
        editText=(EditText) findViewById(R.id.studentEmail);
        email=editText.getText().toString();
        editText=(EditText) findViewById(R.id.studentPassword);
        password=editText.getText().toString();
        editText=(EditText) findViewById(R.id.stream);
        stream=editText.getText().toString();
        editText=(EditText) findViewById(R.id.startYear);
        startYear=Integer.parseInt(editText.getText().toString());
        editText=(EditText) findViewById(R.id.endYear);
        endYear=Integer.parseInt(editText.getText().toString());
        if(!ValidityChecker.checkValidityEmail(email)){
            Message.toastMessage(getApplicationContext(),"Please Enter a valid Email Id!","");
            setNull(R.id.studentEmail);
            return;
        }
        if(!ValidityChecker.checkValidityPassword(password)){
            Message.toastMessage(getApplicationContext(),"Please Enter a valid password!","");
            setNull(R.id.studentPassword);
            return;
        }
        //send the OTP to the Email Code.
        try {
            HTTPHandler httpHandler = new HTTPHandler("http://classistant.esy.es/MyPhpProject2/confirmer.php", 10000, true, true, "POST");
            ContentValues contentValues=new ContentValues();
            contentValues.put("id",email);
            httpHandler.HttpPost(contentValues);
            String reply=httpHandler.getReplyData();
            JSONObject jsonObject=new JSONObject(reply);
            int success=jsonObject.getInt("suc");
            if(success==0) {
                Message.toastMessage(getApplicationContext(), "ERROR: " + jsonObject.getString("msg"),"");
                return;
            }
            OTPReceived=jsonObject.getInt("msg");
            showDialog(Constant.OTP_DIALOG);
        }
        catch (Exception e){
            Message.logMessages("ERROR: ",e.toString());
        }
        showDialog(Constant.OTP_DIALOG);
        if(isEmailValid) {
            Bundle studentDetails = new Bundle();
            studentDetails.putString(Constant.STUDENT_NAME, name);
            studentDetails.putString(Constant.STUDENT_ROLL, roll);
            studentDetails.putString(Constant.STUDENT_EMAIL, email);
            studentDetails.putString(Constant.STUDENT_STREAM, stream);
            studentDetails.putInt(Constant.STUDENT_START_YR, startYear);
            studentDetails.putInt(Constant.STUDENT_END_YR, endYear);
            FileController fileController = new FileController(getApplicationContext());
            fileController.CreateAccountStudent(studentDetails);
            ActivityChanger.changeActivity(StudentRegisterActivity.this,"AddSubjectStudent");
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==Constant.OTP_DIALOG){
            AlertDialog.Builder builder=new AlertDialog.Builder(StudentRegisterActivity.this);
            LayoutInflater inflater= StudentRegisterActivity.this.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.otp_layout,null))
                    .setPositiveButton(R.string.otpConfirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText otpText=(EditText) findViewById(R.id.otp);
                            int otpEntered=Integer.parseInt(otpText.getText().toString());
                            if(otpEntered==OTPReceived) {
                                isEmailValid = true;
                                Message.toastMessage(getApplicationContext(),"Email verified successfully!","long");
                            }
                            else
                                Message.toastMessage(getApplicationContext(),"Please enter a valid OTP!","long");
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
    private void setNull(int id){
        EditText editText=(EditText) findViewById(id);
        editText.setText("");
    }
}
