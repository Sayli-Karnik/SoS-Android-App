package com.example.piyu.navdrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class Edit_msgActivity extends AppCompatActivity {
    private EditText ed1, ed2;
    private Button b1;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Alertmsg = "alertKey";
    public static final String Safetymsg = "safetyKey";
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_msg);

        //android.support.v7.app.ActionBar bar = getSupportActionBar();
        //ColorDrawable colorDrawable = new ColorDrawable(Color.rgb(179, 0, 0));
       // bar.setBackgroundDrawable(colorDrawable);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setContentView(R.layout.content_edit_msg);

        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText1);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        //android.support.v7.app.ActionBar bar = getSupportActionBar();
// bar.setBackgroundDrawable(new ColorDrawable(""));
        //ColorDrawable colorDrawable = new ColorDrawable(Color.rgb(179, 0, 0));
        //bar.setBackgroundDrawable(colorDrawable);

        b1 = (Button) findViewById(R.id.button);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String am = ed1.getText().toString();
                String sm = ed2.getText().toString();

                if (am.isEmpty() || sm.isEmpty())
                    Toast.makeText(Edit_msgActivity.this, "Enter the messages.", Toast.LENGTH_LONG).show();

                else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    editor.putString("Alertmsg", am);
                    editor.putString("Safetymsg", sm);
                    editor.commit();
                    Toast.makeText(Edit_msgActivity.this, "Your Mesages are stored !!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}