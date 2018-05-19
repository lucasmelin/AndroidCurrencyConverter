package com.lucasmelin.currencyconverter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
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
    private var audConversion: Double = 0.9670
    private var brlConversion: Double = 0.3443
    private var cnyConversion: Double = 0.2019
    private var eurConversion: Double = 1.5163
    private var hkdConversion: Double = 0.164082
    private var inrConversion: Double = 0.01894
    private var idrConversion: Double = 0.000091
    private var jpyConversion: Double = 0.01163
    private var myrConversion: Double = 0.3243
    private var mxnConversion: Double = 0.06468
    private var nzdConversion: Double = 0.8902
    private var nokConversion: Double = 0.1587
    private var penConversion: Double = 0.3920
    private var rubConversion: Double = 0.02066
    private var sarConversion: Double = 0.3435
    private var sgdConversion: Double = 0.9587
    private var zarConversion: Double = 0.10106
    private var krwConversion: Double = 0.001190
    private var sekConversion: Double = 0.1472
    private var chfConversion: Double = 1.2904
    private var twdConversion: Double = 0.04300
    private var thbConversion: Double = 0.04000
    private var tryConversion: Double = 0.2869
    private var gbpConversion: Double = 1.735
    private var usdConversion: Double = 1.2880
    private var vndConversion: Double = 0.000057
    var conversion: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Call the API to retrieve the current conversion rates
        val sharedPref = this@MainActivity.getPreferences(Context.MODE_PRIVATE) ?: return
        val lastAPICall = sharedPref.getLong(getString(R.string.saved_api_call_key), Long.MIN_VALUE)
        val lastTimestamp = Instant.ofEpochMilli(lastAPICall).atZone(ZoneId.systemDefault()).toLocalDate()
        val daysBetween = ChronoUnit.DAYS.between(lastTimestamp, LocalDate.now())

        if (daysBetween > 1) {
            getConversionValuesFromAPI()
        } else {
            loadConversionFromSharedPreferences()
        }

        val currencySpinner = findViewById(R.id.currency_spinner) as Spinner
        currencySpinner.setSelection(0, false)
        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                val selected = parent.getItemAtPosition(pos).toString()
                when (selected) {
                    "Australian dollar" -> conversion = audConversion
                    "Brazilian real" -> conversion = brlConversion
                    "Chinese renminbi" -> conversion = cnyConversion
                    "European euro" -> conversion = eurConversion
                    "Hong Kong dollar" -> conversion = hkdConversion
                    "Indian rupee" -> conversion = idrConversion
                    "Indonesian rupiah" -> conversion = idrConversion
                    "Japanese yen" -> conversion = jpyConversion
                    "Malaysian ringgit" -> conversion = myrConversion
                    "Mexican peso" -> conversion = mxnConversion
                    "New Zealand dollar" -> conversion = nzdConversion
                    "Norwegian kron" -> conversion = nokConversion
                    "Peruvian new sol" -> conversion = penConversion
                    "Russian ruble" -> conversion = rubConversion
                    "Saudi riyal" -> conversion = sarConversion
                    "Singapore dollar" -> conversion = sgdConversion
                    "South African rand" -> conversion = sarConversion
                    "South Korean won" -> conversion = krwConversion
                    "Swedish krona" -> conversion = sekConversion
                    "Swiss franc" -> conversion = chfConversion
                    "Taiwanese dollar" -> conversion = twdConversion
                    "Thai baht" -> conversion = thbConversion
                    "Turkish lira" -> conversion = tryConversion
                    "UK pound sterling" -> conversion = gbpConversion
                    "US dollar" -> conversion = usdConversion
                    "Vietnamese dong" -> conversion = vndConversion
                }
                val intent = Intent(parent.context, ConvertCurrencyActivity::class.java)
                intent.putExtra(EXTRA_CONVERSION, conversion)
                startActivity(intent)
            }
        }


    }


    private fun getConversionValuesFromAPI() {
        val url = "https://www.bankofcanada.ca/valet/observations/group/FX_RATES_DAILY/json?recent=1"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            try {
                val rates = response.getJSONArray("observations").getJSONObject(0)
                val sharedPref = this@MainActivity.getPreferences(Context.MODE_PRIVATE)

                audConversion = getExchangeRate(rates, "FXAUDCAD")
                brlConversion = getExchangeRate(rates, "FXBRLCAD")
                cnyConversion = getExchangeRate(rates, "FXCNYCAD")
                eurConversion = getExchangeRate(rates, "FXEURCAD")
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
                // don't call the API more than once a day
                with(sharedPref.edit()) {
                    putLong(getString(R.string.saved_api_call_key), LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())

                    putDouble(getString(R.string.aud_conversion), audConversion)
                    putDouble(getString(R.string.brl_conversion), brlConversion)
                    putDouble(getString(R.string.cny_conversion), cnyConversion)
                    putDouble(getString(R.string.eur_conversion), eurConversion)
                    putDouble(getString(R.string.hkd_conversion), hkdConversion)
                    putDouble(getString(R.string.inr_conversion), inrConversion)
                    putDouble(getString(R.string.idr_conversion), idrConversion)
                    putDouble(getString(R.string.jpy_conversion), jpyConversion)
                    putDouble(getString(R.string.myr_conversion), myrConversion)
                    putDouble(getString(R.string.mxn_conversion), mxnConversion)
                    putDouble(getString(R.string.nzd_conversion), nzdConversion)
                    putDouble(getString(R.string.nok_conversion), nokConversion)
                    putDouble(getString(R.string.pen_conversion), penConversion)
                    putDouble(getString(R.string.rub_conversion), rubConversion)
                    putDouble(getString(R.string.sar_conversion), sarConversion)
                    putDouble(getString(R.string.sgd_conversion), sgdConversion)
                    putDouble(getString(R.string.zar_conversion), zarConversion)
                    putDouble(getString(R.string.krw_conversion), krwConversion)
                    putDouble(getString(R.string.sek_conversion), sekConversion)
                    putDouble(getString(R.string.chf_conversion), chfConversion)
                    putDouble(getString(R.string.twd_conversion), twdConversion)
                    putDouble(getString(R.string.thb_conversion), thbConversion)
                    putDouble(getString(R.string.try_conversion), tryConversion)
                    putDouble(getString(R.string.gbp_conversion), gbpConversion)
                    putDouble(getString(R.string.usd_conversion), usdConversion)
                    putDouble(getString(R.string.vnd_conversion), vndConversion)

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

    private fun getExchangeRate(jsonObj: JSONObject, key: String): Double {
        return jsonObj.getJSONObject(key).getDouble("v")
    }


    private fun loadConversionFromSharedPreferences() {
        val sharedPref = this@MainActivity.getPreferences(Context.MODE_PRIVATE)

        audConversion = sharedPref.getDouble(getString(R.string.aud_conversion), 1.0)
        brlConversion = sharedPref.getDouble(getString(R.string.brl_conversion), 1.0)
        cnyConversion = sharedPref.getDouble(getString(R.string.cny_conversion), 1.0)
        eurConversion = sharedPref.getDouble(getString(R.string.eur_conversion), 1.0)
        hkdConversion = sharedPref.getDouble(getString(R.string.hkd_conversion), 1.0)
        inrConversion = sharedPref.getDouble(getString(R.string.inr_conversion), 1.0)
        idrConversion = sharedPref.getDouble(getString(R.string.idr_conversion), 1.0)
        jpyConversion = sharedPref.getDouble(getString(R.string.jpy_conversion), 1.0)
        myrConversion = sharedPref.getDouble(getString(R.string.myr_conversion), 1.0)
        mxnConversion = sharedPref.getDouble(getString(R.string.mxn_conversion), 1.0)
        nzdConversion = sharedPref.getDouble(getString(R.string.nzd_conversion), 1.0)
        nokConversion = sharedPref.getDouble(getString(R.string.nok_conversion), 1.0)
        penConversion = sharedPref.getDouble(getString(R.string.pen_conversion), 1.0)
        rubConversion = sharedPref.getDouble(getString(R.string.rub_conversion), 1.0)
        sarConversion = sharedPref.getDouble(getString(R.string.sar_conversion), 1.0)
        sgdConversion = sharedPref.getDouble(getString(R.string.sgd_conversion), 1.0)
        zarConversion = sharedPref.getDouble(getString(R.string.zar_conversion), 1.0)
        krwConversion = sharedPref.getDouble(getString(R.string.krw_conversion), 1.0)
        sekConversion = sharedPref.getDouble(getString(R.string.sek_conversion), 1.0)
        chfConversion = sharedPref.getDouble(getString(R.string.chf_conversion), 1.0)
        twdConversion = sharedPref.getDouble(getString(R.string.twd_conversion), 1.0)
        thbConversion = sharedPref.getDouble(getString(R.string.thb_conversion), 1.0)
        tryConversion = sharedPref.getDouble(getString(R.string.try_conversion), 1.0)
        gbpConversion = sharedPref.getDouble(getString(R.string.gbp_conversion), 1.0)
        usdConversion = sharedPref.getDouble(getString(R.string.usd_conversion), 1.0)
        vndConversion = sharedPref.getDouble(getString(R.string.vnd_conversion), 1.0)
    }

    fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
            putLong(key, java.lang.Double.doubleToRawLongBits(double))

    fun SharedPreferences.getDouble(key: String, default: Double) =
            java.lang.Double.longBitsToDouble(getLong(key, java.lang.Double.doubleToRawLongBits(default)))


}
