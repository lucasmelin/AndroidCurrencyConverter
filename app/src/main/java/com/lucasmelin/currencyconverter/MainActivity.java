package com.lucasmelin.currencyconverter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_CONVERSION = "com.lucasmelin.currencyconverter.CONVERSION";
    public Double usd_conversion = 0.78;
    public Double eur_conversion = 0.64;
    public Double cny_conversion = 4.93;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Call the API to retrieve the current conversion rates
        getConversionValues();
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
                    conversion = usd_conversion;
                }
                break;
            case R.id.radio_eur:
                if (checked) {
                    // Convert to EUR
                    conversion = eur_conversion;
                }
                break;
            case R.id.radio_cny:
                if (checked) {
                    // Convert to CNY
                    conversion = cny_conversion;
                }
                break;
        }

        Intent intent = new Intent(this, ConvertCurrencyActivity.class);
        intent.putExtra(EXTRA_CONVERSION, conversion);
        startActivity(intent);
    }

    public void getConversionValues() {
        String url = "https://www.bankofcanada.ca/valet/observations/group/FX_RATES_DAILY/json?recent=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject rates = response.getJSONArray("observations").getJSONObject(0);

                    usd_conversion = rates.getJSONObject("FXUSDCAD").getDouble("v");
                    eur_conversion = rates.getJSONObject("FXEURCAD").getDouble("v");
                    cny_conversion = rates.getJSONObject("FXCNYCAD").getDouble("v");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Purposely do nothing if we can't reach the API
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
}
