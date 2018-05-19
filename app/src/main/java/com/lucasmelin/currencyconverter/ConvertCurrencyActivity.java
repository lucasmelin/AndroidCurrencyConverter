package com.lucasmelin.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;

public class ConvertCurrencyActivity extends AppCompatActivity {
    private Double conversion;

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
        double cdn_dollars;
        double local_amount;
        // Regex to validate the input, even though the EditText is set
        // to entering only numbers
        String validDouble = "^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$";
        // Format to use when outputting converted currency
        DecimalFormat currencyFormat = new DecimalFormat("00.00");


        EditText cdn_amount_text = findViewById(R.id.cdn_dollars);
        EditText local_currency_text = findViewById(R.id.local_currency_amount);

        // Get the amounts that have been entered in the EditText boxes
        String cdnTextValue = cdn_amount_text.getText().toString();
        String localTextValue = local_currency_text.getText().toString();

        // If a valid number was entered in the CDN EditText field, convert to
        // LocalCurrency
        if (!"".equals(cdnTextValue) && cdnTextValue.matches(validDouble)) {
            // Convert from CDN to Local Currency
            cdn_dollars = Double.parseDouble(cdnTextValue);
            local_amount = cdn_dollars / conversion;
            // Format the double to a string with two decimal places before outputting
            local_currency_text.setText(currencyFormat.format(local_amount));
        } else if (!"".equals(localTextValue) && localTextValue.matches(validDouble)) {
            // Convert from Local Currency to CDN
            local_amount = Double.parseDouble(localTextValue);
            cdn_dollars = local_amount * conversion;
            // Format the double to a string with two decimal places before outputting
            cdn_amount_text.setText(currencyFormat.format(cdn_dollars));
        }


    }

    /**
     * Called when the user taps the Clear Amounts button
     */
    public void clearCurrencyFields(View view) {
        // Get both EditText fields
        EditText cdn_amount_text = findViewById(R.id.cdn_dollars);
        EditText local_currency_text = findViewById(R.id.local_currency_amount);
        // Clear the EditText fields
        cdn_amount_text.getText().clear();
        local_currency_text.getText().clear();
    }


}
