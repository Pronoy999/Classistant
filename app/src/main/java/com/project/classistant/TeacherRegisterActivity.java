package com.project.classistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

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
        String arr[]={email};
        InternetConnect internetConnect=new InternetConnect();
        internetConnect.execute(arr);
        //Start the Progress Dialog.
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
    public class InternetConnect extends AsyncTask<String,Void,Void>{
        String data="";

        @Override
        protected Void doInBackground(String... strings) {
            String reply="";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constant.EMAIL_ID_CONFIRM, strings[0]);
                HTTPHandler httpHandler=new HTTPHandler(Constant.URL_EMAIL_CONFIRM,10000,true,true,"POST");
                httpHandler.HttpPost(jsonObject);
                reply=httpHandler.getReplyData();
                JSONObject jsonReply=new JSONObject(reply);
                if(jsonReply.getInt("suc")==0){
                    Message.logMessages("ERROR: ","Couldn't receive data.");
                    Message.toastMessage(getApplicationContext(),"Your Internet Connection is Wonky!","");
                }
                else{
                    otpReceived=jsonReply.getInt("msg");  //setting the OTP received from the server.
                }
            }
            catch (Exception e){
                Message.logMessages("ERROR: ",e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //End the Progress Dialog.
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==Constant.OTP_DIALOG){
            AlertDialog.Builder builder=new AlertDialog.Builder(TeacherRegisterActivity.this);
            LayoutInflater inflater= TeacherRegisterActivity.this.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.otp_layout,null))
                    .setPositiveButton(R.string.otpConfirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            EditText otpText=(EditText) findViewById(R.id.otp);
                            int otpEntered=Integer.parseInt(otpText.getText().toString());
                            if(otpEntered==otpReceived) {
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
                            Message.toastMessage(getApplicationContext(),"We couldn't verify your email ID!","");
                        }
                    });
        }
        return super.onCreateDialog(id);
    }
}

