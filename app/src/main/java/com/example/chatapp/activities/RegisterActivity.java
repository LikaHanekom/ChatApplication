package com.example.chatapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.chatapp.database.UserDao;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapp.R;
import com.example.chatapp.database.ChatDatabaseHelper;
import com.example.chatapp.database.User;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);   // load my XML layout onto the screen

        UserDao userDao = ChatDatabaseHelper.getInstance(this).userDao();

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        EditText confirmpasswordEditText = findViewById(R.id.confirm_password);
        Button registerButton = findViewById(R.id.btn_register);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmpasswordEditText.getText().toString();
                if(!password.equals(confirmPassword)){
                    Toast.makeText(RegisterActivity.this, "Your Passwords do not match, please try again", Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(() -> {
                        User newUser = new User();
                        newUser.setUsername(username);
                        newUser.setPassword(password);
                        userDao.insertUser(newUser);

                        runOnUiThread(() -> {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    }).start();
                }
            }
        });

    }
}
