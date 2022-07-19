package com.enriqueizel.topdelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.enriqueizel.topdelivery.Adapter.AdapterProduct;
import com.enriqueizel.topdelivery.Model.Product;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Products_List extends AppCompatActivity {

  private RecyclerView recyclerViewProducts;
  private AdapterProduct adapterProduct;
  private List<Product> productList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_list);

    recyclerViewProducts = findViewById(R.id.recycler_products);
    productList = new ArrayList<>();
    adapterProduct = new AdapterProduct(getApplicationContext(), productList);
    recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    recyclerViewProducts.setHasFixedSize(true);
    recyclerViewProducts.setAdapter(adapterProduct);

    Product product = new Product(R.drawable.ic_launcher_background, "Produto 1", "30,99");
    productList.add(product);
    Product product2 = new Product(R.drawable.ic_launcher_background, "Produto 2", "30,99");
    productList.add(product2);
    Product product3 = new Product(R.drawable.ic_launcher_background, "Produto 3", "30,99");
    productList.add(product3);

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
      Intent intent = new Intent(Products_List.this, User_Profile.class);
      startActivity(intent);
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