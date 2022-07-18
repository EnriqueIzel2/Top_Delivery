package com.enriqueizel.topdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Profile extends AppCompatActivity {

  private CircleImageView userPhoto;
  private TextView userName, userEmail;
  private Button editProfile;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_profile);

    getComponentsID();
  }

  public void getComponentsID() {
    userPhoto = findViewById(R.id.img_user_photo);
    userName = findViewById(R.id.txt_user_name);
    userEmail = findViewById(R.id.txt_user_email);
    editProfile = findViewById(R.id.btn_edit_profile);
  }
}