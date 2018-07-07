package edu.wision_assignment.ui;

import android.os.Bundle;
import android.widget.TextView;

import edu.wision_assignment.BaseActivity;
import edu.wision_assignment.R;

public class SplashActivity extends BaseActivity {

    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /*
         * TODO Check to be performed for user already logged In
         */
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(getIntentToActivity(SplashActivity.this, LoginActivity.class));
                }
            }
        };
        thread.start();
    }
}
