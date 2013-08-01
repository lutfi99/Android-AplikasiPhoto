package com.example.studio;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class EditProfile extends Activity {
	
	private static final int DIALOG_LOADING = 0;
	EditText editTextNameEdit, editTextEmailEdit, editTextPhoneEdit,editTextChangePassword,editTextNewPassword,editTextNewPasswordAgain;
	Button buttonChangePassword, buttonSaveChange;
	LoginDataBaseAdapter loginDataBaseAdapter;
	String getUserName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
		
		ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff6600")));
        bar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        
		editTextNameEdit = (EditText) findViewById (R.id.editTextNameEdit);
		editTextEmailEdit = (EditText) findViewById (R.id.editTextEmailEdit);
		editTextPhoneEdit = (EditText) findViewById (R.id.editTextPhoneEdit);
		buttonChangePassword = (Button) findViewById (R.id.buttonChangePassword);
		buttonSaveChange = (Button) findViewById (R.id.buttonSaveChange);
		
		editTextNameEdit.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.name), null, null, null);
		editTextEmailEdit.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.email), null, null, null);
		editTextPhoneEdit.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.phone), null, null, null);
		
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();
		
		Bundle bundle = this.getIntent().getExtras();
		getUserName = bundle.getString("value_username_profile");

		String getNama = loginDataBaseAdapter.getName(getUserName);
		editTextNameEdit.setText(getNama);
		
		String getEmail = loginDataBaseAdapter.getEmailEdit(getUserName);
		editTextEmailEdit.setText(getEmail);
		
		String getPhone = loginDataBaseAdapter.getPhone(getUserName);
		editTextPhoneEdit.setText(getPhone);
		
		buttonSaveChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = editTextNameEdit.getText().toString();
				String email = editTextEmailEdit.getText().toString();
				String phone = editTextPhoneEdit.getText().toString();
				
				//check jika field kosong
//				if(name.equals("")||email.equals("")||phone.equals("")) {
//					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
//					return;
//				} else {
//					//save ke database
//					loginDataBaseAdapter.updateEntry(getUserName, name, email, phone);
//					Toast.makeText(EditProfile.this, "Updated!", Toast.LENGTH_LONG).show();
//					showDialog(DIALOG_LOADING);
//					finish();
//				}
				
				
				if(validated()){
					loginDataBaseAdapter.updateEntry(getUserName, name, email, phone);
					Toast.makeText(EditProfile.this, "Updated!", Toast.LENGTH_LONG).show();
					showDialog(DIALOG_LOADING);
					finish();
				} 
				else {
					return;
				}
			}
		});
		
		buttonChangePassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(EditProfile.this);
				dialog.setContentView(R.layout.change_password);
				dialog.setTitle("Change Password");
				
				editTextChangePassword = (EditText)dialog.findViewById(R.id.editTextChangePassword);
				editTextNewPassword = (EditText)dialog.findViewById(R.id.editTextNewPassword);
				editTextNewPasswordAgain = (EditText)dialog.findViewById(R.id.editTextNewPasswordAgain);
				
				String pass = loginDataBaseAdapter.getPass(getUserName);
				editTextChangePassword.setText(pass);

				
				Button buttonSaveChangePassword = (Button)dialog.findViewById(R.id.buttonSaveChangePassword);
				buttonSaveChangePassword.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						String newPass = editTextNewPassword.getText().toString();
						String newPassAgain = editTextNewPasswordAgain.getText().toString();
						
						if(!newPass.equals(newPassAgain))
						{
							Toast.makeText(getApplicationContext(), "New Password doesn't match with confirm password", Toast.LENGTH_LONG).show();
							return;
						}
						else
						{
							loginDataBaseAdapter.updatePass(getUserName, newPass);
							Toast.makeText(EditProfile.this, "Password Changed!", Toast.LENGTH_LONG).show();
							finish();
						}
					}
				});
				dialog.show();
			}
		});
	}
	
	protected boolean validated() {
		  
		 boolean validated = true;
		 
		 if (!Validate.hasText(editTextNameEdit)){
			 editTextNameEdit.setError( "Name is required!" );
			 editTextNameEdit.requestFocus();
			 validated = false;
		 }else
		 if (!Validate.hasText(editTextEmailEdit)){
			 editTextEmailEdit.setError( "Email is required!" );
			 editTextEmailEdit.requestFocus();
			 validated = false;
		 }else
		 if (!Validate.hasText(editTextPhoneEdit)){
			 editTextPhoneEdit.setError( "Phone is required!" );
			 editTextPhoneEdit.requestFocus();
			 validated = false;
		 }else
		 if (!Validate.isEmailAddress(editTextEmailEdit, false)){
			 Toast.makeText(getApplicationContext(), "email is not valid", Toast.LENGTH_SHORT).show();
			 editTextEmailEdit.requestFocus();
			 validated = false;
		 }
		 return validated;
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
}
