package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private var currentPlayer = "X"
    private var moves = 0
    private lateinit var buttons: Array<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeButtons()
    }

    private fun initializeButtons() {
        buttons = Array(9) { index ->
            findViewById<Button>(resources.getIdentifier("b${index + 1}", "id", packageName)).apply {
                setOnClickListener { onButtonClick(this) }
            }
        }
    }

    private fun onButtonClick(button: Button) {
        if (button.text.isEmpty()) {
            button.text = currentPlayer
            moves++
            button.setBackgroundColor(getPlayerColor(currentPlayer))
            if (checkWinner()) {
                announceWinner("$currentPlayer wins!")
            } else if (moves == 9) {
                announceDraw()
            } else {
                currentPlayer = if (currentPlayer == "X") "O" else "X"
            }
        }
        var Textview = findViewById<TextView>(R.id.turn)
        Textview.text = "$currentPlayer'S TURN"
    }

    private fun getPlayerColor(player: String): Int {
        return if (player == "X") {
            ContextCompat.getColor(this, R.color.colorPlayerX)
        } else {
            ContextCompat.getColor(this, R.color.colorPlayerO)
        }
    }


    private fun checkWinner(): Boolean {
        val winCombinations = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5),
            intArrayOf(6, 7, 8), intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
            intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
        )

        for (combination in winCombinations) {
            val (a, b, c) = combination
            if (buttons[a].text.isNotEmpty() && buttons[a].text == buttons[b].text && buttons[b].text == buttons[c].text) {
                return true
            }
        }
        return false
    }

    private fun announceWinner(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        buttons.forEach { it.isEnabled = false }
        disableAllButtons()
    }

    private fun announceDraw() {
        Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show()
        buttons.forEach { it.isEnabled = false }
        disableAllButtons()
    }

    private fun disableAllButtons() {
        buttons.forEach { it.isEnabled = false
            it.setOnClickListener {
                Toast.makeText(this, "Game is over!", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun resetGame(view: View) {
        buttons.forEach {
            it.text = ""
            it.isEnabled = true
        }
        currentPlayer = "X"
        moves = 0
    }
}
