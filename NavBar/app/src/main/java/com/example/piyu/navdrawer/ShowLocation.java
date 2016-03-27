package com.example.piyu.navdrawer;
/*
* This activity shows your current location address and sends the location to your emergency contacts.
* */
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.location.Location;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.provider.Settings;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
import android.widget.Toast;

public class ShowLocation extends AppCompatActivity {
    String result="My Location\n";
    Button btnGPSShowLocation;
    Button btnShowAddress;
    TextView tvAddress;
    public static final String MyPREFERENCES = "MyPrefs" ;
    AppLocationService appLocationService;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
// bar.setBackgroundDrawable(new ColorDrawable(""));
        ColorDrawable colorDrawable = new ColorDrawable(Color.rgb(179, 0, 0));
       // bar.setBackgroundDrawable(colorDrawable);

        tvAddress = (TextView) findViewById(R.id.tvAddress);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        editor = sharedpreferences.edit();

        appLocationService = new AppLocationService(
                ShowLocation.this);

        btnShowAddress = (Button) findViewById(R.id.btnShowAddress);
        btnShowAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location location = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);

                //you can hard-code the lat & long if you have issues with getting it
                //remove the below if-condition and use the following couple of lines
                //double latitude = 37.422005;
                //double longitude = -122.084095

               if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());
                   result = locationAddress.toString();
                }// else {
                  //  showSettingsAlert();
                //}

                SharedPreferences sharedpreferences = getSharedPreferences("Emergency_Numbers", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedpreferences.edit();
                int size_of_emergency_contacts = sharedpreferences.getAll().size();
                int j=0;
                                                                                                                                        result = tvAddress.getText().toString();

                SmsManager smsManager = SmsManager.getDefault();
                try {

                    while (size_of_emergency_contacts > 0) {
                        String phoneNo =sharedpreferences.getString("phno"+j,"9869105188");
                        System.out.print("phoneNo1"+phoneNo);
                        j++;
                        smsManager.sendTextMessage(phoneNo, null, "Address:"+result, null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                        size_of_emergency_contacts--;
                    }
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                ShowLocation.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        ShowLocation.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
                                                                                                                                           String locationAddress="Bhavans Campus,Munshi Nagar,Andheri-west,400058,Mumbai,Maharashtra";
            switch (message.what) {
                case 1:

                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");

                    result=result+locationAddress;
                    editor.putString("locationAddress", result);
                    SharedPreferences sharedpreferences = getSharedPreferences("Emergency_Numbers", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =  sharedpreferences.edit();
                    int size_of_emergency_contacts = sharedpreferences.getAll().size();
                    String result = tvAddress.getText().toString();
                    System.out.print(size_of_emergency_contacts+"size2..\n");
                    int j=0;
//
                break;
                default:

               //     locationAddress = "Bhavans Campus,Munshi Nagar,Andheri-west,400058,Mumbai,Maharashtra";
               /*     sharedpreferences = getSharedPreferences("Emergency_Numbers", Context.MODE_PRIVATE);
                    editor =  sharedpreferences.edit();
                    size_of_emergency_contacts = sharedpreferences.getAll().size();
                    j=0;
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        while (size_of_emergency_contacts > 0) {
                            String phoneNo =sharedpreferences.getString("phno"+j,"9869105188");
                            j++;
                            smsManager.sendTextMessage(phoneNo, null, locationAddress, null, null);
                            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                            size_of_emergency_contacts--;
                        }
                    }
                    catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }*/

            }//locationAddress="Bhavans Campus,Munshi Nagar,Andheri-west,400058,Mumbai,Maharashtra";
            //tvAddress.setText(locationAddress);
        }
    }
}