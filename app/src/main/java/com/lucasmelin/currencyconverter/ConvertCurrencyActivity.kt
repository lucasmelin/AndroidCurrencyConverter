package com.lucasmelin.currencyconverter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import java.text.DecimalFormat

class ConvertCurrencyActivity : AppCompatActivity() {
    private var conversion: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_currency)

        // Get the Intent that started this activity and extract the conversion value
        val intent = intent
        // Default exchange rate if not found is 1
        conversion = intent.getDoubleExtra(MainActivity.EXTRA_CONVERSION, 1.0)
    }

    /**
     * Called when the user taps the EXCHANGE button
     */
    fun convertCurrency(view: View) {
        val cdnDollars: Double
        val localAmount: Double
        // Regex to validate the input, even though the EditText is set
        // to entering only numbers
        val validDouble = "^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$"
        // Format to use when outputting converted currency
        val currencyFormat = DecimalFormat("00.00")


        val cdnAmountText = findViewById<EditText>(R.id.cdn_dollars)
        val localCurrencyText = findViewById<EditText>(R.id.local_currency_amount)

        // Get the amounts that have been entered in the EditText boxes
        val cdnTextValue = cdnAmountText.text.toString()
        val localTextValue = localCurrencyText.text.toString()

        // If a valid number was entered in the CDN EditText field, convert to
        // LocalCurrency
        if ("" != cdnTextValue && cdnTextValue.matches(validDouble.toRegex())) {
            // Convert from CDN to Local Currency
            cdnDollars = java.lang.Double.parseDouble(cdnTextValue)
            localAmount = cdnDollars / conversion!!
            // Format the double to a string with two decimal places before outputting
            localCurrencyText.setText(currencyFormat.format(localAmount))
        } else if ("" != localTextValue && localTextValue.matches(validDouble.toRegex())) {
            // Convert from Local Currency to CDN
            localAmount = java.lang.Double.parseDouble(localTextValue)
            cdnDollars = localAmount * conversion!!
            // Format the double to a string with two decimal places before outputting
            cdnAmountText.setText(currencyFormat.format(cdnDollars))
        }


    }

    /**
     * Called when the user taps the Clear Amounts button
     */
    fun clearCurrencyFields(view: View) {
        // Get both EditText fields
        val cdnAmountText = findViewById<EditText>(R.id.cdn_dollars)
        val localCurrencyText = findViewById<EditText>(R.id.local_currency_amount)
        // Clear the EditText fields
        cdnAmountText.text.clear()
        localCurrencyText.text.clear()
    }


}
