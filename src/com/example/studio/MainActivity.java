package com.example.studio;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	private static final int DIALOG_LOADING = 0;
	Button buttonRegister, buttonSignIn;
	EditText editTextUserSignIn, editTextPasswordSignIn;
	LoginDataBaseAdapter loginDataBaseAdapter;
	SessionManager session;
	String name, userName, password;
	Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		// Session Manager
        session = new SessionManager(getApplicationContext());

		session.checkLogin();
       
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		buttonRegister = (Button) findViewById(R.id.buttonRegister);
		buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
		
		buttonRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentRegister = new Intent(getApplicationContext(),RegisterActivity.class);
				startActivity(intentRegister);
			}
		});
	}
	
	public void signIn(View V) {
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.sign_in);
		dialog.setTitle("Sign In");
		
		editTextUserSignIn = (EditText)dialog.findViewById(R.id.editTextUserSignIn);
		editTextPasswordSignIn = (EditText)dialog.findViewById(R.id.editTextPasswordSignIn);
		
		Button buttonSignInMasuk = (Button)dialog.findViewById(R.id.buttonSignInMasuk);
		
		TextView textViewForgotPass = (TextView)dialog.findViewById(R.id.textViewForgotPass);
		
		textViewForgotPass.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.setContentView(R.layout.forgot_pass);
				dialog.setTitle("Forgot Password");

				Button buttonShowPass = (Button)dialog.findViewById(R.id.buttonShowPass);

				buttonShowPass.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						EditText editTextEmailForgot = (EditText)dialog.findViewById(R.id.editTextEmailForgot);
						TextView textViewForgotPassword = (TextView)dialog.findViewById(R.id.textViewForgotPassword);
						
						
						String email = editTextEmailForgot.getText().toString();
						String forgotPass = loginDataBaseAdapter.getEmail(email);
						textViewForgotPassword.setText("Password: "+forgotPass);
					}
				});
				
			}
		});
		
		buttonSignInMasuk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ambil username dan pass
				userName = editTextUserSignIn.getText().toString();
				password = editTextPasswordSignIn.getText().toString();
				
				//cocokkan pass dari database
				String storedPassword = loginDataBaseAdapter.getSingleEntry(userName);
				
				session.createLoginSession(userName, password);
				
				// get user data from session
		        HashMap<String, String> user = session.getUserDetails();
		        
		        // name
		        name = user.get(SessionManager.KEY_NAME);
		        
				
				if(password.equals(storedPassword)){
					toast = Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_SHORT);
					//toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					
					Bundle bundle = new Bundle();
					bundle.putString("value_username", name);
					
					Intent intentProfile= new Intent(getApplicationContext(),Tab.class);
					intentProfile.putExtras(bundle); 
					startActivity(intentProfile);
					dialog.dismiss();
					finish();
					showDialog(DIALOG_LOADING);
				}
				else {
					toast = Toast.makeText(MainActivity.this, "Username or Password does not match", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});
		dialog.show();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//tutup database
		loginDataBaseAdapter.close();
		finish();
	}
	
	@Override   
	protected Dialog onCreateDialog(int id) {
	    switch (id) {
	    case DIALOG_LOADING:
	        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent);          
	        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        dialog.setContentView(R.layout.loading);
	        dialog.setCancelable(true);
	        dialog.setOnCancelListener(new OnCancelListener() {             
	            @Override
	            public void onCancel(DialogInterface dialog) {
	                //onBackPressed();
	            }
	        });
	    return dialog;  

	    default:
	        return null;
	    }
	};
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//	    
////		menu.getItem(1).setVisible(false);
//		return true;
//	}
}
