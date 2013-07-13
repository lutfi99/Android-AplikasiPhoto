package com.example.studio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class LoginDataBaseAdapter {
	
	static final String DATABASE_NAME = "login.db";
	static final int DATABASE_VERSION = 1;
	public static final int NAME_COLUMN = 1;
	
	static final String DATABASE_CREATE = "CREATE TABLE "+"LOGIN"+"("+"ID"+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ "USERNAME TEXT NOT NULL,PASSWORD TEXT NOT NULL, NAME TEXT NOT NULL, EMAIL TEXT NOT NULL, PHONE TEXT NOT NULL, IMAGE BLOB NOT NULL);";
	
	public SQLiteDatabase db;
	
	private final Context context;
	
	private DataBaseHelper dbHelper;
	
	public LoginDataBaseAdapter(Context _context) {
		context = _context;
		dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public LoginDataBaseAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	/*public void read() {
		db = dbHelper.getReadableDatabase();
	}*/
	
	public void close() {
		db.close();
	}
	
	public SQLiteDatabase getDatabaseInstance() {
		return db;
	}
	
	public void insertEntry(String userName, String password, String name, String email, String phone, byte[] image/*Bitmap image*/) {
		//ByteArrayOutputStream out = new ByteArrayOutputStream();
        //image.compress(Bitmap.CompressFormat.PNG, 100, out);
        
		ContentValues newValues = new ContentValues();
		
		newValues.put("USERNAME", userName);
		newValues.put("PASSWORD", password);
		newValues.put("NAME", name);
		newValues.put("EMAIL", email);
		newValues.put("PHONE", phone);
		newValues.put("IMAGE", image);
		
		try {
			db.insert("LOGIN", null, newValues);
			Toast.makeText(context, "Succesfully", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int deleteEntry(String UserName) {
		String where="USERNAME=?";
		int numberOFEntriesDeleted = db.delete("LOGIN", where, new String[]{UserName});
		return numberOFEntriesDeleted;
	}
	
	public String getSingleEntry(String userName) {
		Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
		//username tidak ada
		if(cursor.getCount()<1) {
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
		cursor.close();
		return password;
	}
	
	public void updateEntry(String userName, String name, String email, String phone) {
		
		ContentValues updatedValues = new ContentValues();
		
		//updatedValues.put("USERNAME", userName);
		//updatedValues.put("PASSWORD", password);
		updatedValues.put("NAME", name);
		updatedValues.put("EMAIL", email);
		updatedValues.put("PHONE", phone);
		
		String where="USERNAME=?";
		db.update("LOGIN", updatedValues, where, new String[]{userName});
	}
	
	public void updatePass(String userName, String password) {
		
		ContentValues updatedValues = new ContentValues();
		
		updatedValues.put("PASSWORD", password);
		
		String where="USERNAME=?";
		db.update("LOGIN", updatedValues, where, new String[]{userName});
	}
	
	public void updatePhoto(String userName, byte[] image) {
		
		ContentValues updatedValues = new ContentValues();
		
		updatedValues.put("IMAGE", image);
		
		String where="USERNAME=?";
		db.update("LOGIN", updatedValues, where, new String[]{userName});
	}
	
	public String getEmail(String email) {
		Cursor cursor = db.query("LOGIN", null, " EMAIL=?", new String[]{email}, null, null, null);
		//email tidak ada
		if(cursor.getCount()<1) {
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
		cursor.close();
		return password;
	}
	
	public byte[] getPhoto(String userName) {
		Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
		
		cursor.moveToFirst();
		byte[] image = cursor.getBlob(cursor.getColumnIndex("IMAGE"));
		return image;
	}
	
	public String getName(String userName) {
		Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
		//username tidak ada
		if(cursor.getCount()<1) {
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String name = cursor.getString(cursor.getColumnIndex("NAME"));
		cursor.close();
		return name;
	}
	
	public String getPass(String userName) {
		Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
		//username tidak ada
		if(cursor.getCount()<1) {
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String password = cursor.getString(cursor.getColumnIndex("PASSWORD"));
		cursor.close();
		return password;
	}
	
	public String getPhone(String userName) {
		Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
		//username tidak ada
		if(cursor.getCount()<1) {
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String phone = cursor.getString(cursor.getColumnIndex("PHONE"));
		cursor.close();
		return phone;
	}
	
	public String getEmailEdit(String userName) {
		Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
		//username tidak ada
		if(cursor.getCount()<1) {
			cursor.close();
			return "NOT EXIST";
		}
		cursor.moveToFirst();
		String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
		cursor.close();
		return email;
	}
	
	/*public Cursor getProfile(String userName) {
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
		return cursor;
	}*/
}
