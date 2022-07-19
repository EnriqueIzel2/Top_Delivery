package com.enriqueizel.topdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_Profile extends AppCompatActivity {

  private CircleImageView userPhoto;
  private EditText editUsername;
  private Button btnUpdateData, btnEditSelectPhoto;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_profile);

    getComponentsID();
  }

  private void getComponentsID() {
    userPhoto = findViewById(R.id.img_edit_user_photo);
    editUsername = findViewById(R.id.edit_edit_username);
    btnUpdateData = findViewById(R.id.btn_update_data);
    btnEditSelectPhoto = findViewById(R.id.btn_edit_select_photo);
  }
}