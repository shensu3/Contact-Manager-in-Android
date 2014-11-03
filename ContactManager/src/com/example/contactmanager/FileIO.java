/*@Author : Sushen Kumar Manchukanti
 * Contact Manager in android.
 */
package com.example.contactmanager;

import java.io.File;
import java.util.Scanner;
import java.io.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class FileIO 
{
	//loads the data from the file into a String array called by main activity
	public static String[] load(Activity context)
	{
		String[] values={"could not load data"}; 	//default failure message
		try {										//read file code.
		File newFolder = context.getFilesDir();
		if (!newFolder.isDirectory())
		{
		newFolder.mkdir();
		}
		File file = new File(newFolder.getAbsolutePath() + File.separator + "/" + "contacts.txt");
	    if(!file.exists())
	    file.createNewFile();
	    Scanner in = new Scanner(file);
		StringBuilder sb= new StringBuilder();  
	    while (in.hasNextLine())					//parse lines using "-" as delimiter.
		  	{
		  	sb.append(in.nextLine());
		  	sb.append("-");
		  	}
	    values = sb.toString().split("-");			//parse...
	    for(int i=0;i<values.length;i++)
	    	values[i]=values[i].replace("|"," ");	//parse each field of the contact using "|" delimeter...
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return values;								//return String array
	}
	//save called by saveactivity.
	public static void save(SaveActivity context)
	{
		//default failure checking.
	if(context.fname.getText().toString().equals("") || context.lname.getText().toString().equals("") ||
	   context.ph.getText().toString().equals("") || context.email.getText().toString().equals(""))
			{
			support.Toast(context,"a field cannot be left empty");
			return;
			}
		try 
		{
		File newFolder = context.getFilesDir();
		if (!newFolder.isDirectory())
			{
			newFolder.mkdir();
			}	
		File file = new File(newFolder.getAbsolutePath() + File.separator + "/" + "contacts.txt");
	    if(!file.exists())
	    file.createNewFile();
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
	    //write the line into file (append mode) with "|" in the middle of all fields..
	    out.println(context.fname.getText()+"|"+context.lname.getText()+"|"+context.ph.getText()+"|"+context.email.getText());
	    out.close();
	    //toast to the user.
	    support.Toast(context,"Saved data"); 
	    context.finish(); //activity must be closed.
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	//**delete everything in the file. Never used. Was used for testing the app**
	public static void clear(SaveActivity context)
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
	    out.print(""); //oops... 
	    out.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
