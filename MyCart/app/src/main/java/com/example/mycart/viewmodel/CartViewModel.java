package com.example.mycart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mycart.models.ItemCart;
import com.example.mycart.repositories.ItemRepository;
import com.example.mycart.threads.Constants;

import java.util.List;

public class CartViewModel extends AndroidViewModel {
    private final ItemRepository itemRepository;
    public CartViewModel(@NonNull Application application) {
        super(application);
        itemRepository = new ItemRepository(application);
    }

    public void insertCartItem(ItemCart itemCart) {
        Constants.executor.execute(() -> itemRepository.addItem(itemCart));
    }

    public void deleteCartItem(int id) {
        Constants.executor.execute(() -> itemRepository.deleteItem(id));
    }

    public void updateQuantity(int id, int quantity) {
        Constants.executor.execute(() -> itemRepository.updateQuantity(id, quantity));
    }

    public void updatePrice(int id, int price) {
        Constants.executor.execute(() -> itemRepository.updatePrice(id, price));
    }

    public LiveData<List<ItemCart>> getAllCartItems() {
        return itemRepository.getItemCartLiveData();
    }
}
