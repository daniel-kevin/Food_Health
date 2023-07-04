package com.example.foodhealth;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private HashMap<Product, Integer> selectedProducts = new HashMap<>();
    private List<CartItem> cartItems;
    public interface OnQuantityChangedListener {
        void onQuantityIncreased(int position);
        void onQuantityDecreased(int position);
    }
    private OnQuantityChangedListener quantityChangedListener;

    public void setOnQuantityChangedListener(OnQuantityChangedListener listener) {
        this.quantityChangedListener = listener;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewProduct;
        public TextView textViewName;
        public TextView textViewPrice;
        public Button buttonMinus;
        public TextView textViewCounter;
        public Button buttonPlus;

        public ProductViewHolder(View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);
            textViewCounter = itemView.findViewById(R.id.textViewCounter);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
        }
    }


    public ProductAdapter(List<Product> productList, List<CartItem> cartItems) {
        this.productList = productList;
        this.cartItems = cartItems;
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
        holder.imageViewProduct.setImageResource(product.getImageResourceId());
        // Set the initial counter value
        holder.textViewCounter.setText(String.valueOf(getCartItemQuantity(product.getId())));

        holder.buttonPlus.setOnClickListener(v -> {
            if (quantityChangedListener != null) {
                quantityChangedListener.onQuantityIncreased(position);
            }
            holder.textViewCounter.setText(String.valueOf(getCartItemQuantity(product.getId())));
        });

        holder.buttonMinus.setOnClickListener(v -> {
            if (quantityChangedListener != null) {
                quantityChangedListener.onQuantityDecreased(position);
            }
            holder.textViewCounter.setText(String.valueOf(getCartItemQuantity(product.getId())));
        });

//        Picasso.get().load(product.getImagePath()).placeholder(R.drawable.placeholder).into(holder.imageViewProduct);
    }
    private int getCartItemQuantity(int productId) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductId() == productId) {
                return cartItem.getQuantity();
            }
        }
        return 0;
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

