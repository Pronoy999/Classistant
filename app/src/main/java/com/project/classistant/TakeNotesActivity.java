package com.project.classistant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class TakeNotesActivity extends AppCompatActivity {

    String dir; File imgFile;
    static final int CAMERA_REQ_CODE = 111;
    static int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dir = Environment.getExternalStorageDirectory()+"/Class Notes/";
        File noteFile = new File(dir);
        if(!noteFile.mkdirs()){
            Toast.makeText(getApplicationContext(), "Cannot find notes directory", Toast.LENGTH_LONG).show();
        }
    }
    public void startCamera(View v){
        count++;
        String imgFileName = dir + "IMG_"+count+".jpg";
        imgFile = new File(imgFileName);
        try{
            imgFile.createNewFile();
        }catch (IOException e){
            Toast.makeText(getApplicationContext(), "Unable to make image file. Sorry.", Toast.LENGTH_LONG).show();
        }
        Uri imgUri = Uri.fromFile(imgFile);
        Intent camintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camintent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);

        startActivityForResult(camintent, CAMERA_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQ_CODE && resultCode == RESULT_OK){
            Toast.makeText(getApplicationContext(), "Your notes have been saved in"+dir, Toast.LENGTH_LONG).show();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imgFile)));
        }
    }
}
