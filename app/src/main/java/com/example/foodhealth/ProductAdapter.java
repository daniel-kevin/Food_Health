package com.example.foodhealth;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProduct;
        public TextView textViewName;
        public TextView textViewPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }
    }

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.textViewName.setText(product.getName());
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));

        Picasso.get().load(product.getImagePath()).placeholder(R.drawable.placeholder).into(holder.imageViewProduct);
    }
    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}

