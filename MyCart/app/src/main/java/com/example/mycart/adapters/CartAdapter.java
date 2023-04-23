package com.example.mycart.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycart.R;
import com.example.mycart.models.ItemCart;
import com.example.mycart.threads.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private CartClickedListeners cartClickedListeners;
    private List<ItemCart> cartList;

    public CartAdapter(CartClickedListeners cartClickedListeners) {
        this.cartClickedListeners = cartClickedListeners;
    }

    public void setCartList(List<ItemCart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

        ItemCart itemCart = cartList.get(position);

        holder.itemNameTv.setText(itemCart.getName());
        holder.itemPriceTv.setText("₫ " + itemCart.convertToVietNamDong(itemCart.getPrice() / itemCart.getQuantity()));
        holder.totalEachItemPriceTv.setText("Total: ₫ " + itemCart.convertToVietNamDong());
        holder.itemQuantity.setText(String.valueOf(itemCart.getQuantity()));
        Constants.executor.execute(() -> {
            Bitmap bitmap = downloadImage(itemCart.getThumbnail());
            if (bitmap != null) {
                handler.post(() -> holder.itemImageView.setImageBitmap(bitmap));
            }
        });

        holder.deleteItemBtn.setOnClickListener(view -> cartClickedListeners.onDeleteClicked(itemCart));
        holder.addQuantityBtn.setOnClickListener(view -> cartClickedListeners.onAddQuantityClicked(itemCart));
        holder.minusQuantityBtn.setOnClickListener(view -> cartClickedListeners.onMinusQuantityClicked(itemCart));
    }

    @Override
    public int getItemCount() {
        if (cartList == null) {
            return 0;
        }
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameTv, itemPriceTv, itemQuantity, totalEachItemPriceTv;
        private ImageView deleteItemBtn;
        private ImageView itemImageView;
        private ImageButton addQuantityBtn, minusQuantityBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            totalEachItemPriceTv = itemView.findViewById(R.id.eachCartTotalItemPriceTv);
            itemNameTv = itemView.findViewById(R.id.eachCartItemName);
            itemPriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            deleteItemBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            itemImageView = itemView.findViewById(R.id.eachCartItemIV);
            itemQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);
        }
    }

    private Bitmap downloadImage(String link) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface CartClickedListeners {
        void onDeleteClicked(ItemCart itemCart);

        void onAddQuantityClicked(ItemCart itemCart);

        void onMinusQuantityClicked(ItemCart itemCart);
    }
}
