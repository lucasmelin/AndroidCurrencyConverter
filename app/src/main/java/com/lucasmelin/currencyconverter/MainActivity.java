package com.lucasmelin.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_CONVERSION = "com.lucasmelin.currencyconverter.CONVERSION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user selects a currency radio button
     */
    public void onRadioButtonClicked(View view) {
        // Check if the button is now checked
        boolean checked = ((RadioButton) view).isChecked();

        Double conversion = null;

        // Check which currency was selected
        switch (view.getId()) {
            case R.id.radio_usd:
                if (checked) {
                    // Convert to USD
                    conversion = 0.78;
                }
                break;
            case R.id.radio_eur:
                if (checked) {
                    // Convert to EUR
                    conversion = 0.64;
                }
                break;
            case R.id.radio_cny:
                if (checked) {
                    // Convert to CNY
                    conversion = 4.93;
                }
                break;
        }

        Intent intent = new Intent(this, ConvertCurrencyActivity.class);
        intent.putExtra(EXTRA_CONVERSION, conversion);
        startActivity(intent);
    }
}
