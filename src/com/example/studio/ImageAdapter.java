package com.example.studio;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	
	//masukkan images ke array
	public Integer[] image = {
	//		pic1, pic2, pic3;
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
