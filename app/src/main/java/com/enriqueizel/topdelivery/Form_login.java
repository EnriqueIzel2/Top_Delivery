package com.enriqueizel.topdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Form_login extends AppCompatActivity {

  private TextView txtCreateAccount;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_form_login);

    getSupportActionBar().hide();
    getComponentsID();

    txtCreateAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Form_login.this, Form_Register.class);
        startActivity(intent);
      }
    });
  }

  public void getComponentsID() {
    txtCreateAccount = findViewById(R.id.txt_create_account);
  }
}