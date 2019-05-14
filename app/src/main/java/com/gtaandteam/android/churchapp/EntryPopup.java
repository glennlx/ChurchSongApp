package com.gtaandteam.android.churchapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Pawan on 01-10-2017.
 */

public class EntryPopup extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String date = "Date :" + day + "/" + month + "/" + year ;

        final String message = getIntent().getStringExtra("EXTRA_SESSION_ID");
        setContentView(R.layout.entry_popup);

        EditText editText = (EditText)findViewById(R.id.EntryPOPupText);
        editText.setText(date + "\n" + message);

        Button button = (Button)findViewById(R.id.CopyButton);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                                          ClipData clip = ClipData.newPlainText("Church",message);
                                          clipboard.setPrimaryClip(clip);

                                          Toast toast = Toast.makeText(EntryPopup.this, "Copied to clipboard" , Toast.LENGTH_SHORT);
                                          toast.show();

                                          finish();
                                      }
                                  }
        );

                DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int  height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9) ,(int)(height*.5));

    }
}
