package com.example.mycart.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.os.HandlerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycart.R;
import com.example.mycart.models.Item;
import com.example.mycart.threads.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private List<Item> itemList;
    private OnItemClickListener onItemClickListener;

    public ItemAdapter(Context context, List<Item> itemList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.itemList = itemList;
        this.onItemClickListener = onItemClickListener;
    }

    public void setFilteredList(List<Item> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

        Item item = itemList.get(position);
        holder.itemNameTv.setText(item.getName());
        holder.itemPriceTv.setText("₫ " + item.convertToVietNamDong());

        Constants.executor.execute(() -> {
            Bitmap bitmap = downloadImage(item.getThumbnail());
            if (bitmap != null) {
                handler.post(() -> holder.itemImageView.setImageBitmap(bitmap));
            }
        });

        holder.addToCartBtn.setOnClickListener(v -> {
            onItemClickListener.onAddToCartBtnCLicked(item);
        });
    }

    @Override
    public int getItemCount() {
        if (itemList == null) {
            return 0;
        }
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImageView , addToCartBtn;
        private TextView itemNameTv, itemPriceTv;
        private CardView cardView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.eachItemCardView);
            addToCartBtn = itemView.findViewById(R.id.eachItemAddToCartBtn);
            itemNameTv = itemView.findViewById(R.id.eachItemName);
            itemImageView = itemView.findViewById(R.id.eachItemIv);
            itemPriceTv = itemView.findViewById(R.id.eachItemPriceTv);
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


    public interface OnItemClickListener {
        void onAddToCartBtnCLicked(Item item);
    }

}
