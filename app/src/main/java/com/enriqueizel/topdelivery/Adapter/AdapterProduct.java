package com.enriqueizel.topdelivery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.enriqueizel.topdelivery.Model.Product;
import com.enriqueizel.topdelivery.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder> {

  private Context context;
  private List<Product> productList;

  public AdapterProduct(Context context, List<Product> productList) {
    this.context = context;
    this.productList = productList;
  }

  @NonNull
  @Override
  public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View listItem;
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    listItem = layoutInflater.inflate(R.layout.item_product, parent, false);
    return new ProductViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    Glide.with(context).load(productList.get(position).getPhoto()).into(holder.photo);
    holder.name.setText(productList.get(position).getName());
    holder.price.setText(productList.get(position).getPrice());
    holder.description.setText(productList.get(position).getDescription());
  }

  @Override
  public int getItemCount() {
    return productList.size();
  }

  public class ProductViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView photo;
    private TextView name;
    private TextView price;
    private TextView description;

    public ProductViewHolder(@NonNull View itemView) {
      super(itemView);

      photo = itemView.findViewById(R.id.img_product_photo);
      name = itemView.findViewById(R.id.txt_product_name);
      price = itemView.findViewById(R.id.txt_product_price);
      description = itemView.findViewById(R.id.details_txt_description);
    }
  }
}
