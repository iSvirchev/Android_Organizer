package com.organizer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Converter extends AppCompatActivity {
    private TextView kg_tv;
    private TextView lb_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        kg_tv = findViewById(R.id.kg_tv);
        lb_tv = findViewById(R.id.lb_tv);

        kg_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(kg_tv.hasFocus()) {
                    if (!s.toString().trim().isEmpty()) {
                        double inputNum = Double.parseDouble(s.toString());
                        double result = convert(inputNum, "kg");
                        lb_tv.setText(String.format("%.2f", result));
                    }else{
                        lb_tv.setText("0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lb_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(lb_tv.hasFocus()) {
                    if (!s.toString().trim().isEmpty()) {
                        double inputNum = Double.parseDouble(s.toString());
                        double result = convert(inputNum, "lb");
                        kg_tv.setText(String.format("%.2f", result));
                    }else{
                        kg_tv.setText("0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private double convert(double op, String operation) {
        double KG_LB_RATIO = 2.20462;
        switch (operation){
            case "kg":
                return op* KG_LB_RATIO;
            case "lb":
                return op/ KG_LB_RATIO;
            default: return 0;
        }
    }
}
