package com.amandafarrell.twoteamvolumebuttoncounter

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit_starting_number_activity.*

class EditStartingNumberActivity : AppCompatActivity() {
    var team1StartingNumber = 0
    var team2StartingNumber = 0

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_starting_number_activity)

        //Set variable values from Shared Preferences
        sharedPreferences =
            this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return
        team1StartingNumber = sharedPreferences!!.getString(
            getString(R.string.team_one_starting_score_key),
            getString(R.string.settings_starting_score_default)
        )!!.toInt()
        team2StartingNumber = sharedPreferences!!.getString(
            getString(R.string.team_two_score_key),
            getString(R.string.settings_starting_score_default)
        )!!.toInt()


        edit_team1_starting_number.setText(team1StartingNumber.toString())
        edit_team2_starting_number.setText(team2StartingNumber.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save_starting_number -> {
                save()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun saveOnClick(view: View){
        save()
        finish()
    }

    fun save() { //Check the input
        try {
            team1StartingNumber = Integer.valueOf(edit_team1_starting_number!!.text.toString())
        } catch (e: Exception) {
            Toast.makeText(this, R.string.large_number_error_toast, Toast.LENGTH_LONG).show()
            edit_team1_starting_number!!.text.clear()
        }

        try {
            team2StartingNumber = Integer.valueOf(edit_team2_starting_number!!.text.toString())
        } catch (e: Exception) {
            Toast.makeText(this, R.string.large_number_error_toast, Toast.LENGTH_LONG).show()
            edit_team2_starting_number!!.text.clear()
        }

        sharedPreferences!!.edit()
            .putString(
                getString(R.string.team_one_score_key),
                team1StartingNumber.toString()
            )
            .putString(
                getString(R.string.team_two_score_key),
                team2StartingNumber.toString()
            )
            .putString(
                getString(R.string.team_one_starting_score_key),
                team1StartingNumber.toString()
            )
            .putString(
                getString(R.string.team_two_starting_score_key),
                team2StartingNumber.toString()
            )
            .apply()
    }
}