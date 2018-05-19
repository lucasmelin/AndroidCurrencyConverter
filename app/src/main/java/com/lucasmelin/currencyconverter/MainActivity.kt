package com.lucasmelin.currencyconverter

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioButton
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private var usdConversion: Double? = 0.78
    private var eurConversion: Double? = 0.64
    private var cnyConversion: Double? = 4.93
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Call the API to retrieve the current conversion rates
        getConversionValues()
    }

    /**
     * Called when the user selects a currency radio button
     */
    fun onRadioButtonClicked(view: View) {
        // Check if the button is now checked
        val checked = (view as RadioButton).isChecked

        var conversion: Double? = null

        // Check which currency was selected
        when (view.getId()) {
            R.id.radio_usd -> if (checked) {
                // Convert to USD
                conversion = usdConversion
            }
            R.id.radio_eur -> if (checked) {
                // Convert to EUR
                conversion = eurConversion
            }
            R.id.radio_cny -> if (checked) {
                // Convert to CNY
                conversion = cnyConversion
            }
        }

        val intent = Intent(this, ConvertCurrencyActivity::class.java)
        intent.putExtra(EXTRA_CONVERSION, conversion)
        startActivity(intent)
    }

    fun getConversionValues() {
        val url = "https://www.bankofcanada.ca/valet/observations/group/FX_RATES_DAILY/json?recent=1"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                val rates = response.getJSONArray("observations").getJSONObject(0)

                usdConversion = rates.getJSONObject("FXUSDCAD").getDouble("v")
                eurConversion = rates.getJSONObject("FXEURCAD").getDouble("v")
                cnyConversion = rates.getJSONObject("FXCNYCAD").getDouble("v")

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener {
            // Purposely do nothing if we can't reach the API
        })
        Volley.newRequestQueue(this).add(jsonObjectRequest)
    }

    companion object {
        val EXTRA_CONVERSION = "com.lucasmelin.currencyconverter.CONVERSION"
    }
}
