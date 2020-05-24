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
    private TextView gallon_tv;
    private TextView liter_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        kg_tv = findViewById(R.id.kg_tv);
        lb_tv = findViewById(R.id.lb_tv);
        gallon_tv = findViewById(R.id.gallon_tv);
        liter_tv = findViewById(R.id.liter_tv);

        gallon_tv.addTextChangedListener(generateTextWatcher(gallon_tv,liter_tv));
        liter_tv.addTextChangedListener(generateTextWatcher(liter_tv,gallon_tv));
        kg_tv.addTextChangedListener(generateTextWatcher(kg_tv,lb_tv));
        lb_tv.addTextChangedListener(generateTextWatcher(lb_tv,kg_tv));
    }

    private TextWatcher generateTextWatcher(final TextView focused_tv, final TextView toChange_tv) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("DefaultLocale")  // TODO: Look into this.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (focused_tv.hasFocus()) {
                    if (!s.toString().trim().isEmpty()) {
                        double inputNum = Double.parseDouble(s.toString());
                        double result = convert(inputNum, (String)focused_tv.getTag());
                        toChange_tv.setText(String.format("%.3f", result));
                    } else {
                        toChange_tv.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    private double convert(double op, String operation) {   // op = operand -> the measurement in the 'operation' string
        double KG_LB_RATIO = 2.20462;
        double LITER_GAL_RATION = 0.264172;

        switch (operation){    // A switch on the textView's tags
            case "kg":
                return op * KG_LB_RATIO;
            case "lb":
                return op / KG_LB_RATIO;
            case "liter":
                return op * LITER_GAL_RATION;
            case "gallon":
                return op / LITER_GAL_RATION;

            default: return 0;
        }
    }
}
