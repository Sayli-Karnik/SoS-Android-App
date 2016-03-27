package com.example.piyu.navdrawer;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
//This activity creates an adapter which is used to populate the list view with contacts from the phone with a check box besides each.
//Also we make a file called Emergency_Contacts which will store the contact names.
public class CustomAdapter extends BaseAdapter {

    ArrayList<String> result;
    Context context;int i=0;
    int [] imageId;
    public SharedPreferences sharedpreferences;
    public SharedPreferences.Editor editor;
    //static int emergencycontacts[];
    private static LayoutInflater inflater=null;
    public CustomAdapter(ImportContacts mainActivity, ArrayList<String> list) {
        // TODO Auto-generated constructor stub
        result=list;
        context=mainActivity;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        sharedpreferences = context.getSharedPreferences("Emergency_Contacts", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
//holder is a class which contains the name of contact and checkbox
    public class Holder
    {
        TextView textview1;
        CheckBox checkBox1;

    }
    //getView method returns a view of the holder. So firstly, we have to inflate the xml used for each row, then populate the
    //view with contact name and checkbox.
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        String phoneNumber;
        View rowView;
        rowView = inflater.inflate(R.layout.customcontactslist, null);
        holder.textview1=(TextView) rowView.findViewById(R.id.textView1);
        //holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.textview1.setText(result.get(position));
        holder.checkBox1=(CheckBox)rowView.findViewById(R.id.checkBox1);
       // Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        //phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

       rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                holder.checkBox1.setChecked(true);
            editor.putString("name"+i,result.get(position));
                editor.commit();
                Toast.makeText(context, "You Clicked " +sharedpreferences.getString("name"+i,"Yash") , Toast.LENGTH_SHORT).show();
                i++;
             //   System.out.print("saylii"+sharedpreferences.getAll());
            }
        });
        return rowView;
    }
}
