package com.project.classistant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

public class StudentRegisterActivity extends AppCompatActivity {
    boolean isEmailValid;int OTPReceived;
    HashMaker hashMaker=new HashMaker("SHA-256");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        Message.toastMessage(getApplicationContext(),"If in your college you don't have a section for your stream then enter 'A' or else keep it blank.","long");
        findViewById(R.id.submitDetails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeData();
            }
        });
    }
    protected void takeData(){
        String name,roll,email,stream,section,password,dob,clgName;
        int startYear,endYear;
        EditText editText;
        editText=(EditText) findViewById(R.id.studentName);
        name=editText.getText().toString();
        if(name.isEmpty() || name.equals("")){
            Message.toastMessage(getApplicationContext(),"Please enter your name!","");
            return;
        }
        editText=(EditText) findViewById(R.id.studentRollNumber);
        roll=editText.getText().toString();
        if (roll.isEmpty() || roll.equals("")){
            Message.toastMessage(getApplicationContext(),"You have to give your roll number!","");
            return;
        }
        editText=(EditText) findViewById(R.id.studentEmail);
        email=editText.getText().toString();
        if (email.isEmpty() || email.equals("")){
            Message.toastMessage(getApplicationContext(),"We need to contact you through your email ID!","");
            return;
        }
        editText=(EditText) findViewById(R.id.studentPassword);
        password=editText.getText().toString();
        if (password.isEmpty() || password.equals("")){
            Message.toastMessage(getApplicationContext(),"Please provide a password!","");
            return;
        }
        password=hashMaker.getHash(password);
        editText=(EditText) findViewById(R.id.stream);
        stream=editText.getText().toString();
        if (stream.isEmpty() || stream.equals("")){
            Message.toastMessage(getApplicationContext(),"We need to know what you are studying!","");
            return;
        }
        editText=(EditText)findViewById(R.id.section);
        section=editText.getText().toString();
        if (section.equals("")){
            section="A";
        }
        editText=(EditText) findViewById(R.id.startYear);
        startYear=Integer.parseInt(editText.getText().toString());
        if (editText.getText().toString().isEmpty() || editText.getText().toString().equals("")){
            Message.toastMessage(getApplicationContext(),"Please enter the start year!","");
            return;
        }
        editText=(EditText) findViewById(R.id.endYear);
        endYear=Integer.parseInt(editText.getText().toString());
        if (editText.getText().toString().isEmpty() || editText.getText().toString().equals("")){
            Message.toastMessage(getApplicationContext(),"Please enter the end year!","");
            return;
        }
        editText=(EditText) findViewById(R.id.dob);
        dob=editText.getText().toString();
        if(dob.isEmpty() || dob.equals("")){
            Message.toastMessage(getApplicationContext(),"How young are you?","");
            return;
        }
        editText=(EditText) findViewById(R.id.collegename);
        clgName=editText.getText().toString();
        if (clgName.isEmpty() || clgName.equals("")){
            Message.toastMessage(getApplicationContext(),"Which college you are in?","");
            return;
        }

        if(!ValidityChecker.checkValidityEmail(email)){
            Message.toastMessage(getApplicationContext(),"Please Enter a valid Email Id!","");
            setNull(R.id.studentEmail);
            return;
        }
        if(!ValidityChecker.checkValidityPassword(password)){
            Message.toastMessage(getApplicationContext(),"Please Enter a valid password!","");
            //TODO:Provide the details for the password.
            setNull(R.id.studentPassword);
            return;
        }
        //send the OTP to the Email Code.
        String arr[]={email};
        ConnectInternet connectInternet=new ConnectInternet();
        //Start the Progress Dialog

        connectInternet.execute(arr);
        showDialog(Constant.OTP_DIALOG);
        if(isEmailValid) {
            Bundle studentDetails = new Bundle();
            studentDetails.putString(Constant.STUDENT_NAME, name);
            studentDetails.putString(Constant.STUDENT_ROLL, roll);
            studentDetails.putString(Constant.STUDENT_EMAIL, email);
            HashMaker hashMaker=new HashMaker("SHA-256");
            studentDetails.putString(Constant.STUDENT_PASSWORD,hashMaker.getHash(password));
            studentDetails.putString(Constant.STUDENT_STREAM, stream);
            studentDetails.putString(Constant.STUDENT_SECTION,section);
            studentDetails.putInt(Constant.STUDENT_START_YR, startYear);
            studentDetails.putInt(Constant.STUDENT_END_YR, endYear);
            studentDetails.putString(Constant.COLLEGE_NAME,clgName);
            studentDetails.putString(Constant.DATE_BIRTH_STUDENT,dob);
            FileController fileController = new FileController(getApplicationContext());
            fileController.CreateAccountStudent(studentDetails);
            fileController.createLoginDetails(Constant.ACCOUNT_STUDENT,email,hashMaker.getHash(password));
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
                            Message.toastMessage(getApplicationContext(),"We couldn't verify your email ID!","");
                        }
                    });
        }
        return super.onCreateDialog(id);
    }
    private void setNull(int id){
        EditText editText=(EditText) findViewById(id);
        editText.getText().clear();
    }
    public class ConnectInternet extends AsyncTask<String,Void,Void>{
        String reply="";
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Constant.EMAIL_ID_CONFIRM, strings[0]);
                HTTPHandler httpHandler=new HTTPHandler(Constant.URL_EMAIL_CONFIRM,10000,true,true,"POST");
                if(httpHandler.isReachable()) {
                    httpHandler.HttpPost(jsonObject);
                    reply = httpHandler.getReplyData();
                    JSONObject jsonRepy = new JSONObject(reply);
                    if (jsonRepy.getInt("suc") == 0) {
                        Message.toastMessage(getApplicationContext(), "Your Internet Connection is Wonky!", "");
                    } else {
                        OTPReceived = jsonRepy.getInt("msg");
                    }
                }
                else {
                    Message.toastMessage(getApplicationContext(), "Please connect to an Internet source!", "long");
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
            //Stop the dialog here.
            progressDialog.dismiss();
        }
    }
}
