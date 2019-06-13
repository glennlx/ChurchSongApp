package com.gtaandteam.android.churchapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SearchActivity extends AppCompatActivity {
    final String LOG_TAG = this.getClass().getSimpleName();
    ProgressDialog Progress;
    TextView lyric;
    Spinner spinner1;
    Button search;
    EditText entrySearch;
    StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //private StorageReference mStorageRef;
        lyric = (TextView)findViewById(R.id.Lyric);

        spinner1 = (Spinner)findViewById(R.id.spinner_search);
        search = (Button)findViewById(R.id.button_search);
        entrySearch = (EditText)findViewById(R.id.entrySearch);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.TypeOfSong, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        final FirebaseStorage storage = FirebaseStorage.getInstance();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Progress = new ProgressDialog(SearchActivity.this);
                Progress.setMessage("Loading Lyrics");
                Progress.setCancelable(false);
                Progress.show();
                timerDelayRemoveDialog(20000,Progress);
                String songType=spinner1.getSelectedItem().toString();
                String songNo = entrySearch.getText().toString();
                String storageRefLink = "gs://churchsongapp.appspot.com/";
                String fullURL = storageRefLink + "/" + songType + "/Song-No-" + songNo + ".txt";

                storageRef = storage.getReferenceFromUrl(fullURL);

                final long ONE_MEGABYTE = 1024 * 1024;
                storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Data for "images/island.jpg" is returns, use this as needed
                        String str1 = new String(bytes, 0 , bytes.length);
                        lyric.setText(str1);
                        if(Progress.isShowing())
                            Progress.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        if(Progress.isShowing())
                            Progress.dismiss();
                        Log.d(LOG_TAG,""+exception.getMessage());
                        Toast.makeText(SearchActivity.this, "An Error Occured\n" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
