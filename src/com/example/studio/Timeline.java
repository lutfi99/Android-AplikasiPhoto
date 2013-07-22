package com.example.studio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

@SuppressLint("NewApi")
public class Timeline extends Activity {

	
	SimpleCursorAdapter adapter;
	Cursor cursor;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		LoginDataBaseAdapter db = new LoginDataBaseAdapter(this);
		db.open();
		
		ListView list = (ListView) findViewById(R.id.list);
		cursor = db.test();
	    startManagingCursor(cursor); 
		
		adapter = new SimpleCursorAdapter(this,
				R.layout.list_item_view,
				cursor,
				new String[]{"NAME"},
				new int[] {R.id.textListView});
		list.setAdapter(adapter);
		
	}
}
