package com.example.phonedb;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    private FloatingActionButton mMainFab;

    private ActivityResultLauncher<Intent> mActivityResultLauncher;

    private void mainFabClicked(){
        Intent intent = new Intent(
                MainActivity.this,NewPhoneActivity.class);
            mActivityResultLauncher.launch(intent);
    }
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

        mActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),result -> {
                    if(result.getResultCode() == RESULT_OK){
                        String manufacturer = result.getData().getStringExtra(NewPhoneActivity.EXTRA_MANUFACTURER);
                        String model = result.getData().getStringExtra(NewPhoneActivity.EXTRA_MODEL);
                        String androidVersion = result.getData().getStringExtra(NewPhoneActivity.EXTRA_ANDROID_VERSION);
                        String website = result.getData().getStringExtra(NewPhoneActivity.EXTRA_WEBSITE);
                        Phone phone = new Phone(manufacturer, model, androidVersion, website);
                        mPhoneViewModel.insert(phone);
                    }
                });
            mMainFab = findViewById(R.id.fabMain);
            mMainFab.setOnClickListener(view -> mainFabClicked());
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