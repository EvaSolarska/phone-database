package com.example.phonedb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewPhoneActivity extends AppCompatActivity {
    public static final  String EXTRA_MANUFACTURER =
            "com.example.phonedb.EXTRA_MANUFACTURER";
    public static final  String EXTRA_MODEL =
            "com.example.phonedb.EXTRA_MODEL";
    public static final  String EXTRA_ANDROID_VERSION =
            "com.example.phonedb.EXTRA_ANDROID_VERSION";
    public static final  String EXTRA_WEBSITE =
            "com.example.phonedb.EXTRA_WEBSITE";
    EditText manufacturerEditText;
    EditText modelEditText;
    EditText androidVersionEditText;
    EditText websiteEditText;

    private void savePhone(){
        String manufacturer = manufacturerEditText.getText().toString();
        String model = modelEditText.getText().toString();
        String androidVersion = androidVersionEditText.getText().toString();
        String website = websiteEditText.getText().toString();

        if (TextUtils.isEmpty(manufacturer) || TextUtils.isEmpty(model) ||
                TextUtils.isEmpty(androidVersion) || TextUtils.isEmpty(website)) {
            Toast.makeText(this, "Fill in the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_MANUFACTURER, manufacturer);
        data.putExtra(EXTRA_MODEL, model);
        data.putExtra(EXTRA_WEBSITE, website);
        data.putExtra(EXTRA_ANDROID_VERSION, androidVersion);
        setResult(RESULT_OK, data);
        finish();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_phone);

        manufacturerEditText = findViewById(R.id.editTextManufacturer);
        websiteEditText = findViewById(R.id.editTextWebsite);
        androidVersionEditText = findViewById(R.id.editTextAndroidVersion);
        modelEditText = findViewById(R.id.editTextModel);

        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(view -> savePhone());

        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        Button websiteButton = findViewById(R.id.buttonWebsite);
        websiteButton.setOnClickListener(view -> {
                String website = websiteEditText.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + website));
                startActivity(intent);
        });
    }
}

