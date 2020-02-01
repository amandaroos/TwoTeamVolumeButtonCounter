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

    var team1Score = 0
    var team1StartingScore = 0

    var team2Score = 0
    var team2StartingScore = 0

    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set variable values from Shared Preferences
        sharedPreferences =
            this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                ?: return
        getStartingScores()
        getScores()
        setScores()
    }

    fun getScores() {
        team1Score = sharedPreferences!!.getString(
            getString(R.string.team_one_score_key),
            getString(R.string.settings_score_default)
        )!!.toInt()

        team2Score = sharedPreferences!!.getString(
            getString(R.string.team_two_score_key),
            getString(R.string.settings_score_default)
        )!!.toInt()
    }

    fun getStartingScores() {
        team1StartingScore = sharedPreferences!!.getString(
            getString(R.string.team_one_starting_score_key),
            getString(R.string.settings_starting_score_default)
        )!!.toInt()

        team2StartingScore = sharedPreferences!!.getString(
            getString(R.string.team_two_starting_score_key),
            getString(R.string.settings_starting_score_default)
        )!!.toInt()
    }

    fun setScores() {
        team1_score.setText(team1Score.toString())
        team2_score.setText(team2Score.toString())
    }

    fun saveScores() {
        sharedPreferences!!.edit()
            .putString(getString(R.string.team_one_score_key), team1Score.toString())
            .putString(getString(R.string.team_two_score_key), team2Score.toString())
            .apply()
    }

    fun reset(view: View?) {
        getStartingScores()
        team1Score = team1StartingScore
        team2Score = team2StartingScore
        setScores()
        saveScores()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            team1Score += 1
            setScores()
            saveScores()
            //return true so that it doesn't trigger normal volume button functionality
            return true
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            team2Score += 1
            setScores()
            saveScores()
            //return true so that it doesn't trigger normal volume button functionality
            return true
        }
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

//    override fun onPause() {
//        sharedPreferences!!.edit()
//            .putString(getString(R.string.team_one_score_key), team1Score.toString())
//            .commit()
//        super.onPause()
//    }

    override fun onResume() {
        getScores()
        setScores()
        super.onResume()
    }
}
