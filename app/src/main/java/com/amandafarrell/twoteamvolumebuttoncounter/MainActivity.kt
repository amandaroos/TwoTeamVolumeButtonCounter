package com.amandafarrell.twoteamvolumebuttoncounter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var number = 0
    var starting_number = 0

    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set variable values from Shared Preferences
        sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key),Context.MODE_PRIVATE) ?: return
        getNumber()
        getStartingNumber()

        team1_score.setText(number.toString())
    }

    fun getNumber() {
        number = sharedPreferences!!.getString(
            getString(R.string.settings_number_key),
            getString(R.string.settings_number_default)
        )!!.toInt()
    }

    fun getStartingNumber() {
        starting_number = sharedPreferences!!.getString(
            getString(R.string.settings_starting_number_key),
            getString(R.string.settings_starting_number_default)
        )!!.toInt()
    }

    fun reset(view: View?) {
        getStartingNumber()
        team1_score!!.text = starting_number.toString()
        number = starting_number
        //Save starting number as number in SharedPreferences
        sharedPreferences!!.edit()
            .putString(getString(R.string.settings_number_key), number.toString())
            .apply()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            number += 1
            team1_score.setText(number.toString())
            //return true so that it doesn't trigger normal volume button functionality
            return true
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            number -= 1
            team1_score.setText(number.toString())
            //return true so that it doesn't trigger normal volume button functionality
            return true
        }
        //Save number to SharedPreferences
        sharedPreferences!!.edit()
            .putString(getString(R.string.settings_number_key), number.toString())
            .apply()
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_donate -> {
                val intent = Intent(this@MainActivity, DonationActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_edit_starting_number -> {
                val intent2 =
                    Intent(this@MainActivity, EditStartingNumberActivity::class.java)
                startActivity(intent2)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        sharedPreferences!!.edit()
            .putString(getString(R.string.settings_number_key), number.toString())
            .commit()
        super.onPause()
    }

    override fun onResume() {
        getNumber()
        team1_score.setText(number.toString())
        super.onResume()
    }
}
