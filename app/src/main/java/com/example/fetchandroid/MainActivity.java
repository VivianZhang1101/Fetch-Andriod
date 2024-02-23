package com.example.fetchandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvErrorMessage;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private boolean isShowingId = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Item Name");
        final ImageButton toggleButton = findViewById(R.id.toggleButton);
        final TextView tvToggleState = findViewById(R.id.tvToggleState);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowingId = !isShowingId;
                if (isShowingId) {
                    toggleButton.setImageResource(R.drawable.visibility);
                    tvToggleState.setText(R.string.hide_list_id);
                } else {
                    toggleButton.setImageResource(R.drawable.visibility_off);
                    tvToggleState.setText(R.string.show_list_id);
                }
                adapter.toggleItemID();
            }
        });

        progressBar = findViewById(R.id.progressBar);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize itemList
        itemList = new ArrayList<>();

        // Fetch data
        fetchData();

        // Set adapter
        adapter = new ItemAdapter(itemList);
        recyclerView.setAdapter(adapter);


    }
    private void fetchData() {
        Retrofit retrofit = RetrofitClient.getClient();
        FetchService service = retrofit.create(FetchService.class);
        Call<List<Item>> call = service.getItems();

        call.enqueue(new Callback<List<Item>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                // On Response, hide loading indicator
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> items = response.body();
                    List<Item> filteredItems = items.stream()
                            .filter(item -> item.getName() != null && !item.getName().trim().isEmpty())
                            .collect(Collectors.toList());

                    // Sort the list by listId and then by name
                    filteredItems.sort((item1, item2) -> {
                        if (item1.getListId() != item2.getListId()) {
                            return Integer.compare(item1.getListId(), item2.getListId());
                        } else {
                            return item1.getName().compareTo(item2.getName());
                        }
                    });

                    // Update the itemList and notify the adapter
                    itemList.clear();
                    itemList.addAll(filteredItems);
                    adapter.notifyDataSetChanged();
                    tvErrorMessage.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    // Show error message
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, Throwable t) {
                // Hide loading indicator
                progressBar.setVisibility(View.GONE);

                // Show error message
                tvErrorMessage.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);

                Log.e("MainActivity", "Error fetching data", t);
            }
        });
    }
}