package com.organizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button converterActivityBtn = findViewById(R.id.converterActivityBtn);

        converterActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConverterActivity();
            }
        });
    }

    private void openConverterActivity() {
        Intent intent = new Intent(this, Converter.class);
        startActivity(intent);
    }
}
