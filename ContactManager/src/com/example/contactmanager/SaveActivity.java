/*@Author : Sushen Kumar Manchukanti
 * Contact Manager in android.
 */
package com.example.contactmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
//Save activity. called when user wants to add a contact.
public class SaveActivity extends Activity {
	public static EditText fname;
	public static EditText lname;
	public static EditText ph;
	public static EditText email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		fname   = (EditText)findViewById(R.id.editText1);		//
		lname   = (EditText)findViewById(R.id.editText2);		//
		ph      = (EditText)findViewById(R.id.editText3);		//setting the fields
		email   = (EditText)findViewById(R.id.editText4);		//
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}
	//called when users clicks on save 
	public void save(View view)
	{
		FileIO.save(this); // call the fucntion to write data into the file.
	}
}
