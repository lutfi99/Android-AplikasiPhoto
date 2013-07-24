package com.example.studio;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Tab extends TabActivity{
	SessionManager session;
	final Context context = this;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);

        Bundle bundle = new Bundle();
        String getUserName;
        
        session = new SessionManager(getApplicationContext());
        
        TabHost tabHost = getTabHost();
         
        // Tab for Profile
        TabSpec profile = tabHost.newTabSpec("Profile");
        // setting Title and Icon for the Tab
        profile.setIndicator("Profile");
        
		bundle = this.getIntent().getExtras();
		
		getUserName = bundle.getString("value_username");
		bundle.putString("value_username", getUserName);
		
		
        Intent profileIntent = new Intent(this, Profile.class);
        profileIntent.putExtras(bundle); 
        profile.setContent(profileIntent);
        
        TabSpec photo = tabHost.newTabSpec("Photo");        
        photo.setIndicator("Photo", getResources().getDrawable(R.drawable.photo));
        bundle.putString("value_username", getUserName);
		
        Intent photoIntent = new Intent(this, Photo.class);
        photoIntent.putExtras(bundle); 
        photo.setContent(photoIntent);
        
        TabSpec timeline = tabHost.newTabSpec("Home");        
        timeline.setIndicator("Home");
        Intent timelineIntent = new Intent(this, Timeline.class);
        timeline.setContent(timelineIntent);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(timeline);
        tabHost.addTab(photo);
        tabHost.addTab(profile);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemLogout:
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
				alertDialogBuilder.setTitle("LOGOUT");
	 
				alertDialogBuilder
					.setMessage("Are you sure? Click Yes to Logout")
					.setCancelable(false)
					.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							session.logoutUser();
							//finish();
						}
					  })
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
	 
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
			
			return true;
		default:
			return true;
		}
	}
}
