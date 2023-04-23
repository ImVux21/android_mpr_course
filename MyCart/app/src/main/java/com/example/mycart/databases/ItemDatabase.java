package com.example.mycart.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ItemDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "my_cart.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "my_cart";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ITEM_NAME = "item_name";
    public static final String COLUMN_ITEM_CATEGORY = "item_category";
    public static final String COLUMN_ITEM_THUMBNAIL = "item_thumbnail";
    public static final String COLUMN_ITEM_PRICE = "item_price";
    public static final String COLUMN_ITEM_QUANTITY = "item_quantity";

    public ItemDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_CATEGORY + " TEXT, " +
                COLUMN_ITEM_THUMBNAIL + " TEXT, " +
                COLUMN_ITEM_PRICE + " REAL, " +
                COLUMN_ITEM_QUANTITY + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
