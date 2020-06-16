package com.organizer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class TimeConverter extends AppCompatActivity {
    private final int YEAR_TO_MONTH_RATIO = 12;
    private TextView years_tv;
    private TextView extra_months_tv;
    private TextView total_months_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_converter);

        years_tv = findViewById(R.id.years_tv);
        extra_months_tv = findViewById(R.id.extra_months_tv);
        total_months_tv = findViewById(R.id.total_months_tv);

        years_tv.addTextChangedListener(generateMainToTotalTW(years_tv, extra_months_tv, total_months_tv));
        extra_months_tv.addTextChangedListener(generateMainToTotalTW(years_tv, extra_months_tv, total_months_tv));
        total_months_tv.addTextChangedListener(generateTotalTW(total_months_tv, years_tv, extra_months_tv));
    }

    private TextWatcher generateTotalTW(
            final TextView total_tv,
            final TextView main_tv,
            final TextView extra_tv) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(total_tv.hasFocus()){
                  int totalNum = getInputNum(s.toString().trim());
                  String totalTag = (String) total_tv.getTag();

                  if(totalNum != 0){
                      int[] mainAndExtra = convertUp(totalNum, totalTag);

                      if (mainAndExtra[0] == 0) main_tv.setText("");
                      else main_tv.setText(String.format("%d", mainAndExtra[0]));

                      if (mainAndExtra[1] == 0) extra_tv.setText("");
                      else extra_tv.setText(String.format("%d", mainAndExtra[1]));
                  }else{
                      main_tv.setText("");
                      extra_tv.setText("");
                  }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }


    private TextWatcher generateMainToTotalTW(
            final TextView main_tv,
            final TextView extra_tv,
            final TextView toChange_tv
    ) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(extra_tv.hasFocus() || main_tv.hasFocus()){
                    String mainInput = "";
                    String extraInput = "";

                    if (extra_tv.hasFocus()) {
                        mainInput = main_tv.getText().toString().trim();
                        extraInput = s.toString().trim();
                    } else if (main_tv.hasFocus()) {
                        mainInput = s.toString().trim();
                        extraInput = extra_tv.getText().toString().trim();
                    }

                    int mainNum = getInputNum(mainInput);
                    int extraNum = getInputNum(extraInput);

                    String focusedTag = (String) main_tv.getTag();
                    int mainToExtraFormat = convertDown(mainNum, focusedTag);

                    if (mainToExtraFormat == 0) {
                        if (extraNum != 0) toChange_tv.setText(extraInput);
                        else toChange_tv.setText("");
                    } else toChange_tv.setText(String.format("%d", mainToExtraFormat + extraNum));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }

    private int getInputNum(String focusedInput) {
        if (!focusedInput.isEmpty() &&
                !focusedInput.equals(".") &&
                !focusedInput.equals("-")) {
            return Integer.parseInt(focusedInput);
        } else {
            return 0;
        }
    }

    private int[] convertUp (int op, String operation){
        int[] array = new int[2];   // array[0] - main, array[1] - extra

        switch (operation){
            case "total_months":
                array[0] = op / YEAR_TO_MONTH_RATIO;
                array[1] = op % YEAR_TO_MONTH_RATIO;
        }
        return array;
    }

    private int convertDown(int op, String operation) {   // op = operand -> the measurement in the 'operation' string
        switch(operation){
            case "year_to_months":
               return op * YEAR_TO_MONTH_RATIO;
            default:
                return 0;
        }
    }
}