package com.enriqueizel.topdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Products_List extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_list);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);

    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int itemID = item.getItemId();

    if (itemID == R.id.ic_user_profile) {

    } else if (itemID == R.id.ic_orders) {

    } else if (itemID == R.id.ic_logout) {
      FirebaseAuth.getInstance().signOut();
      Toast.makeText(Products_List.this, "Sessão encerrada", Toast.LENGTH_SHORT).show();
      Intent intent = new Intent(Products_List.this, Form_login.class);
      startActivity(intent);
      finish();
    }
    return super.onOptionsItemSelected(item);
  }
}