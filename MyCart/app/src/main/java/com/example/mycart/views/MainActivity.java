package com.example.mycart.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.mycart.R;
import com.example.mycart.adapters.ItemAdapter;
import com.example.mycart.models.Item;
import com.example.mycart.models.ItemCart;
import com.example.mycart.viewmodel.CartViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    private CartViewModel cartViewModel;
    private RecyclerView recyclerView;
    private List<Item> itemList;
    public static List<ItemCart> itemCartList;
    private ItemAdapter itemAdapter;
    private ImageView cartImageView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initializeVariables();
        setUpList();

        itemAdapter.setItemList(itemList);
        recyclerView.setAdapter(itemAdapter);

        cartImageView.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItemList(newText);
                return true;
            }
        });
    }

    private void filterItemList(String newText) {
        List<Item> filteredList = new ArrayList<>();

        for (Item item : itemList) {
            if (item.getName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);
            }
        }

        itemAdapter.setFilteredList(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cartViewModel.getAllCartItems().observe(this, itemCarts -> itemCartList.addAll(itemCarts));
    }

    private void setUpList() {
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
        handler.post(() -> {
            String data = loadData();

            if (data == null) {
                Toast.makeText(MainActivity.this, "No data found!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONArray jsonArray = new JSONArray(data);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String thumbnail = jsonObject.getString("thumbnail");
                    String name = jsonObject.getString("name");
                    String category = jsonObject.getString("category");
                    int unitPrice = jsonObject.getInt("unitPrice");

                    Item item = new Item(id, thumbnail, name, category, unitPrice);
                    itemList.add(item);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private String loadData() {
        URL url;
        HttpsURLConnection urlConnection;

        try {
            url = new URL("https://hanu-congnv.github.io/mpr-cart-api/products.json");
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void initializeVariables() {
        searchView = findViewById(R.id.search_bar);
        cartImageView = findViewById(R.id.cartIv);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        itemCartList = new ArrayList<>();
        itemList = new ArrayList<>();


        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        itemAdapter = new ItemAdapter(this, itemList, this);
    }

    @Override
    public void onAddToCartBtnCLicked(Item item) {
        ItemCart itemCart = new ItemCart();

        itemCart.setName(item.getName());
        itemCart.setCategory(item.getCategory());
        itemCart.setThumbnail(item.getThumbnail());
        itemCart.setPrice(item.getUnitPrice());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!itemCartList.isEmpty()) {
            for (ItemCart itemCart1 : itemCartList) {
                if (itemCart1.getName().equals(itemCart.getName())) {
                    quantity[0] = itemCart1.getQuantity();
                    quantity[0]++;
                    id[0] = itemCart1.getId();
                }
            }
        }

        if (quantity[0] == 1) {
            // In database
            itemCart.setQuantity(quantity[0]);
            cartViewModel.insertCartItem(itemCart);
        } else {
            // In Database
            cartViewModel.updatePrice(id[0], itemCart.getPrice() * quantity[0]);
            cartViewModel.updateQuantity(id[0], quantity[0]);
        }

        Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show();
    }
}