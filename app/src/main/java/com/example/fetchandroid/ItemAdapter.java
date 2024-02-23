package com.example.fetchandroid;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for the RecyclerView to display items.
 * The adapter manages the data model and adapts it to individual entries in the widget.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    // List of items to be displayed in the RecyclerView
    private final List<Item> itemList;
    // Flag to determine whether the item ID should be displayed
    private Boolean showId = false;
    /**
     * Constructor for the ItemAdapter.
     * @param itemList A list of Item objects to be displayed.
     */
    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = itemList.get(position);
        String displayText = showId ? "List Id " + item.getListId() + " : " + item.getName() : item.getName();
        holder.textView.setText(displayText);
    }
    /**
     * Toggles the visibility of the item ID in the list.
     * Uses notifyDataSetChanged to update the list items after changing the visibility state.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void toggleItemID() {
        showId = !showId;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}