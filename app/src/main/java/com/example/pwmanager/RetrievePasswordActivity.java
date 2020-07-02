package com.example.pwmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RetrievePasswordActivity extends AppCompatActivity {
    private List<InputAccount> accounts;
    private StorageDatabaseHelper db;
    private Toolbar toolbar;
    private int accID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pass);
        toolbar = findViewById(R.id.retrieve_toolbar);
        db = new StorageDatabaseHelper(this);
        accID = getIntent().getIntExtra("account_id", 0);
        accounts = db.getAccounts(accID);

        RecyclerView rv = findViewById(R.id.accounts_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));  //Positions the items
        rv.setAdapter(new ExerciseRVAdapter(accounts));
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(RetrievePasswordActivity.this, StorePasswordActivity.class);
        i.putExtra("account_id", accID);
        finish();
        overridePendingTransition(0,0);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    private class ExerciseRVAdapter extends RecyclerView.Adapter<ExerciseRVAdapter.ExerciseViewHolder> {
        List<InputAccount> accounts;

        public ExerciseRVAdapter(List<InputAccount> accounts) {
            this.accounts = accounts;
        }

        public class ExerciseViewHolder extends RecyclerView.ViewHolder {
            TextView name;
            TextView pass;

            ExerciseViewHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.display_name);
                pass = itemView.findViewById(R.id.display_pass);
            }
        }

        @Override
        public int getItemCount() {
            return accounts.size();
        }

        @Override
        public ExerciseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.account_card, viewGroup, false);
            ExerciseViewHolder exerciseViewHolder = new ExerciseViewHolder(view);
            return exerciseViewHolder;
        }

        @Override
        public void onBindViewHolder(final ExerciseViewHolder evh, int i) {
            evh.name.setText(accounts.get(i).getName());
            evh.pass.setText(accounts.get(i).getPass());
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

    }
}
