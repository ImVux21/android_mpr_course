package com.example.mycart.views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycart.R;
import com.example.mycart.adapters.CartAdapter;
import com.example.mycart.models.ItemCart;
import com.example.mycart.viewmodel.CartViewModel;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartClickedListeners {
    private CartViewModel cartViewModel;
    private RecyclerView cartRecyclerView;
    private TextView totalCartPriceTv;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeVariables();

        cartViewModel.getAllCartItems().observe(this, itemCarts -> {
            int price = 0;
            cartAdapter.setCartList(itemCarts);
            for (ItemCart itemCart : itemCarts) {
                price += itemCart.getPrice();
            }
            totalCartPriceTv.setText("₫ " + convertToVietNamDong(price));
        });
    }

    private void initializeVariables() {
        cartAdapter = new CartAdapter(this);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalCartPriceTv = findViewById(R.id.cartActivityTotalPriceTv);

        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void onDeleteClicked(ItemCart itemCart) {
        cartViewModel.deleteCartItem(itemCart.getId());
    }

    @Override
    public void onAddQuantityClicked(ItemCart itemCart) {
        int quantity = itemCart.getQuantity() + 1;
        cartViewModel.updateQuantity(itemCart.getId(), quantity);
        cartViewModel.updatePrice(itemCart.getId(), (itemCart.getPrice() / itemCart.getQuantity()) * quantity);
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMinusQuantityClicked(ItemCart itemCart) {
        int quantity = itemCart.getQuantity() - 1;
        if (quantity != 0) {
            cartViewModel.updateQuantity(itemCart.getId(), quantity);
            cartViewModel.updatePrice(itemCart.getId(), (itemCart.getPrice() / itemCart.getQuantity()) * quantity);
            cartAdapter.notifyDataSetChanged();
        }
        else {
            cartViewModel.deleteCartItem(itemCart.getId());
            cartAdapter.notifyDataSetChanged();
        }
    }

    private String convertToVietNamDong(int totalPrice) {
        String price = String.valueOf(totalPrice);
        StringBuilder result = new StringBuilder();
        int count = 0;
        for (int i = price.length() - 1; i >= 0; i--) {
            result.append(price.charAt(i));
            count++;
            if (count == 3 && i != 0) {
                result.append(".");
                count = 0;
            }
        }
        return result.reverse().toString();
    }
}