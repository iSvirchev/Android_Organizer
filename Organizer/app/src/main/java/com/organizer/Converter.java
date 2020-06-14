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
    private TextView celsius_tv;
    private TextView fahrenheit_tv;
    private TextView ml_tv;
    private TextView oz_tv;
    private TextView km_tv;
    private TextView miles_tv;
    private TextView m_tv;
    private TextView ft_tv;
    private TextView cm_tv;
    private TextView in_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        kg_tv = findViewById(R.id.kg_tv);
        lb_tv = findViewById(R.id.lb_tv);
        gallon_tv = findViewById(R.id.gallon_tv);
        liter_tv = findViewById(R.id.liter_tv);
        celsius_tv = findViewById(R.id.celsius_tv);
        fahrenheit_tv = findViewById(R.id.fahrenheit_tv);
        ml_tv = findViewById(R.id.ml_tv);
        oz_tv = findViewById(R.id.oz_tv);
        km_tv = findViewById(R.id.km_tv);
        miles_tv = findViewById(R.id.miles_tv);
        m_tv = findViewById(R.id.m_tv);
        ft_tv = findViewById(R.id.ft_tv);
        cm_tv = findViewById(R.id.cm_tv);
        in_tv = findViewById(R.id.in_tv);

        gallon_tv.addTextChangedListener(generateTextWatcher(gallon_tv,liter_tv));
        liter_tv.addTextChangedListener(generateTextWatcher(liter_tv,gallon_tv));
        kg_tv.addTextChangedListener(generateTextWatcher(kg_tv,lb_tv));
        lb_tv.addTextChangedListener(generateTextWatcher(lb_tv,kg_tv));
        celsius_tv.addTextChangedListener(generateTextWatcher(celsius_tv, fahrenheit_tv));
        fahrenheit_tv.addTextChangedListener(generateTextWatcher(fahrenheit_tv, celsius_tv));
        ml_tv.addTextChangedListener(generateTextWatcher(ml_tv, oz_tv));
        oz_tv.addTextChangedListener(generateTextWatcher(oz_tv, ml_tv));
        km_tv.addTextChangedListener(generateTextWatcher(km_tv, miles_tv));
        miles_tv.addTextChangedListener(generateTextWatcher(miles_tv, km_tv));
        m_tv.addTextChangedListener(generateTextWatcher(m_tv, ft_tv));
        ft_tv.addTextChangedListener(generateTextWatcher(ft_tv, m_tv));
        cm_tv.addTextChangedListener(generateTextWatcher(cm_tv, in_tv));
        in_tv.addTextChangedListener(generateTextWatcher(in_tv, cm_tv));
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
                    String input = s.toString().trim();
                    if (   !input.isEmpty() &&
                            !input.equals(".") &&
                            !input.equals("-") ) {
                        double inputNum = Double.parseDouble(s.toString());
                        String tag = (String) focused_tv.getTag();
                        double result = convert(inputNum, tag);
                        if (tag.equals("cel") || tag.equals("fahren")){
                            toChange_tv.setText(String.format("%.0f", result));
                        }else{
                            toChange_tv.setText(String.format("%.3f", result));
                        }
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
        double LITER_GAL_RATIO = 0.264172;
        double ML_OZ_RATIO = 0.033814;
        double KM_MILES_RATIO = 0.62137;
        double M_FT_RATIO = 3.2808;
        double CM_IN_RATION = 0.39370;

        switch (operation){    // A switch on the editView's tags
            case "kg":
                return op * KG_LB_RATIO;
            case "lb":
                return op / KG_LB_RATIO;
            case "liter":
                return op * LITER_GAL_RATIO;
            case "gallon":
                return op / LITER_GAL_RATIO;
            case "cel":
                return (op * 1.8) + 32;
            case "fahren":
                return (op - 32) * 0.5556;
            case "ml":
                return op * ML_OZ_RATIO;
            case "oz":
                return op / ML_OZ_RATIO;
            case "km":
                return op * KM_MILES_RATIO;
            case "miles":
                return op / KM_MILES_RATIO;
            case "m":
                return op * M_FT_RATIO;
            case "ft":
                return op / M_FT_RATIO;
            case "cm":
                return op * CM_IN_RATION;
            case "in":
                return op / CM_IN_RATION;

            default: return 0;
        }
    }
}
