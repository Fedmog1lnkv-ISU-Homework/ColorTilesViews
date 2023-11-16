package com.example.colortilesviewsk

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.colortilesviewsk.databinding.ActivityMainBinding
import kotlin.random.Random

data class Coord(val x: Int, val y: Int)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tiles: Array<Array<View>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tiles = Array(4) { x ->
            Array(4) { y ->
                binding.root.findViewWithTag<View>("$x$y")
            }
        }

        for (row in tiles) {
            for (view in row) {
                val randomColor = if (Random.nextBoolean()) R.color.bright
                else R.color.dark
                view.setBackgroundColor(resources.getColor(randomColor))
            }
        }
    }

    fun getCoordFromString(s: String): Coord {
        val x = s[0].toString().toInt()
        val y = s[1].toString().toInt()
        return Coord(x, y)
    }

    fun changeColor(view: View) {
        val brightColor = ContextCompat.getColor(this, R.color.bright)
        val darkColor = ContextCompat.getColor(this, R.color.dark)

        val drawable = view.background as ColorDrawable
        if (drawable.color == brightColor) {
            view.setBackgroundColor(darkColor)
        } else {
            view.setBackgroundColor(brightColor)
        }
    }

    fun onClick(v: View) {
        val coord = getCoordFromString(v.tag.toString())
        changeColor(v)

        for (i in 0..3) {
            changeColor(tiles[coord.x][i])
            changeColor(tiles[i][coord.y])
        }

        if (checkVictory()) {
            showToast("Победа!")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun checkVictory(): Boolean {
        val firstColor = (tiles[0][0].background as ColorDrawable).color
        for (row in tiles) {
            for (view in row) {
                val currentColor = (view.background as ColorDrawable).color
                if (currentColor != firstColor) {
                    return false
                }
            }
        }
        return true
    }

}
