package edu.wision_assignment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {



    public Intent getIntentToActivity(Context first, Class<? extends AppCompatActivity> second) {
        Intent intent = new Intent(first, second);
        return intent;
    }


}
