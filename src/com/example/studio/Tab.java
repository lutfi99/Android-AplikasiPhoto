package com.example.studio;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class Tab extends TabActivity{
	SessionManager session;
	final Context context = this;
	TabHost tabHost;
	@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);
        
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff6600")));
//        bar.setBackgroundDrawable(new ColorDrawable(R.drawable.gradient));
        
        bar.setIcon(R.drawable.logo);

        Bundle bundle = new Bundle();
        String getUserName;
        
        session = new SessionManager(getApplicationContext());
        
        Resources res = getResources();
        tabHost = getTabHost();
        
        // Tab for Profile
        TabSpec profile = tabHost.newTabSpec("Profile");
        // setting Title and Icon for the Tab
        profile.setIndicator("", res.getDrawable(R.drawable.tab_profile));
        
		bundle = this.getIntent().getExtras();
		
		getUserName = bundle.getString("value_username");
		bundle.putString("value_username", getUserName);
		
		
        Intent profileIntent = new Intent(this, Profile.class);
        profileIntent.putExtras(bundle); 
        profile.setContent(profileIntent);
        
        TabSpec photo = tabHost.newTabSpec("Photo");        
        photo.setIndicator("", res.getDrawable(R.drawable.tab_photo));
        bundle.putString("value_username", getUserName);
		
        Intent photoIntent = new Intent(this, Photo.class);
        photoIntent.putExtras(bundle); 
        photo.setContent(photoIntent);
        
        TabSpec timeline = tabHost.newTabSpec("Home");        
        timeline.setIndicator("", res.getDrawable(R.drawable.tab_home));
        Intent timelineIntent = new Intent(this, Timeline.class);
        timeline.setContent(timelineIntent);
         
        // Adding all TabSpec to TabHost
        tabHost.addTab(timeline);
        tabHost.addTab(photo);
        tabHost.addTab(profile);
//        tabHost.setCurrentTab(2);
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
		case R.id.itemRefresh:
			
			
			return true;
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
							finish();
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
	
	public TabHost getMyTabHost() { 
		return tabHost; }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}
}
