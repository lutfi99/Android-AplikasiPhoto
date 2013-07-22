package com.example.studio;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

@SuppressLint("NewApi")
public class TimelineActivity extends ListActivity implements
LoaderManager.LoaderCallbacks<Cursor>{
	private static final String[] PROJECTION = new String[] { "ID", "USERNAME" };
	private static final int LOADER_ID = 1;
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
	
	SimpleCursorAdapter adapter;
	Cursor cursor;
	
	private static final Uri CONTENT_URI = null;
	//LoginDataBaseAdapter loginDataBaseAdapter;
	
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle icicle) {
		    super.onCreate(icicle);
		    setContentView(R.layout.list_view);
		    
			LoginDataBaseAdapter db = new LoginDataBaseAdapter(this);
			db.open();
			
			cursor = db.test();
		    startManagingCursor(cursor); 
		    
		    adapter = new SimpleCursorAdapter(this, 
			R.layout.list_item_view, cursor, 
			new String[] {"USERNAME"},
			new int[] { R.id.textListView });
			setListAdapter(adapter);
			
			mCallbacks = this;
			LoaderManager lm = getLoaderManager();
		    lm.initLoader(LOADER_ID, null, mCallbacks);
	}
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return new CursorLoader(TimelineActivity.this, CONTENT_URI,
		        PROJECTION, null, null, null);
	}
	@SuppressLint("NewApi")
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		// TODO Auto-generated method stub
		switch (loader.getId()) {
	      case LOADER_ID:
	        // The asynchronous load is complete and the data
	        // is now available for use. Only now can we associate
	        // the queried Cursor with the SimpleCursorAdapter.
	        adapter.swapCursor(cursor);
	        break;
	    }
	}
	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		adapter.swapCursor(null);
	}
}
