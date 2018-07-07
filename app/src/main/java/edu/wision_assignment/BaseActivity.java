package edu.wision_assignment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static final String ISLOGIN = "islogin";

    public Intent getIntentToActivity(Context first, Class<? extends AppCompatActivity> second) {
        Intent intent = new Intent(first, second);
        return intent;
    }

    public SharedPreferences initSharedPref() {
        pref = getApplication().getSharedPreferences("base_db", MODE_PRIVATE);
        return pref;
    }

    public boolean isLoggedIn(SharedPreferences pref) {
        if (pref.getBoolean(ISLOGIN, false)) {
            return true;
        } else {
            return false;
        }
    }

    public void setLogin(SharedPreferences pref) {
        editor = pref.edit();
        editor.putBoolean(ISLOGIN, true);
        editor.apply();
    }

    public void logout(SharedPreferences pref) {
        editor = pref.edit();
        editor.putBoolean(ISLOGIN, false);
        editor.apply();
    }

}
