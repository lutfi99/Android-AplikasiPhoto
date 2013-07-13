package com.example.studio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Profile extends Activity {
	ImageView imageViewPhoto;
	TextView textViewUserName;
	Button buttonEditProfile;
	LoginDataBaseAdapter loginDataBaseAdapter;
	String getUserName, getNama;
	
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

	/*public String nama(){
		String nama;
		nama = getNama;
		return nama;
	}*/
}
