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
//Activity to edit fields of a selected user.
public class EditActivity extends Activity {
	public static int position;
	public static EditText fname;
	public static EditText lname;
	public static EditText ph;
	public static EditText email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		fname   = (EditText)findViewById(R.id.editText1);		//first name field
		lname   = (EditText)findViewById(R.id.editText2);		//last name field 
		ph      = (EditText)findViewById(R.id.editText3);		//phone number field
		email   = (EditText)findViewById(R.id.editText4);		//email field.
		Intent intent = getIntent();							//get the intent
		position = intent.getIntExtra("position",0);			//get the position variable that was sent along with intent
		support.display(this);									//display the fields of that particular contact that was selected.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	//called when user clicks on save.
	public void edit(View view)
	{
		support.edit(this);			//calls function to save data into file and close this application.
	}
}
