package com.example.pwmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StorePasswordActivity extends AppCompatActivity {
    private EditText webName;
    private EditText password;
    private Button suggest;
    private Button storePass;
    private StorageDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_pass);

        webName = findViewById(R.id.enter_site_name);
        password = findViewById(R.id.enter_password);
        suggest = findViewById(R.id.suggest_password_button);
        storePass = findViewById(R.id.add_password_button);
        db = new StorageDatabaseHelper(this);

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password.setText(newPass(), TextView.BufferType.EDITABLE);
                Toast toast = Toast.makeText(StorePasswordActivity.this, "New Password Created", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        storePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.insertAccount(webName.getText().toString(), password.getText().toString());
                Intent i = new Intent(StorePasswordActivity.this, RetrievePasswordActivity.class);  //For testing
                startActivity(i);
            }
        });
    }

    public String newPass(){
        String pass = "";
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int count = 15;
        while (count -- != 0){
            int place = (int) (Math.random() * chars.length());
            pass+=chars.charAt(place);
        }
        return pass;
    }


}
