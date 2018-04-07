package com.lucasmelin.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.lucasmelin.currencyconverter.MESSAGE";
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

        // Check which currency was selected
        switch (view.getId()) {
            case R.id.radio_usd:
                if (checked) {
                    // Convert to USD
                }
                break;
            case R.id.radio_eur:
                if (checked) {
                    // Convert to EUR
                }
                break;
            case R.id.radio_cny:
                if (checked) {
                    // Convert to CNY
                }
                break;
        }

        Intent intent = new Intent(this, ConvertCurrencyActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
