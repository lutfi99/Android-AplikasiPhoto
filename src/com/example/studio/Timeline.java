package com.example.studio;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

@SuppressLint("NewApi")
public class Timeline extends ListActivity {

	private static final String E = null;
	LoginDataBaseAdapter loginDataBaseAdapter;
	Cursor cursor;
	ListPhoto list;
	List<ListPhoto> image;
	AdapterCustomTimeline adapter;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.list_view);

		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		/*List<String> txt = new ArrayList<String>();
		if (cursor.getCount() > 0) {

			for(int i=0;i<cursor.getCount();i++){
				cursor.moveToNext();
				txt.add(cursor.getString(1));
			}
			
		}*/
		/*ArrayList<Map<String, String>> list = buildData();
		String[] from = {"user", "id"};
		int[] to = {R.id.textListView, R.id.imageListView};
		
		SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.list_item_view, from, to);*/
		
		/*SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.list_item_view, cursor,
				from, to);*/
		
		//setListAdapter(adapter);
		
		//dataAdapter = new CustomCursorAdapter(this, cursor, 0);
	    //ListView listview = (ListView) findViewById(R.id.list);
	   // listview.setAdapter(dataAdapter);
		
//		cursor.moveToFirst();
//		byte[] byteImg = cursor.getBlob(cursor.getColumnIndex("IMAGE"));
		

//		cursor = loginDataBaseAdapter.test();
//		image = new ArrayList<ListPhoto>();
//		
//		if (cursor.getCount() > 0) {
//
//			for(int i=0;i<cursor.getCount();i++){
//				cursor.moveToNext();
//				list = new ListPhoto();
//				//Bitmap bm = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
//				list.setImage(cursor.getString(2));
//				list.setUser(cursor.getString(1));
//				image.add(0, list);
//				//image.add(bm);
//			}
//			
//		}
//		adapter = new AdapterCustomTimeline(this, R.layout.list_item_timeline, R.id.imageListView, image);
//		setListAdapter(adapter); 
//		
//		adapter.notifyDataSetChanged();
		
		setupData();
		
		/*		startManagingCursor(cursor);

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.list_item_view, cursor,
				new String[] {cursor.getString(cursor.getColumnIndex("USERNAME"))}, new int[] { R.id.textListView });*/
	}

//	private ArrayList<Map<String, String>> buildData() {
//		//cursor.moveToFirst();
//		//byte[] byteImg = cursor.getBlob(cursor.getColumnIndex("IMAGE"));
//		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
//		if (cursor.getCount() > 0) {
//
//			for(int i=0;i<cursor.getCount();i++){
//				cursor.moveToNext();
//				//Bitmap bm = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
//				list.add(putData(cursor.getString(1), cursor.getString(0)));
//			}
//		}
//		return list;
//	}
//
//
//	private HashMap<String, String> putData(String user, String id) {
//		HashMap<String, String> item = new HashMap<String, String>();
//		item.put("user", user);
//		
//		item.put("id", id);
//		return item;
//	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("Lutfi", "resumeee");
		
		adapter.notifyDataSetChanged();
		setupData();
	}
	
	private void setupData() {
		image = new ArrayList<ListPhoto>();
		cursor = loginDataBaseAdapter.test();
		if (cursor.getCount() > 0) {

			for(int i=0;i<cursor.getCount();i++){
				cursor.moveToNext();
				list = new ListPhoto();
				//Bitmap bm = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
				list.setImage(cursor.getString(2));
				list.setUser(cursor.getString(1));
				image.add(0, list);
				//image.add(bm);
			}
			
		}

		adapter = new AdapterCustomTimeline(this, R.layout.list_item_timeline, R.id.imageListView, image);
		setListAdapter(adapter);
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}