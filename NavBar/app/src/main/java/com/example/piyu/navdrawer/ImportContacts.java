package com.example.piyu.navdrawer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
//This activity is used to make a record of the user's emergency contact numbers. Two shared preference files Emergency_Contacts and
//Emergency_Numbers are used to store the names and numbers respectively. Emergency_Contacts was already made in CustomAdapter.
//We map the names to their numbers.
public class ImportContacts extends Activity {
    ListView contactslist;
    ArrayList<String> list = new ArrayList<String>();
    public SharedPreferences sharedpreferences;
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedpreferences1;
    public SharedPreferences.Editor editor1;
    ArrayAdapter<String> adapter;
    String phno[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_contacts);
        contactslist = (ListView) findViewById(R.id.listView1);
        int j = 0, k = 0;
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {

            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //System.out.println("Name " + name);
                if (Integer.parseInt(cur.getString
                        (cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        // System.out.print("\n"+cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    //CustomAdapter.Holder holder= CustomAdapter.Holder(name,phno);
                    list.add(name);
                }
            }
        }
        cur.close();

        //adapter=new ArrayAdapter<String>(this,R.layout.customcontactslist,con);
        contactslist.setAdapter(new CustomAdapter(ImportContacts.this, list));

        //contactslist.setAdapter(adapter);
    }
// We iterate through the shared prefs file called Emergency_Contacts to get the name and pass it to the method getPhoneNumber().
    public void savecontacts(View view) {
        int j = 0;
        sharedpreferences = getSharedPreferences("Emergency_Contacts", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        sharedpreferences1 = getSharedPreferences("Emergency_Numbers", Context.MODE_PRIVATE);
        //editor1 = sharedpreferences.edit();
        String id = "";
        int i = 0;
        String phoneNumber = "9820379688";
        ContentResolver cr = getContentResolver();
        int size_of_emergency_contacts = sharedpreferences.getAll().size();
        //Cursor phones;
        System.out.print("\nbloooh  "+size_of_emergency_contacts);
        while (size_of_emergency_contacts > 0) {
            editor1 = sharedpreferences1.edit();
            String name = sharedpreferences.getString("name" + i, "Ankita");
            i++;
           // System.out.print("Names " + i + name);
          //  System.out.println("num" + getPhoneNumber(name, this));
            phoneNumber = getPhoneNumber(name,this);
            editor1.putString("phno" + j, phoneNumber);
            editor1.commit();
            System.out.print("\nbloooh"+(i-1) + sharedpreferences1.getString("phno"+j,"noooo"));
            //System.out.println("\n" + size_of_emergency_contacts + "\n");
            j++;
            size_of_emergency_contacts--;

        }

  //        System.out.print("say" + sharedpreferences.getString("name0", "blah"));
  //      System.out.print("say" + sharedpreferences.getString("name1", "blah"));
        editor1 = sharedpreferences1.edit();
        System.out.println("yes" + sharedpreferences1.getString("phno0", "blah"));
        System.out.println("yes" + sharedpreferences1.getString("phno1","blah"));
        System.out.println("yes" + sharedpreferences1.getString("phno2","blah"));

    }


    boolean in(int j,int[] arr)
    {
        for(int i=0;i<arr.length;i++)
            if(j==arr[i])return true;
        return  false;
    }

    //This method will search for the name in the contact list and get the number associated with it to return as a String back
    //to its caller in saveContacts method.
    public String getPhoneNumber(String name, Context context) {
        String ret = null;
        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";
        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, selection, null, null);
        if (c.moveToFirst()) {
            ret = c.getString(0);
        }
        c.close();
        if(ret==null)
            ret = "Unsaved";
        return ret;
    }
}