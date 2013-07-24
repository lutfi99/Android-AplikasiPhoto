package com.example.studio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	
	private List<Bitmap> i = new ArrayList<Bitmap>();
	private Cursor cursor;
	
	/*private LoginDataBaseAdapter loginDataBaseAdapter;
	
	loginDataBaseAdapter = new LoginDataBaseAdapter(this);
	loginDataBaseAdapter = loginDataBaseAdapter.open();

	Cursor cursor = loginDataBaseAdapter.test();
	
	cursor.moveToFirst();
	byte[] byteImg = cursor.getBlob(cursor.getColumnIndex("IMAGE"));
	
	List<Bitmap> image = new ArrayList<Bitmap>();
	
	if (cursor.getCount() > 0) {

		for(int i=0;i<cursor.getCount();i++){
			cursor.moveToNext();
			Bitmap bm = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
			//image.add(cursor.getBlob(2));
			image.add(bm);
		}
	}*/
	
	//masukkan images ke array
	 Integer[] image = {
			
	};
	
	public ImageAdapter (Context c) {
		mContext = c;
	}
	
	@Override
	public int getCount() {
		return image.length;
	}
	
	@Override
	public Object getItem(int position) {
		return image[position];
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(image[position]);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
		return imageView; 
	}

}
