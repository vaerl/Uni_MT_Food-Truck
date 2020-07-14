package com.example.foodtruck;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.foodtruck.model.order.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataService {

    private String TAG = getClass().getSimpleName();
    private static DataService dataService = new DataService();

    private static SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    //    public static String BACKEND_URL = "http://192.168.54.225:8080/api";
    public static String BACKEND_URL = "http://192.168.0.115:8080/api";
    public static String USER_TYPE_TAG = "user_type";
    public static String USER_ID_TAG = "user_id";
    public static String Location_ID_TAG = "location_id";
    public static int OPERATOR_ID = 1;

    private DataService() {
    }

    public static DataService getInstance(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return dataService;
    }

    public static Map<String, String> getStandardHeader() {
        Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        return params;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean setEntry(String tag, String value) {
        Log.d(TAG, "set entry " + tag + " to " + value);
        editor = preferences.edit();
        editor.putString(tag, value);
        editor.apply();
        return true;
    }

    public String getEntry(String tag) {
        return preferences.getString(tag, "getEntry-Default");
    }

    public boolean isPresent(String tag) {
        return !preferences.getString(tag, "getEntry-Default").equals("getEntry-Default");
    }

    public boolean getUserType(UserType type) {
        return setEntry(USER_TYPE_TAG, type.toString());
    }

    public UserType getUserType() {
        if (preferences.getString(USER_TYPE_TAG, UserType.CUSTOMER.toString()).equals(UserType.CUSTOMER.toString())) {
            return UserType.CUSTOMER;
        } else {
            return UserType.OPERATOR;
        }
    }

    public boolean setUserId(Long id) {
        return setEntry(USER_ID_TAG, String.valueOf(id));
    }

    public String getUserId() {
        return getEntry(USER_ID_TAG);
    }


    public boolean setLocationId(Long id) {
        return setEntry(Location_ID_TAG, String.valueOf(id));
    }

    public String getLocationId() {
        return getEntry(Location_ID_TAG);
    }

    public enum UserType {
        CUSTOMER, OPERATOR
    }

    public static String translate(Order.Status status) {
        switch (status) {
            case DONE:
                return "Fertig";
            case STARTED:
                return "Begonnen";
            case ACCEPTED:
                return "Akzeptiert";
            case CONFIRMED:
                return "Bestätigt";
            case NOT_POSSIBLE:
                return "Nicht möglich";
            default:
                return "ERROR";
        }
    }
}
