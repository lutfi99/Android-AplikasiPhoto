package com.example.studio;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	EditText editTextUsernameRegister, editTextPasswordRegister, editTextNameRegister, editTextEmailRegister, editTextPhoneRegister;
	Button buttonRegisterMasuk;
	ImageView imagePhotoProfile;
	TextView textViewChangePhoto;
	
	LoginDataBaseAdapter loginDataBaseAdapter;

	private static final int REQUEST_CODE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private Bitmap bitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		editTextUsernameRegister = (EditText) findViewById (R.id.editTextUsernameRegister);
		editTextPasswordRegister = (EditText) findViewById (R.id.editTextPasswordRegister);
		editTextNameRegister = (EditText) findViewById (R.id.editTextNameRegister);
		editTextEmailRegister = (EditText) findViewById (R.id.editTextEmailRegister);
		editTextPhoneRegister = (EditText) findViewById (R.id.editTextPhoneRegister);
		
		imagePhotoProfile = (ImageView) findViewById (R.id.imagePhotoProfile);
		
		textViewChangePhoto = (TextView) findViewById (R.id.textViewChangePhoto);
		
		buttonRegisterMasuk = (Button) findViewById (R.id.buttonRegisterMasuk);
		buttonRegisterMasuk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String userName = editTextUsernameRegister.getText().toString();
				String password = editTextPasswordRegister.getText().toString();
				String name = editTextNameRegister.getText().toString();
				String email = editTextEmailRegister.getText().toString();
				String phone = editTextPhoneRegister.getText().toString();

				//Bundle extras = getIntent().getExtras();
				//	byte[] image = extras.getByteArray("image"); //bitmaptoByteArray(bitmap);
				//	imagePhotoProfile.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length));
				//if(bitmap != null) {
					ByteArrayOutputStream outStr = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStr);
					//bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStr);
					byte[] image = outStr.toByteArray();	        	
				//}	
				
				//check jika field kosong
				if(userName.equals("")||password.equals("")||name.equals("")||email.equals("")||phone.equals("")) {
					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
					return;
				} else {
					//save ke database
					loginDataBaseAdapter.insertEntry(userName, password, name, email, phone, image);
					Intent intentBack =  new Intent(RegisterActivity.this, MainActivity.class);
					startActivity(intentBack);
				}

				finish();
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		InputStream stream = null;
		if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
			try {
				if(bitmap != null) {
					bitmap.recycle();
				}
				stream = getContentResolver().openInputStream(data.getData());
				bitmap = BitmapFactory.decodeStream(stream);
				
				imagePhotoProfile.setImageBitmap(bitmap);

				//bitmaptoByteArray(bitmap);
				
				//return image;
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
		if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			bitmap = (Bitmap) data.getExtras().get("data"); 
			imagePhotoProfile.setImageBitmap(bitmap);
			
			//bitmaptoByteArray(bitmap);
		}
	}

	public void ChangePhoto(View v) {
		final Dialog dialog = new Dialog(RegisterActivity.this);
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
						/*URL img_value = null;
						
						img_value = new URL("http://graph.facebook.com/"+fbID+"/picture?type=large");
						Bitmap bm = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
						imagePhotoProfile.setImageBitmap(bm);*/
						
						
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
						        	imagePhotoProfile.setImageBitmap(bitmap);
									
						        	//bitmaptoByteArray(bm);
						        	dialog.cancel();
						}
				});
				
			}
		});
		
		dialog.show();
	}
	
	/*public byte[] bitmaptoByteArray (Bitmap bitmap) {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] byteArray = out.toByteArray();
        return byteArray;
	}*/
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loginDataBaseAdapter.close();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//finish();
	}

}