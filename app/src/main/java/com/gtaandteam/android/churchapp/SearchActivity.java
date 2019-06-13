package com.gtaandteam.android.churchapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class SearchActivity extends AppCompatActivity {
    final String LOG_TAG = this.getClass().getSimpleName();
    ProgressDialog Progress;
    TextView lyric;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //private StorageReference mStorageRef;
        lyric = (TextView)findViewById(R.id.Lyric);
        Progress = new ProgressDialog(this);
        Progress.setMessage("Loading Lyrics");
        Progress.setCancelable(false);
        Progress.show();
        timerDelayRemoveDialog(20000,Progress);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://churchsongapp.appspot.com/Kristheeya-Keerthanam/Song-No-101.txt");
        /*try {
            final File localFile = File.createTempFile("lyric", "txt");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            });
        } catch (IOException e ) {}*/

        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                String str1 = new String(bytes, 0 , bytes.length);
                lyric.setText(str1);
                Progress.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Progress.dismiss();
                Log.d(LOG_TAG,""+exception.getMessage());
            }
        });
    }
    public void timerDelayRemoveDialog(long time, final Dialog d){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try
                {
                    if(d.isShowing()) {
                        d.dismiss();
                        Toast.makeText(SearchActivity.this, "Taking Too Long Due To Connectivity Issues", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    Log.d(LOG_TAG,""+e.getMessage());
                }
            }
        }, time);
    }
}
