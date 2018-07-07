package edu.wision_assignment.ui;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.wision_assignment.BaseActivity;
import edu.wision_assignment.R;
import edu.wision_assignment.model.User;
import edu.wision_assignment.util.DbInit;

public class LoginActivity extends BaseActivity {


    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnSubmit;
    private TextView txtRegister;
    private DbInit dbInit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
        dbInit = Room.databaseBuilder(getApplicationContext(),
                DbInit.class, "sample-db").build();
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getIntentToActivity(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginUser().execute();
            }
        });

    }

    private class LoginUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(LoginActivity.this, "Logging you...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Get From db
            User user = dbInit.userDao().getSingleRecord(edtEmail.getText().toString());
            if (user != null) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                        setLogin(initSharedPref());
                        startActivity(getIntentToActivity(LoginActivity.this, DashboardActivity.class));
                    }
                });

            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


}
