package com.amandafarrell.twoteamvolumebuttoncounter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_starting_number_activity.*

class EditStartingNumberActivity : AppCompatActivity() {
    var starting_number = 0
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_starting_number_activity)

        //Set variable values from Shared Preferences
        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE) ?: return
        starting_number = sharedPreferences!!.getString(
            getString(R.string.settings_starting_number_key),
            getString(R.string.settings_starting_number_default)
        )!!.toInt()
        edit_text_starting_number.setText(starting_number.toString())
        //Save and finish EditStartingNumber activity when "done" is pressed on keyboard
        edit_text_starting_number.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveStartingNumber()
                setStartingNumberAsNumber()
                finish()
                handled = true
            }
            handled
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save_starting_number -> {
                saveStartingNumber()
                setStartingNumberAsNumber()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveStartingNumber() { //Check the input
        try {
            starting_number = Integer.valueOf(edit_text_starting_number!!.text.toString())
        } catch (e: Exception) {
            Toast.makeText(this, R.string.large_number_error_toast, Toast.LENGTH_LONG).show()
            edit_text_starting_number!!.text.clear()
        }
        //Save new starting number to SharedPreferences
        sharedPreferences!!.edit()
            .putString(getString(R.string.settings_starting_number_key), starting_number.toString())
            .apply()
    }

    fun setStartingNumberAsNumber() {
        sharedPreferences!!.edit()
            .putString(getString(R.string.settings_number_key), starting_number.toString())
            .apply()
    }
}