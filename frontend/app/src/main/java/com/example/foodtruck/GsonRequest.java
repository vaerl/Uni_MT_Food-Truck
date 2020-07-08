package com.example.foodtruck;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class GsonRequest<E, T> extends Request<T> {

    private String TAG = getClass().getSimpleName();

    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
        Log.d(TAG, "deserialized LocalDateTime " + LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()).toString());
        return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
    }).registerTypeAdapter(Duration.class, (JsonDeserializer<Duration>) (json, type, jsonDeserializationContext) -> {
        Log.d(TAG, "deserialized Duration " +Duration.parse(json.getAsJsonPrimitive().getAsString()).toString());
        return Duration.parse(json.getAsJsonPrimitive().getAsString());
    }).create();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;
    private Object dataIn;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, E dataIn, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.dataIn = dataIn;
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        Log.d(TAG, "deliverResponse: recieved response from backend.");
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody() {
        return gson.toJson(dataIn).getBytes();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.d(TAG, "parseNetworkResponse: response-data: " + json);
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}