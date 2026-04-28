package com.felipesilva.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class StockService {

    private final String apiKey;
    private final OkHttpClient client = new OkHttpClient();

    public StockService(String apiKey) {
        this.apiKey = apiKey;
    }

    public double getPrice(String symbol) throws IOException {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="
                + symbol + ".SAO&apikey="
                + apiKey;
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                throw new IOException("Response body is null");
            }
            String responseBody = response.body().string();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
            JsonObject quote = json.getAsJsonObject("Global Quote");
            return Double.parseDouble(quote.get("05. price").getAsString());
        }
    }
}