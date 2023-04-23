package com.example.mycart.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mycart.databases.ItemDatabase;
import com.example.mycart.models.ItemCart;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    private final ItemDatabase itemDatabase;
    private final MutableLiveData<List<ItemCart>> itemCartLiveData = new MutableLiveData<>();

    public ItemRepository(Context context) {
        this.itemDatabase = new ItemDatabase(context);
    }

    public LiveData<List<ItemCart>> getItemCartLiveData() {
        readAllData();
        return itemCartLiveData;
    }

    public void addItem(ItemCart itemCart) {
        SQLiteDatabase db = itemDatabase.getWritableDatabase();
        String query = "INSERT INTO " + ItemDatabase.TABLE_NAME +
                " (" + ItemDatabase.COLUMN_ITEM_NAME + ", " +
                ItemDatabase.COLUMN_ITEM_CATEGORY + ", " +
                ItemDatabase.COLUMN_ITEM_THUMBNAIL + ", " +
                ItemDatabase.COLUMN_ITEM_PRICE + ", " +
                ItemDatabase.COLUMN_ITEM_QUANTITY + ") " +
                "VALUES ('" + itemCart.getName() + "', '" + itemCart.getCategory() + "', '" + itemCart.getThumbnail() + "', '" + itemCart.getPrice() + "', '" + itemCart.getQuantity() + "')";
        db.execSQL(query);
        readAllData();
    }

    // get all items
    public void readAllData() {
        List<ItemCart> itemCartList = new ArrayList<>();

        String query = "SELECT * FROM " + ItemDatabase.TABLE_NAME;
        SQLiteDatabase db = itemDatabase.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String itemName = cursor.getString(1);
                    String category = cursor.getString(2);
                    String thumbnail = cursor.getString(3);
                    int itemPrice = cursor.getInt(4);
                    int itemQuantity = cursor.getInt(5);

                    ItemCart itemCart = new ItemCart(id, itemName, category, thumbnail, itemPrice, itemQuantity);
                    itemCartList.add(itemCart);
                } while (cursor.moveToNext());
            }
        }

        // close connection
        cursor.close();

        // set data into liveData
        itemCartLiveData.postValue(itemCartList);
    }



    // delete item
    public void deleteItem(int id) {
        SQLiteDatabase db = itemDatabase.getWritableDatabase();
        String query = "DELETE FROM " + ItemDatabase.TABLE_NAME + " WHERE " + ItemDatabase.COLUMN_ID + " = " + id;
        db.execSQL(query);
        readAllData();
    }

    // update quantity of item
    public void updateQuantity(int id, int quantity) {
        SQLiteDatabase db = itemDatabase.getWritableDatabase();
        String query = "UPDATE " + ItemDatabase.TABLE_NAME + " SET " + ItemDatabase.COLUMN_ITEM_QUANTITY + " = " + quantity + " WHERE " + ItemDatabase.COLUMN_ID + " = " + id;
        db.execSQL(query);
        readAllData();
    }

    // update the price of item
    public void updatePrice(int id,int price) {
        SQLiteDatabase db = itemDatabase.getWritableDatabase();
        String query = "UPDATE " + ItemDatabase.TABLE_NAME + " SET " + ItemDatabase.COLUMN_ITEM_PRICE + " = " + price + " WHERE " + ItemDatabase.COLUMN_ID + " = " + id;
        db.execSQL(query);
        readAllData();
    }
}
