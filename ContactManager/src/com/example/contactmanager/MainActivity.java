/*@Author : Sushen Kumar Manchukanti
 * Contact Manager in android.
 */

package com.example.contactmanager;

import android.app.Activity;
import android.app.AlertDialog;

import java.util.*;
import java.io.*;
import android.content.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ActionBar;
import android.net.Uri;
import android.net.UrlQuerySanitizer.ValueSanitizer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//Main activity. Displays list of contacts as well as actions in the action bar.
public class MainActivity extends Activity 
{
	public Context context = this;						//Variable of the class activity
	MainActivity act =this;								//	''
	public static int position=-1;						//position of listvie selected by the suer 
	public static ListView lv;							//list view variable
	
	@Override
	//code executed as the activity is created.
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String[] values = FileIO.load(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, values);
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(adapter);											//sets the adapter for the listview and the data
		//Listener. Invoked whenever user clicks ona contact. makes position variable point to the row clicked.
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				MainActivity.position=position;
			}
			}); 
	}

	@Override
	//inflates the menus. 
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actions, menu);
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	//Here we call specific functions according to what menu action was clicked.
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (item.getItemId()) {
        case R.id.action_add:
            add();
            return true;
        case R.id.action_delete:
        	if(position>=0)
        		deleteConfirmation();
        	return true;
        case R.id.action_edit:
        	if(position>=0)
        		edit();
        	return true;
        case R.id.action_call:
        	if(position>=0)
        	call();
        	return true;
        case R.id.action_mail:
        	if(position>=0)
        	mail();
        	return true;
        default:
            return super.onOptionsItemSelected(item);
    }
	}
	//Brings to front add activity.
	public void add()
	{
		Intent intent = new Intent(this,SaveActivity.class);
		startActivity(intent);
	}
	//Dialog box for delete confirmation
	public void deleteConfirmation()
	{
		 AlertDialog.Builder alert = new AlertDialog.Builder(this);
		    alert.setTitle("Confirm");
		    alert.setMessage("Are you sure you want to delete this contact?");  //alert message

		    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {  //on clicking ok 
		        public void onClick(DialogInterface dialog, int whichButton) {
		        	support.delete(act);
		        	String[] values = FileIO.load(MainActivity.this);
		        	ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
		        			android.R.layout.simple_list_item_1, values);
		        				lv.setAdapter(adapter);
		        }
		    });

		    alert.setNegativeButton("Cancel", 
		        new DialogInterface.OnClickListener() {
   		            public void onClick(DialogInterface dialog, int whichButton) {
		            	return;
		            }
		        });
		    alert.show();
	}
	//bringing edit activity to front.
	public void edit()
	{
		Intent intent = new Intent(this,EditActivity.class);
		intent.putExtra("position",position);
		startActivity(intent);
	}
	@Override
	//on resuming the main activity refresh the listview reading the file again.
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String[] values = FileIO.load(MainActivity.this);
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
    			android.R.layout.simple_list_item_1, values);
    				lv.setAdapter(adapter);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	//Use a call intent to call the number selected by the user.
	public void call()
	{
		support.fetch(this);
		String[] temp = support.list.get(position).split("\\|");
		Intent callIntent = new Intent(Intent.ACTION_CALL);          
        callIntent.setData(Uri.parse("tel:"+temp[2]));          
        startActivity(callIntent);  
	}
	//Use a intent to mail the selected contact.
	public void mail()
	{
		support.fetch(this);
		String[] temp = support.list.get(position).split("\\|");
		String[] TO ={temp[3]};
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
	    emailIntent.setData(Uri.parse("mailto:"));
	    emailIntent.setType("text/plain");
	    emailIntent.putExtra(Intent.EXTRA_EMAIL,TO);
	    try {
	    	startActivity(emailIntent);
	    } catch (android.content.ActivityNotFoundException ex) {
	        support.Toast(this, "There are no email clients installed.");
	    }
	}
}
