package com.example.fetchandroid;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for creating and managing Retrofit client instances.
 * Ensures that only a single instance of the Retrofit client is created during the application's lifecycle.
 */
public class RetrofitClient {
    // Base URL for the Fetch Rewards API
     static final String BASE_URL = "https://fetch-hiring.s3.amazonaws.com/";
    private static Retrofit retrofit = null;

    /**
     * Gets the singleton instance of Retrofit client.
     * If no instance has been created yet, it initializes a new instance with the base URL and the Gson converter factory.
     * The Gson converter is used for automatic deserialization of the response into Java objects.
     *
     * @return The singleton Retrofit instance.
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())  // Add Gson converter factory for JSON deserialization
                    .build();
        }
        return retrofit;
    }
}