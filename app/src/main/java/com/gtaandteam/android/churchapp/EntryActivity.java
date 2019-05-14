package com.gtaandteam.android.churchapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EntryActivity extends AppCompatActivity {

    String message;

    View.OnLongClickListener LongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            Toast toast= Toast.makeText(EntryActivity.this, "Long press detected",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

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

        AddEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message = "Opening :" +opening.getText().toString() + "\nBible Reading : "
                        + bibleReading.getText().toString()+ "\nThanksgiving : " + thanksGiving.getText().toString() + "\nOffertory : "
                        + offertory.getText().toString() + "\nConfession: " + confession.getText().toString()
                        + "\nHC1: " + HC1.getText().toString() + "\nHC2 :"+ HC2.getText().toString() + "\nHC3 :"
                        + HC3.getText().toString() + "\nDoxology :" +Doxology.getText().toString();

                opening.setText("");
                bibleReading.setText("");
                thanksGiving.setText("");
                offertory.setText("");
                confession.setText("");
                HC1.setText("");
                HC2.setText("");
                HC3.setText("");
                Doxology.setText("");

                Intent intent  = new Intent(EntryActivity.this, EntryPopup.class);
                intent.putExtra("EXTRA_SESSION_ID", message);
                startActivity(intent);
            }
        });
    }
}
