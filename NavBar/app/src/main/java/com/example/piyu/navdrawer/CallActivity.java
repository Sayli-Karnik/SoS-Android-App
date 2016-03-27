package com.example.piyu.navdrawer;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
//This activity performs calling action.
public class CallActivity extends AppCompatActivity {
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        b1 = (Button) findViewById(R.id.btncall);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.rgb(179, 0, 0));
        bar.setBackgroundDrawable(colorDrawable);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //This method is called on button click event of the button  'Call'. We take the shared preference file called Emergency Numbers
    //where we have stored all the emergency contacts. It makes an implicit intent call to the ACTION_CALL which handles the
    //call.
    public void call(View view) {
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("Emergency_Numbers", Context.MODE_PRIVATE);
        String number = sharedPreferences.getString("phno0","8888799046");
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
         try {

            startActivity(in);
            Toast.makeText(getApplicationContext(),"Call sent",Toast.LENGTH_LONG).show();
        }

        catch (ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(), "Call not sent", Toast.LENGTH_SHORT).show();
        }
    }


}
