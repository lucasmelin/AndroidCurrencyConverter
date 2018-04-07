package com.lucasmelin.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ConvertCurrencyActivity extends AppCompatActivity {
    public Double conversion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert_currency);

        // Get the Intent that started this activity and extract the conversion value
        Intent intent = getIntent();
        // Default exchange rate if not found is 1
        conversion = intent.getDoubleExtra(MainActivity.EXTRA_CONVERSION, 1);
    }

    /**
     * Called when the user taps the EXCHANGE button
     */
    public void convertCurrency(View view) {
        EditText cdn_amount_text = findViewById(R.id.cdn_dollars);
        EditText local_currency_text = findViewById(R.id.local_currency_amount);

        String cdnTextValue = cdn_amount_text.getText().toString();
        String localTextValue = local_currency_text.getText().toString();

        double cdn_dollars;
        double local_amount;
        String validDouble = "^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$";
        if (!"".equals(cdnTextValue) && cdnTextValue.matches(validDouble)) {
            // Convert from CDN to Local Currency
            cdn_dollars = Double.parseDouble(cdnTextValue);
            local_amount = cdn_dollars * conversion;
            local_currency_text.setText(String.valueOf(local_amount));
        } else if (!"".equals(localTextValue) && localTextValue.matches(validDouble)) {
            // Convert from Local Currency to CDN
            local_amount = Double.parseDouble(localTextValue);
            cdn_dollars = local_amount / conversion;
            cdn_amount_text.setText(String.valueOf(cdn_dollars));
        }


    }

    /**
     * Called when the user taps the Clear Amounts button
     */
    public void clearCurrencyFields(View view) {
        EditText cdn_amount_text = findViewById(R.id.cdn_dollars);
        EditText local_currency_text = findViewById(R.id.local_currency_amount);
        cdn_amount_text.getText().clear();
        local_currency_text.getText().clear();
    }


}
