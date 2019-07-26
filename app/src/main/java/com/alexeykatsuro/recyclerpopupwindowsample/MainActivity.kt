package com.alexeykatsuro.recyclerpopupwindowsample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexeykatsuro.recyclerpopupwindow.RecyclerPopupWindow
import kotlinx.android.synthetic.main.activity_scroll_view.*

class MainActivity : AppCompatActivity() {

    val list = MutableList(3) {
        "Item $it"
    }

    val list1 = MutableList(10) {
        "Item $it"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edit_text2.setOnClickListener {
            RecyclerPopupWindow(this, it, list1) { index: Int, item: String ->
                Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
            }
        }

        edit_text.setOnClickListener {
            RecyclerPopupWindow(this, it, list) { index: Int, item: String ->
                Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
            }
        }
        edit_text1.setOnClickListener {
            RecyclerPopupWindow(this, it, list1) { index: Int, item: String ->
                Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_scroll_view_activity -> {
                startActivity(Intent(this, ScrollViewActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}