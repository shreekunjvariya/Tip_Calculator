package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

/**
 * Program to Calculate the tip from Cost of Service
 */
class MainActivity : AppCompatActivity() {
    //Used for binding all views in one variable BINDING
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //assigning the view to binding variable
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Button Listener for Calculate
        binding.calculateButton.setOnClickListener { calculateTip() }

        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode) }

    }

    //Function for calculating the tip entered
    private fun calculateTip() {

        //taking text from user in variable and converting to string
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        //converting string to double or null and assign to cost variable
        val cost = stringInTextField.toDoubleOrNull()
        //check entered value is null or string
        if (cost == null) {
            //if null tipResult will be null
            binding.tipResult.text = ""
            return
        }
        //check the tip percentage option selected by user
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.radio_avg -> 0.20
            R.id.radio_good -> 0.15
            else -> 0.10
        }
        //calculating the tip
        var tip = tipPercentage * cost
        //Rounding up the tip from double value to Decimal
        if (binding.roundupSwitch.isChecked) {
            //used ceil function for rounding tip
            tip = ceil(tip)
        }

        //NumberFormat is used to get the correspondence format of currency as per mobile
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        //showing the final Tip Amount to User
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}