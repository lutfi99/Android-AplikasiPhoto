package com.example.studio;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {
	ImageView imageViewPhoto;
	TextView textViewUserName;
	Button buttonEditProfile, buttonPhoto, buttonTimeline, buttonGrid;
	LoginDataBaseAdapter loginDataBaseAdapter;
	String getUserName, getNama;
	ImageView mImage;
	File photo;
	Uri u;
	
	SessionManager session;
	
	private static final int REQUEST_CODE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 99;
	private Bitmap bitmap;
	
	Uri mImageUri;
	String mOutputFilePath;
	View mImageContainer;
	int imageWidth, imageHeight;
	Cursor cursor;
	AdapterCustom adapter;
	ListPhoto list;
	List<ListPhoto> image;
	GridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		session = new SessionManager(getApplicationContext());

		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		
		getUserName = bundle.getString("value_username");

		gridView = (GridView) findViewById(R.id.grid_view);
        
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		setupData();
		adapter.notifyDataSetChanged();
		
		gridView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(Profile.this);
				dialog.setContentView(R.layout.image_full);
				dialog.setTitle("Detail Image");
				dialog.show();
				ImageView imageFull = (ImageView)dialog.findViewById(R.id.imageFull);
				
				list = image.get(arg2);
				Uri ur = Uri.parse(list.getImage());
				
				imageFull.setImageURI(ur);
				
				Button buttonDeleteImage = (Button)dialog.findViewById(R.id.buttonDeleteImage);
				buttonDeleteImage.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
								Profile.this);
							alertDialogBuilder.setTitle("Delete");
				 
							alertDialogBuilder
								.setMessage("Photo will be deleted")
								.setCancelable(false)
								.setPositiveButton("Delete",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										String idKirim = Integer.toString(list.getId());
										loginDataBaseAdapter.deleteImage(idKirim);
										adapter.notifyDataSetChanged();
										setupData();
										Toast toast = Toast.makeText(getApplicationContext(), "Photo is deleted", Toast.LENGTH_LONG);
										//toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();
									}
								  })
								.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										dialog.cancel();
									}
								});

								AlertDialog alertDialog = alertDialogBuilder.create();
								alertDialog.show();
								dialog.cancel();
					}
				});
			}
		});
		
		
		imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
		textViewUserName = (TextView) findViewById(R.id.textViewUserName);
		buttonEditProfile = (Button) findViewById(R.id.buttonEditProfile);

		getNama = loginDataBaseAdapter.getName(getUserName);
		
		textViewUserName.setText(getNama);
		
		byte[] photo = loginDataBaseAdapter.getPhoto(getUserName);
		Bitmap bm = BitmapFactory.decodeByteArray(photo, 0, photo.length);
		imageViewPhoto.setImageBitmap(bm);
		
		imageViewPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ChangePhoto(v);
			}
		});
		
		buttonEditProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle bundleProfile = new Bundle();
				bundleProfile.putString("value_username_profile", getUserName);
				
				Intent intentEditProfile = new Intent(getApplicationContext(),EditProfile.class);
				intentEditProfile.putExtras(bundleProfile);
				startActivity(intentEditProfile);
				
				//startActivityForResult(intentEditProfile, CODE);
				
				//finish();
			}
		});
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		getNama = loginDataBaseAdapter.getName(getUserName);
		textViewUserName.setText(getNama);
		
		adapter.notifyDataSetChanged();
		setupData();
	}

//	@Override
//	public boolean onCreateOptionsMenu( Menu menu ) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate( R.menu.main_menu, menu );
//		return true;
//	}

	public void ChangePhoto(View v) {
		final Dialog dialog = new Dialog(Profile.this);
		dialog.setContentView(R.layout.select_photo);
		dialog.setTitle("Change Photo");
		
		Button buttonTakePhoto = (Button)dialog.findViewById(R.id.buttonTakePhoto);
		buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				
				/*intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 256);
				intent.putExtra("outputY", 256);
				intent.putExtra("return-data", true);*/
				
			    
				startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
				dialog.cancel();
			}
		});
		
		Button buttonChooseLibrary = (Button)dialog.findViewById(R.id.buttonChooseLibrary);
		buttonChooseLibrary.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);

				intent.setType("image/*");
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 256);
				intent.putExtra("outputY", 256);
				intent.putExtra("return-data", true);
				
				startActivityForResult(intent, REQUEST_CODE);
				dialog.cancel();
			}
		});
		
		Button buttonTakePhotoFacebook = (Button)dialog.findViewById(R.id.buttonTakePhotoFacebook);
		buttonTakePhotoFacebook.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.setContentView(R.layout.photo_facebook);
				dialog.setTitle("Get Your Facebook Photo");
		
				Button buttonGetPhotoFacebook = (Button)dialog.findViewById(R.id.buttonGetPhotoFacebook);
				buttonGetPhotoFacebook.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View V) {
						// TODO Auto-generated method stub
					
						            URL fbAvatarUrl = null;
						            //Bitmap bitmap = null;
						            EditText editTextUserFacebook = (EditText)dialog.findViewById(R.id.editTextUserFacebook);
									String fbID = editTextUserFacebook.getText().toString();
									
						            try {
						                fbAvatarUrl = new URL("http://graph.facebook.com/"+fbID+"/picture");
						                bitmap = BitmapFactory.decodeStream(fbAvatarUrl.openConnection().getInputStream());
						            } catch (MalformedURLException e) {
						                e.printStackTrace();
						            } catch (IOException e) {
						                e.printStackTrace();
						            }
						            
						            
						            imageViewPhoto.setImageBitmap(bitmap);
						            savePhoto();
						        	dialog.cancel();
						}
				});
				
			}
		});
		
		dialog.show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//InputStream stream = null;
		if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			bitmap = (Bitmap) data.getExtras().get("data");
			imageViewPhoto.setImageBitmap(bitmap);
			savePhoto();
			/*try {
				if(bitmap != null) {
					bitmap.recycle();
				}
				stream = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				
				imageViewPhoto.setImageBitmap(bitmap);
				savePhoto();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/

		}
		else
		if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			
			bitmap = (Bitmap) data.getExtras().get("data");
			imageViewPhoto.setImageBitmap(bitmap);
			savePhoto();
			/*u = data.getData();
			u = Uri.parse(u.toString());
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(u));
                imageViewPhoto.setImageBitmap(bitmap);
                savePhoto();
                
                Toast.makeText(getApplicationContext(), "Photo is saved", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
		}
	}
	
	public void savePhoto(){
		
		ByteArrayOutputStream outStr = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStr);
		byte[] image = outStr.toByteArray();
	
		//save ke database
		loginDataBaseAdapter.updatePhoto(getUserName, image);
	}
	
	private void setupData() {
		image = new ArrayList<ListPhoto>();
		cursor = loginDataBaseAdapter.grid(getUserName);
		
		if (cursor.getCount() > 0) {

			for(int i=0;i<cursor.getCount();i++){
				cursor.moveToNext();
				list = new ListPhoto();
				list.setImage(cursor.getString(2));
//				list.setId(cursor.getInt(0));
				image.add(0, list);
			}
			
		}
		
		adapter = new AdapterCustom(this, R.layout.list_item_view, R.id.imageListView, image);
		gridView.setAdapter(adapter);
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}

