package com.enriqueizel.topdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Form_Register extends AppCompatActivity {

  private CircleImageView userPhoto;
  private Button btnSelectPhoto, btnRegister;
  private EditText editName, editEmail, editPassword;
  private TextView txtErrorMessage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form_register);
  }

  public void getComponentsID(){
    userPhoto = findViewById(R.id.photo_user);
    btnSelectPhoto = findViewById(R.id.btn_select_photo);
    btnRegister = findViewById(R.id.btn_register);
    editName = findViewById(R.id.edit_username);
    editEmail = findViewById(R.id.edit_email);
    editPassword = findViewById(R.id.edit_password);
    txtErrorMessage = findViewById(R.id.txt_error_message);
  }

  TextWatcher registerTextWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
  };
}