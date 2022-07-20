package com.enriqueizel.topdelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Product_Details extends AppCompatActivity {

  private ImageView imgPhoto;
  private TextView txtName, txtDescription, txtPrice;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_details);

    getComponentsID();

    String photo = getIntent().getExtras().getString("photo");
    String name = getIntent().getExtras().getString("name");
    String description = getIntent().getExtras().getString("description");
    String price = getIntent().getExtras().getString("price");

    Glide.with(getApplicationContext()).load(photo).into(imgPhoto);
    txtName.setText(name);
    txtDescription.setText(description);
    txtPrice.setText(price);
  }

  private void getComponentsID() {
    imgPhoto = findViewById(R.id.details_img_photo);
    txtName = findViewById(R.id.details_txt_name);
    txtDescription = findViewById(R.id.details_txt_description);
    txtPrice = findViewById(R.id.details_txt_price);
  }
}