package com.gtaandteam.android.churchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView entryTextView = (TextView)findViewById(R.id.Enter);
        entryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(MainActivity.this, EntryActivity.class);

                // Start the new activity
                startActivity(numbersIntent);
            }
        });


        TextView searchTextView = (TextView)findViewById(R.id.search);
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(MainActivity.this, SearchActivity.class);

                // Start the new activity
                startActivity(numbersIntent);
            }
        });

        TextView AddTextView = (TextView)findViewById(R.id.Add);
          AddTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(MainActivity.this, AddActivity.class);

                // Start the new activity
                startActivity(numbersIntent);
            }
        });

    }
}









































































































































































