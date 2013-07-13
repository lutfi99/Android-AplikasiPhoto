package com.example.studio;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity {
	ImageView imageViewPhoto;
	TextView textViewUserName;
	Button buttonEditProfile;
	LoginDataBaseAdapter loginDataBaseAdapter;
	String getUserName, getNama;
	
	final int PIC_CROP = 2;
	private Uri picUri;
	
	private static final int REQUEST_CODE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);
		textViewUserName = (TextView) findViewById(R.id.textViewUserName);
		buttonEditProfile = (Button) findViewById(R.id.buttonEditProfile);

		Bundle bundle = new Bundle();
		bundle = this.getIntent().getExtras();
		
		getUserName = bundle.getString("value_username");
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
	}
	
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
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				
				intent.putExtra("crop", "true");
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				intent.putExtra("outputX", 200);
				intent.putExtra("outputY", 150);
				//intent.putExtra("return-data", true);
				
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
		InputStream stream = null;
		if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			
			try {
				if(bitmap != null) {
					bitmap.recycle();
				}
				stream = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				
				imageViewPhoto.setImageBitmap(bitmap);
				savePhoto();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		}
		else if (requestCode == PIC_CROP && resultCode == Activity.RESULT_OK){
			bitmap = (Bitmap) data.getExtras().get("data");
			imageViewPhoto.setImageBitmap(bitmap);
			savePhoto();
		}
		
		if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

			crop();
			/*bitmap = (Bitmap) data.getExtras().get("data");
			imageViewPhoto.setImageBitmap(bitmap);
			savePhoto();*/
		}
	}
	
	public void savePhoto(){
		
		ByteArrayOutputStream outStr = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStr);
		byte[] image = outStr.toByteArray();
	
		//save ke database
		loginDataBaseAdapter.updatePhoto(getUserName, image);
	}
	
	public void crop() {
		try {
			
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			cropIntent.setType("image/*");
			cropIntent.setData(picUri);
			    //set crop properties
			cropIntent.putExtra("crop", "true");
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			cropIntent.putExtra("return-data", true);
			startActivityForResult(cropIntent, PIC_CROP);
			
		} catch(ActivityNotFoundException e) {
			Toast.makeText(getApplicationContext(), "Device doesn't support crop action!", Toast.LENGTH_LONG).show();
		}
	}
	
	/*public String nama(){
		String nama;
		nama = getNama;
		return nama;
	}*/
}
