package com.example.piyu.navdrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

//This activity provides the navigation drawer and emergency buttons such as the send alert msg, send safety msg and show my location.
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
// bar.setBackgroundDrawable(new ColorDrawable(""));
       // ColorDrawable colorDrawable = new ColorDrawable(Color.rgb(179,0,0));
     //   bar.setBackgroundDrawable(colorDrawable);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent in = new Intent(MainActivity.this,ShowLocation.class);
                startActivity(in);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    //To send alert msg
   /* public void Msg(View view) {

        Log.i("Send SMS", "");
        String phoneNo = txtphoneNo.getText().toString();
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String alert = (sharedpreferences.getString("Alertmsg", null));
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, alert, null, null);
            Toast.makeText(getApplicationContext(), "Alert SMS sent.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent myIntent = new Intent(MainActivity.this, ImportContacts.class);
            MainActivity.this.startActivity(myIntent);

        } else if (id == R.id.nav_slideshow) {
            Intent myIntent = new Intent(MainActivity.this, SirenActivity.class);
            MainActivity.this.startActivity(myIntent);


        } else if (id == R.id.nav_manage) {
            Intent myIntent = new Intent(MainActivity.this, DraftSms.class);
            MainActivity.this.startActivity(myIntent);

        } else if (id == R.id.nav_call) {
            Intent myIntent = new Intent(MainActivity.this, CallActivity.class);
            MainActivity.this.startActivity(myIntent);

        }
        else if (id == R.id.about_us) {
            Intent myIntent = new Intent(MainActivity.this, AboutUs.class);
            MainActivity.this.startActivity(myIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //on click of show my location button, the method is called.
    public void callmapactivity(View v)
    {
        Intent in = new Intent(MainActivity.this,MapsActivity.class);
        startActivity(in);

    }
    //on click of send alert msg button, this method is called.
    public void onAlertClick(View v)
    {

    SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    String Alertmsg = sharedpreferences.getString("Alertmsg","Help");
    sendSms(Alertmsg);
    }
    //on click of send safety msg button, this method is called.
    public void onSafeClick(View v)
    {

        SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String Alertmsg = sharedpreferences.getString("Safetymsg","Reached safely");
        sendSms(Alertmsg);
    }

    //this method is used to shoot sms
    public void sendSms(String msg)
    {SharedPreferences sharedpreferences = getSharedPreferences("Emergency_Numbers", Context.MODE_PRIVATE);
     SharedPreferences.Editor editor =  sharedpreferences.edit();
        int size_of_emergency_contacts = sharedpreferences.getAll().size();
        int j=0;
        try {
            SmsManager smsManager = SmsManager.getDefault();
            while (size_of_emergency_contacts > 0) {
                String phoneNo =sharedpreferences.getString("phno"+j,"9869105188");
                j++;
                smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                size_of_emergency_contacts--;
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
