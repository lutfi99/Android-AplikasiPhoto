package com.example.studio;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

public class GridActivity extends Activity {
	LoginDataBaseAdapter loginDataBaseAdapter;
	Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);
		
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();

        String getUserName;
        
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		getUserName = bundle.getString("value_username");

		cursor = loginDataBaseAdapter.grid(getUserName);
		
		ListPhoto list;
		List<ListPhoto> image = new ArrayList<ListPhoto>();
		
		if (cursor.getCount() > 0) {

			for(int i=0;i<cursor.getCount();i++){
				cursor.moveToNext();
				list = new ListPhoto();
				list.setImage(cursor.getString(2));
				image.add(list);
			}
			
		}
		
		AdapterCustom adapter = new AdapterCustom(this, R.layout.list_item_view, R.id.imageListView, image);
		
		
		
		GridView gridView = (GridView) findViewById(R.id.grid_view);
		gridView.setAdapter(adapter);
		
		
	}
}
