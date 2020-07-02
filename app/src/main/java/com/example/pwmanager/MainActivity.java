package com.example.pwmanager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private EditText username;
    private EditText password;
    private CheckBox showPassword;
    private TextView signUp;
    StorageDatabaseHelper helper = new StorageDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.login_button);
        username = findViewById(R.id.username_login_text);
        password = findViewById(R.id.password_login_text);
        showPassword = findViewById(R.id.show_hide_password);
        signUp = findViewById(R.id.login_screen_sign_up);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin(username.getText().toString(), password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpScreenActivity.class);
                finish();
                overridePendingTransition(0,0);
                startActivity(intent);
            }
        });
    }

    public void validateLogin(String username, String password){
        if (helper.checkPass(username, password)){
            int id = helper.getAccountID(username);
            Intent intent = new Intent(MainActivity.this, StorePasswordActivity.class);
            intent.putExtra("account_id", id);
            finish();
            overridePendingTransition(0,0);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this, "Username and password don't match", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkBoxClicked(View view) {
        if (showPassword.isChecked()){
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            password.setSelection(password.length());
        }
        else {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setSelection(password.length());
        }
    }

}
