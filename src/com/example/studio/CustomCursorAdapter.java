package com.example.studio;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CustomCursorAdapter extends CursorAdapter {
	
    private LayoutInflater inflater;

    /*@SuppressWarnings("deprecation")
	public CustomCursorAdapter(Context con, Cursor c) {
        super(con, c);
        //inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        context = con;
        cursor = c;
        inflater = LayoutInflater.from(con);
    }*/
    
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor c, ViewGroup parent) {
        // do the layout inflation here
        View v = inflater.inflate(R.layout.list_item_view, parent, false);
        return v;
    }

    @Override
    public void bindView(View v, Context con, Cursor c) {
        // do everything else here
        TextView txt = (TextView) v.findViewById(R.id.textListView);
        //ImageView img = (ImageView) v.findViewById(R.id.imageListView);
        
        txt.setText(c.getString(c.getColumnIndex("USERNAME")));
        
        /*List<String> text = new ArrayList<String>();
		
		if (c.getCount() > 0) {

			for(int i=0;i<c.getCount();i++){
				c.moveToNext();
				text.add(c.getString(1));
				//txt.setText(text.get(i));
			}
			
		}*/

        //String text = c.getString(c.getColumnIndex("USERNAME")); 
        //txt.setText(text);
        
        /*c.moveToFirst();
        byte[] byteImg = c.getBlob(c.getColumnIndex("IMAGE"));
        Bitmap bm = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.length);
        img.setImageBitmap(bm);*/
    }

}