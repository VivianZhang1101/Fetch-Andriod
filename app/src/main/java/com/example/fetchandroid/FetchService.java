package com.example.fetchandroid;
import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

/**
 * The FetchService interface defines the HTTP operations needed for the network communication for this project.
 */
public interface FetchService {
    /**
     * Get a list of items from the server.
     * The @GET annotation denotes that this method performs an HTTP GET request to the specified path.
     * "hiring.json" is appended to the base URL provided by the Retrofit instance.
     * The generic type List<Item> represents the expected response body type.
     * @return A Retrofit Call object that, when executed, will make an HTTP request to retrieve a list of Item objects.
     */
    @GET("hiring.json")
    Call<List<Item>> getItems();
}