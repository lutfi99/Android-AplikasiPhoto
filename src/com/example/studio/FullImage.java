package com.example.studio;

import java.io.FileNotFoundException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class FullImage extends Activity {
	LoginDataBaseAdapter loginDataBaseAdapter;
	ListPhoto list;
	Cursor cursor;
	String getUserName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_full);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		
		getUserName = bundle.getString("value_username");
		
		cursor = loginDataBaseAdapter.grid(getUserName);
		
		if (cursor.getCount() > 0) {
			for(int i=0;i<cursor.getCount();i++){
				cursor.moveToNext();
				list = new ListPhoto();
				list.setId(cursor.getInt(0));
			}
		}
		
		ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
		imageBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		ImageView imageDel = (ImageView) findViewById(R.id.imageDel);
		imageDel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						FullImage.this);
					alertDialogBuilder.setTitle("Delete");
		 
					alertDialogBuilder
						.setMessage("Photo will be deleted")
						.setCancelable(false)
						.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								
								
								String idKirim = Integer.toString(list.getId());
								loginDataBaseAdapter.deleteImage(idKirim);
//								adapter.notifyDataSetChanged();
//								setupData();
								Toast toast = Toast.makeText(getApplicationContext(), "Photo is deleted", Toast.LENGTH_LONG);
								//toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								
								Bundle bundle = new Bundle();
								bundle.putString("value_username", getUserName);
								
								Intent intent = new Intent(getApplicationContext(), Tab.class);
								intent.putExtras(bundle);
								startActivity(intent);
								finish();
							}
						  })
						.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						});

						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
			}
		});
		
		ImageView imageFull = (ImageView) findViewById(R.id.imageFull);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTempStorage = new byte[3*1024];
		
		Uri path = getIntent().getData();
		
		//Bitmap ops = BitmapFactory.decodeFile(path, options);
		Bitmap bitmap;
		try {
			bitmap = BitmapFactory.decodeStream(getApplicationContext().getContentResolver().openInputStream(path));
			imageFull.setImageBitmap(bitmap);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		imageFull.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						FullImage.this);
					alertDialogBuilder.setTitle("Delete");
		 
					alertDialogBuilder
						.setMessage("Photo will be deleted")
						.setCancelable(false)
						.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								
								
								String idKirim = Integer.toString(list.getId());
								loginDataBaseAdapter.deleteImage(idKirim);
//								adapter.notifyDataSetChanged();
//								setupData();
								Toast toast = Toast.makeText(getApplicationContext(), "Photo is deleted", Toast.LENGTH_LONG);
								//toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								
								Bundle bundle = new Bundle();
								bundle.putString("value_username", getUserName);
								
								Intent intent = new Intent(getApplicationContext(), Tab.class);
								intent.putExtras(bundle);
								startActivity(intent);
								finish();
							}
						  })
						.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								dialog.cancel();
							}
						});

						AlertDialog alertDialog = alertDialogBuilder.create();
						alertDialog.show();
				
				return false;
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}
