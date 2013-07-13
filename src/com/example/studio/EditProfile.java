package com.example.studio;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfile extends Activity {
	
	EditText editTextNameEdit, editTextEmailEdit, editTextPhoneEdit,editTextChangePassword,editTextNewPassword,editTextNewPasswordAgain;
	Button buttonChangePassword, buttonSaveChange;
	LoginDataBaseAdapter loginDataBaseAdapter;
	String getUserName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
		
		editTextNameEdit = (EditText) findViewById (R.id.editTextNameEdit);
		editTextEmailEdit = (EditText) findViewById (R.id.editTextEmailEdit);
		editTextPhoneEdit = (EditText) findViewById (R.id.editTextPhoneEdit);
		buttonChangePassword = (Button) findViewById (R.id.buttonChangePassword);
		buttonSaveChange = (Button) findViewById (R.id.buttonSaveChange);
		
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
				if(name.equals("")||email.equals("")||phone.equals("")) {
					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
					return;
				} else {
					//save ke database
					loginDataBaseAdapter.updateEntry(getUserName, name, email, phone);
					Toast.makeText(EditProfile.this, "Updated!", Toast.LENGTH_LONG).show();
					finish();
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
}
