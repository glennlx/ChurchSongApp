package com.gtaandteam.android.churchapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EntryActivity extends AppCompatActivity {

    String message;
    //to fetch todays date//
    String todaysDate;
    String open,bible,bday,off,conf,hc1,hc2,hc3,dox;
    public HashMap<String,String> Data;
    DatabaseReference UserDb71;
    Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7,spinner8,spinner9;

    View.OnLongClickListener LongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            
            
            String key= ((EditText)v).getText().toString();

            //fetching all entries//

            UserDb71 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child(key);
            UserDb71.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    HashMap<String, String> allEntries = (HashMap<String, String>) dataSnapshot.getValue();

                    //filtering based on date to pick data with date older than a month..

                    List older = new ArrayList<String>();

                    for(String keys: allEntries.keySet()){

                        String date=allEntries.get(keys);

                        int yearIndex=date.lastIndexOf("-");
                        int monthIndex=date.indexOf("-");
                        int todaysYearIndex=todaysDate.lastIndexOf("-");
                        int todaysMonthIndex=todaysDate.indexOf("-");

                        int year=Integer.parseInt(date.substring(yearIndex+1, date.length()));
                        int month=Integer.parseInt(date.substring(monthIndex+1, yearIndex));

                        int todaysYear=Integer.parseInt(todaysDate.substring(todaysYearIndex+1, todaysDate.length()));
                        int todaysMonth=Integer.parseInt(todaysDate.substring(todaysMonthIndex+1, todaysYearIndex));

                        /*comparing year
                          if on same year
                          then compare month
                        */
                        if(todaysYear-year==0){
                            if(todaysMonth-month >= 1)
                                older.add(keys);
                        }
                        else{
                            older.add(keys);
                        }

                    }

                    //pick a random index from 
                    Random random= new Random();
                    int pickIndex= random.nextInt(older.size());

                    //to show selected key//
                    android.widget.Toast.makeText(getApplicationContext(), "selected: "+older.get(pickIndex), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




            
            
            /*Toast toast= Toast.makeText(EntryActivity.this, "Long press detected",Toast.LENGTH_SHORT);
            toast.show();*/
            return false;
        }
};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        
        //fetching todays date//
        android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
        int year = cal.get(android.icu.util.Calendar.YEAR);
        int month = cal.get(android.icu.util.Calendar.MONTH);
        int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);
        todaysDate = day + "-" + (month+1) + "-" + year ;


        final String LOG_TAG = this.getClass().getSimpleName();


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        spinner5 = (Spinner) findViewById(R.id.spinner5);
        spinner6 = (Spinner) findViewById(R.id.spinner6);
        spinner7 = (Spinner) findViewById(R.id.spinner7);
        spinner8 = (Spinner) findViewById(R.id.spinner8);
        spinner9 = (Spinner) findViewById(R.id.spinner9);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.TypeOfSong, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        spinner6.setAdapter(adapter);
        spinner7.setAdapter(adapter);
        spinner8.setAdapter(adapter);
        spinner9.setAdapter(adapter);



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

                //Toast.makeText(EntryActivity.this, spinner1.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                open = spinner1.getSelectedItem().toString() + " " + opening.getText().toString();
                bible = spinner2.getSelectedItem().toString() + " " + bibleReading.getText().toString();
                bday = spinner3.getSelectedItem().toString() + " " + thanksGiving.getText().toString();
                off = spinner4.getSelectedItem().toString() + " " + offertory.getText().toString();
                conf = spinner5.getSelectedItem().toString() + " " + confession.getText().toString();
                hc1 = spinner6.getSelectedItem().toString() + " " + HC1.getText().toString();
                hc2 = spinner7.getSelectedItem().toString() + " " + HC2.getText().toString();
                hc3 = spinner8.getSelectedItem().toString() + " " + HC3.getText().toString();
                dox = spinner9.getSelectedItem().toString() + " " + Doxology.getText().toString();



                message = "Opening :\t\t\t\t\t\t\t" + open
                        + "\nBible Reading :\t\t" + bible
                        + "\nThanksgiving :\t\t" + bday
                        + "\nOffertory :\t\t\t\t\t\t\t" + off
                        + "\nConfession :\t\t\t\t" + conf
                        + "\nHC1 :\t\t\t\t\t\t\t\t\t\t\t" + hc1
                        + "\nHC2 :\t\t\t\t\t\t\t\t\t\t\t"+ hc2
                        + "\nHC3 :\t\t\t\t\t\t\t\t\t\t\t" + hc3
                        + "\nDoxology :\t\t\t\t\t\t" + dox ;


                /*opening.setText("");
                bibleReading.setText("");
                thanksGiving.setText("");
                offertory.setText("");
                confession.setText("");
                HC1.setText("");
                HC2.setText("");
                HC3.setText("");
                Doxology.setText("");*/
                DatabaseReference UserDb1,UserDb2,UserDb3,UserDb4,UserDb5,UserDb6,UserDb7,UserDb8,UserDb9,UserDb10;


                Data= new HashMap<>();
                Data.put("Opening", open);
                Data.put("Bible", bible);
                Data.put("Thanksgiving", bday);
                Data.put("Offertory", off);
                Data.put("Confession",conf);
                Data.put("HC1",hc1);
                Data.put("HC2",hc2);
                Data.put("HC3",hc3);
                Data.put("Doxology",dox);

                //Toast.makeText(getApplicationContext(),"Hashmap Done",Toast.LENGTH_SHORT).show();
                android.icu.util.Calendar cal = android.icu.util.Calendar.getInstance();
                int year = cal.get(android.icu.util.Calendar.YEAR);
                int month = cal.get(android.icu.util.Calendar.MONTH);
                int day = cal.get(android.icu.util.Calendar.DAY_OF_MONTH);

                String date = day + "-" + (month+1) + "-" + year ;
                //Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT).show();
                UserDb1 = FirebaseDatabase.getInstance().getReference().child("Sunday Entries");
                UserDb1.child(date).setValue(Data).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb2 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("Opening");
                UserDb2.child(Data.get("Opening")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb3= FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("Bible");
                UserDb3.child(Data.get("Bible")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb4 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("Thanksgiving");
                UserDb4.child(Data.get("Thanksgiving")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb5 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("Offertory");
                UserDb5.child(Data.get("Offertory")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb6 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("Confession");
                UserDb6.child(Data.get("Confession")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb7 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("HC1");
                UserDb7.child(Data.get("HC1")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb8 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("HC2");
                UserDb8.child(Data.get("HC2")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb9 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("HC3");
                UserDb9.child(Data.get("HC3")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });

                UserDb10 = FirebaseDatabase.getInstance().getReference().child("Malayalam Service").child("Doxology");
                UserDb10.child(Data.get("Doxology")).setValue(date).addOnCompleteListener(EntryActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(getApplicationContext(),"Inserted To Database",Toast.LENGTH_SHORT).show();
                    }
                });



                Intent intent  = new Intent(EntryActivity.this, EntryPopup.class);
                intent.putExtra("EXTRA_SESSION_ID", message);
                startActivity(intent);
            }
        });
    }
}
