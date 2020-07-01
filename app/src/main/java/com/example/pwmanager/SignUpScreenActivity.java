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


public class SignUpScreenActivity extends AppCompatActivity {
    private Button signUp;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private CheckBox showPassword;
    private TextView alreadyRegistered;
    private EditText email;
    LoginDatabaseHelper helper = new LoginDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        signUp =  findViewById(R.id.register_button);
        username =  findViewById(R.id.username_sign_up_text);
        password =  findViewById(R.id.password_sign_up_text);
        confirmPassword =  findViewById(R.id.confirm_password_sign_up_text);
        showPassword =  findViewById(R.id.sign_up_show_hide_password);
        alreadyRegistered =  findViewById(R.id.already_registered_text);
        email =  findViewById(R.id.email_sign_up_text);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpClick(username.getText().toString(), email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signUpShowPasswordClicked(View view) {
        if (showPassword.isChecked()){
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            password.setSelection(password.length());

            confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            confirmPassword.setSelection(confirmPassword.length());
        }
        else {
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            password.setSelection(password.length());

            confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            confirmPassword.setSelection(confirmPassword.length());
        }
    }

    public void onSignUpClick(String username, String email, String pass, String confirmPass){
        if (!pass.equals(confirmPass)){
            Toast passToast = Toast.makeText(SignUpScreenActivity.this, "Passwords don't match", Toast.LENGTH_SHORT);
            passToast.show();
        } else if(username.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            Toast.makeText(SignUpScreenActivity.this, "Fill in all data", Toast.LENGTH_SHORT).show();
        }
        else{
            Contact c = new Contact();
            c.setEmail(email);
            c.setUsername(username);
            c.setPassword(pass);
            helper.insertAccount(c);
            Intent intent = new Intent(SignUpScreenActivity.this, MainActivity.class);
            startActivity(intent);
        }


    }
}
