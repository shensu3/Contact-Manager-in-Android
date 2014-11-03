/*@Author : Sushen Kumar Manchukanti
 * Contact Manager in android.
 */
package com.example.contactmanager;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
//class that does all the back end dirty work for the user interface.
public class support {
@SuppressWarnings("static-access")
public static ArrayList<String> list =new ArrayList<String>();			//list arraylist holds all the data from the file.
//called when deleting a contact.
public static void delete(MainActivity context)
{
	if(context.position<0)				//check if the position is invalid
		return;
	fetch(context);						//load all the lines from the file into the list.
	list.remove(context.position);		//remove the particular line
	store(context);						//write data back into the file.
	Toast(context,"Deleted Contact");	//toast to the user.
	context.position=-1;				//reset position.
}
//Toast function shows a toast message to the user.
public static void Toast(Activity act,String text)
{
Context context = act.getApplicationContext();
int duration = android.widget.Toast.LENGTH_SHORT;
android.widget.Toast toast = android.widget.Toast.makeText(context, text, duration);
toast.show();
}	

//called when edit activity wants to show all the details of selected contact in the fields.
public static void display(EditActivity context)
{
	fetch(context);											//load data from the file into the list.
	String[] str=list.get(context.position).split("\\|");	//split the data with delimiter "|"
	context.fname.setText(str[0]);							//set fields
	context.lname.setText(str[1]);							//
	context.ph.setText(str[2]);								//
	context.email.setText(str[3]);							//
	list.clear();											//clear the list.
}
//called when user clicks save in the edit activity.
public static void edit(EditActivity context)
{
	//check if any field is null.
	if(context.fname.getText().toString().equals("") || context.lname.getText().toString().equals("") ||
	   context.ph.getText().toString().equals("") || context.email.getText().toString().equals(""))
		{
		Toast(context,"a field cannot be left empty");
		return;
		}
	//construct the line.
	String temp = context.fname.getText()+"|"+context.lname.getText()+"|"+context.ph.getText()+"|"+context.email.getText();
	fetch(context);						//get all lines of the file,					
	list.set(context.position,temp);	//modify the selected line with the new one we just created. 
	store(context);						//store all the data back into the file.
	Toast(context,"Contact Edited");	//toast to the user.
	context.finish();					//kill the activity.
}
//fetches all the lines from the file and puts it into the list.
public static void fetch(Activity context)
{
	try {
		list.clear();
		File newFolder = context.getFilesDir();
		if (!newFolder.isDirectory())
		{
		newFolder.mkdir();
		}
		File file = new File(newFolder.getAbsolutePath() + File.separator + "/" + "contacts.txt");
	    if(!file.exists())
	    file.createNewFile();
	    Scanner in = new Scanner(file);
	    while (in.hasNextLine())
		  	{
		  		list.add(in.nextLine());
		  	}
		}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
}
//writes all the data from the list into the file.
public static void store(Activity context)
{
	try {
	File newFolder = context.getFilesDir();
	if (!newFolder.isDirectory())
	{
	newFolder.mkdir();
	}
	File file = new File(newFolder.getAbsolutePath() + File.separator + "/" + "contacts.txt");
    if(!file.exists())
    file.createNewFile();
    PrintWriter out = new PrintWriter(file);
    for(int i=0;i<list.size();i++)
    	out.println(list.get(i));
    out.close();
    list.clear();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}

}
