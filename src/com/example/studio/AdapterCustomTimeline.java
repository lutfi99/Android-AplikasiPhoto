package com.example.studio;

import java.io.FileNotFoundException;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterCustomTimeline extends ArrayAdapter<ListPhoto> {
	List<ListPhoto> items;
	Bitmap bitmap = null;
	ListPhoto item;
	Uri u;
	View row;
	
	public AdapterCustomTimeline(Context context, int layout, int resId, List<ListPhoto> items) {
		// Call through to ArrayAdapter implementation
		super(context, layout, resId, items);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		row = convertView;
		// Inflate a new row if one isn’t recycled
		if (row == null) {
			row = LayoutInflater.from(getContext()).inflate(
					R.layout.list_item_timeline, parent, false);
		}
		
		item = items.get(position);
		
		TextView text = (TextView) row.findViewById(R.id.textListView);
		text.setText(item.getUser());
		
		u = Uri.parse(item.getImage());
		ImageView image = (ImageView) row.findViewById (R.id.imageListView);
		try {
			bitmap = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(u));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		image.setImageBitmap(bitmap);
		
		return row;
	}
}
