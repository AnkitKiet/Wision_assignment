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

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (isLoggedIn(initSharedPref())) {
                        startActivity(setClearFlags(getIntentToActivity(SplashActivity.this, DashboardActivity.class)));
                    } else {
                        startActivity(getIntentToActivity(SplashActivity.this, LoginActivity.class));
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
