package com.lucasmelin.currencyconverter

import android.content.Context
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
import org.json.JSONObject
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class MainActivity : AppCompatActivity() {
    private var audConversion: Double? = 0.9670
    private var brlConversion: Double? = 0.3443
    private var cnyConversion: Double? = 0.2019
    private var eurConversion: Double? = 1.5163
    private var hkdConversion: Double? = 0.164082
    private var inrConversion: Double? = 0.01894
    private var idrConversion: Double? = 0.000091
    private var jpyConversion: Double? = 0.01163
    private var myrConversion: Double? = 0.3243
    private var mxnConversion: Double? = 0.06468
    private var nzdConversion: Double? = 0.8902
    private var nokConversion: Double? = 0.1587
    private var penConversion: Double? = 0.3920
    private var rubConversion: Double? = 0.02066
    private var sarConversion: Double? = 0.3435
    private var sgdConversion: Double? = 0.9587
    private var zarConversion: Double? = 0.10106
    private var krwConversion: Double? = 0.001190
    private var sekConversion: Double? = 0.1472
    private var chfConversion: Double? = 1.2904
    private var twdConversion: Double? = 0.04300
    private var thbConversion: Double? = 0.04000
    private var tryConversion: Double? = 0.2869
    private var gbpConversion: Double? = 1.735
    private var usdConversion: Double? = 1.2880
    private var vndConversion: Double? = 0.000057

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Call the API to retrieve the current conversion rates
        val sharedPref = this@MainActivity.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = Long.MIN_VALUE
        val lastAPICall = sharedPref.getLong(getString(R.string.saved_api_call_key), defaultValue)
        val lastTimestamp =  Instant.ofEpochMilli(lastAPICall).atZone(ZoneId.systemDefault()).toLocalDate()
        val daysBetween = ChronoUnit.DAYS.between(lastTimestamp, LocalDate.now())

        if (daysBetween > 1){
            getConversionValues()
        }

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

    private fun getConversionValues() {
        val url = "https://www.bankofcanada.ca/valet/observations/group/FX_RATES_DAILY/json?recent=1"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                val rates = response.getJSONArray("observations").getJSONObject(0)

                audConversion = getExchangeRate(rates, "FXAUDCAD")
                brlConversion = getExchangeRate(rates, "FXBRLCAD")
                cnyConversion = getExchangeRate(rates, "FXCNYCAD")
                hkdConversion = getExchangeRate(rates, "FXHKDCAD")
                inrConversion = getExchangeRate(rates, "FXINRCAD")
                idrConversion = getExchangeRate(rates, "FXIDRCAD")
                jpyConversion = getExchangeRate(rates, "FXJPYCAD")
                myrConversion = getExchangeRate(rates, "FXMYRCAD")
                mxnConversion = getExchangeRate(rates, "FXMXNCAD")
                nzdConversion = getExchangeRate(rates, "FXNZDCAD")
                nokConversion = getExchangeRate(rates, "FXNOKCAD")
                penConversion = getExchangeRate(rates, "FXPENCAD")
                rubConversion = getExchangeRate(rates, "FXRUBCAD")
                sarConversion = getExchangeRate(rates, "FXSARCAD")
                sgdConversion = getExchangeRate(rates, "FXSGDCAD")
                zarConversion = getExchangeRate(rates, "FXZARCAD")
                krwConversion = getExchangeRate(rates, "FXKRWCAD")
                sekConversion = getExchangeRate(rates, "FXSEKCAD")
                chfConversion = getExchangeRate(rates, "FXCHFCAD")
                twdConversion = getExchangeRate(rates, "FXTWDCAD")
                thbConversion = getExchangeRate(rates, "FXTHBCAD")
                tryConversion = getExchangeRate(rates, "FXTRYCAD")
                gbpConversion = getExchangeRate(rates, "FXGBPCAD")
                usdConversion = getExchangeRate(rates, "FXUSDCAD")
                vndConversion = getExchangeRate(rates, "FXVNDCAD")

                // Save today as the last time the API was called, so that we
                // don'y call the API more than once a day
                val sharedPref  = this@MainActivity.getPreferences(Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putLong(getString(R.string.saved_api_call_key), LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    apply()
                }

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

    fun getExchangeRate(jsonObj: JSONObject, key: String): Double {
        return jsonObj.getJSONObject(key).getDouble("v")
    }
}
