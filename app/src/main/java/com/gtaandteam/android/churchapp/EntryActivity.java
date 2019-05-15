package com.gtaandteam.android.churchapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EntryActivity extends AppCompatActivity {

    String message;
    String open,bible,bday,off,conf,hc1,hc2,hc3,dox;
  //  DatabaseReference UserDb1;

    View.OnLongClickListener LongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Toast toast= Toast.makeText(EntryActivity.this, "Long press detected",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
};

/*TODO: Whenever Add Entry button is pressed, it must store in the database, the respective value for the field along with a timestamp.
TODO: Each value should be stored separately
TODO: The OnLongClick fetches one of the values for that particular field from one of the old entries such that the old timestamp for that
TODO: particular value is more than a month ago.
 */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        final String LOG_TAG = this.getClass().getSimpleName();

        final EditText opening = (EditText)findViewById(R.id.EntryOpening);
        opening.setOnLongClickListener(LongClick);

        final EditText bibleReading = (EditText)findViewById(R.id.EntryBible);
        bibleReading.setOnLongClickListener(LongClick);

        final EditText thanksGiving = (EditText)findViewById(R.id.EntryThanksGiving);
        thanksGiving.setOnLongClickListener(LongClick);

        final EditText offertory = (EditText)findViewById(R.id.EntryOffertory);
        offertory.setOnLongClickListener(LongClick);

        final EditText confession = (EditText)findViewById(R.id.EntryConfession);
        confession.setOnLongClickListener(LongClick);

        final EditText HC1 = (EditText)findViewById(R.id.EntryHC1);
        HC1.setOnLongClickListener(LongClick);

        final EditText HC2 = (EditText)findViewById(R.id.EntryHC2);
        HC2.setOnLongClickListener(LongClick);

        final EditText HC3 = (EditText)findViewById(R.id.EntryHC3);
        HC3.setOnLongClickListener(LongClick);

        final EditText Doxology = (EditText)findViewById(R.id.EntryDoxology);
        Doxology.setOnLongClickListener(LongClick);

        final Button AddEntry = (Button)findViewById(R.id.EntryAddEntry);

        Date startDate = Calendar.getInstance().getTime();
        //String rName=FbAuth.getCurrentUser().getDisplayName();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String Date = format.format(startDate);

        AddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open=opening.getText().toString();
                bible=bibleReading.getText().toString();
                bday=thanksGiving.getText().toString();
                off=offertory.getText().toString();
                conf=confession.getText().toString();
                hc1=HC1.getText().toString();
                hc2=HC2.getText().toString();
                hc3=HC3.getText().toString();
                dox=Doxology.getText().toString();

                message = "Opening :" + open + "\nBible Reading : "
                        + bible + "\nThanksgiving : " + bday + "\nOffertory : "
                        + off + "\nConfession: " + conf
                        + "\nHC1: " + hc1 + "\nHC2 :"+ hc2 + "\nHC3 :"
                        + hc3 + "\nDoxology :" + dox ;


                /*opening.setText("");
                bibleReading.setText("");
                thanksGiving.setText("");
                offertory.setText("");
                confession.setText("");
                HC1.setText("");
                HC2.setText("");
                HC3.setText("");
                Doxology.setText("");*/
                DatabaseReference UserDb1;


                HashMap<String,String> Data= new HashMap<>();
                Data.put("Opening", open);
                Data.put("Bible", bible);
                Data.put("Bday", bday);
                Data.put("Off", off);
                Data.put("Conf",conf);
                Data.put("HC1",hc1);
                Data.put("HC2",hc2);
                Data.put("HC3",hc3);
                Data.put("Dox",dox);

                Toast.makeText(getApplicationContext(),"Hashmap Done",Toast.LENGTH_SHORT).show();
                android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
                int year = cal.get(android.icu.util.Calendar.YEAR);
                int month = cal.get(android.icu.util.Calendar.MONTH);
                int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);

                String date = day + "-" + (month+1) + "-" + year ;
                Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();
                UserDb1 = FirebaseDatabase.getInstance().getReference().child("SundayEntries");
                UserDb1.child(date).setValue(Data).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent  = new Intent(EntryActivity.this, EntryPopup.class);
                intent.putExtra("EXTRA_SESSION_ID", message);
                startActivity(intent);
            }
        });
    }
}
