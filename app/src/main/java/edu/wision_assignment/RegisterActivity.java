package edu.wision_assignment;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import edu.wision_assignment.model.User;
import edu.wision_assignment.pojo.Users;
import edu.wision_assignment.util.DbInit;

public class RegisterActivity extends BaseActivity {


    private EditText edtName;
    private EditText edtAge;
    private EditText edtEmail;
    private Spinner spinGender;
    private Button btnRegister;
    private EditText edtPassword;
    private Toolbar toolbar;
    private DbInit dbInit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        spinGender = (Spinner) findViewById(R.id.spinGender);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        dbInit = Room.databaseBuilder(getApplicationContext(),
                DbInit.class, "sample-db").build();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = edtAge.getText().toString();
                String email = edtEmail.getText().toString();
                String name = edtName.getText().toString();
                if (!age.equals("") || !email.equals("") || !name.equals("")) {
                    new RegisterUser().execute();
                } else {
                    Toast.makeText(RegisterActivity.this, "Fill All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private class RegisterUser extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(RegisterActivity.this, "Regestering...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            User user = new User();
            Users users = new Users();
            users.setAge(edtAge.getText().toString());
            users.setEmail(edtEmail.getText().toString());
            users.setPassword(edtPassword.getText().toString());
            users.setGender(spinGender.getSelectedItem().toString());
            users.setName(edtName.getText().toString());
            user.setUsers(users);

            //Insert Into db
            dbInit.userDao().insertOnlySingleRecord(user);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();
        }
    }
}


