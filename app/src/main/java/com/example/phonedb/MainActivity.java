package com.example.phonedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {


    private PhoneViewModel mPhoneViewModel;
    private RecyclerView mRecyclerView;
    private PhoneListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new PhoneListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPhoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        mPhoneViewModel.getAllPhones().observe(this, phones -> mAdapter.setPhones(phones));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_data)
        {
            Toast.makeText(this,"Clearing the data...",
                    Toast.LENGTH_SHORT).show();
            mPhoneViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}