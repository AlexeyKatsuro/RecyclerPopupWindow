package com.alexeykatsuro.recyclerpopupwindowsample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexeykatsuro.recyclerpopupwindow.DropdownPopup
import com.alexeykatsuro.recyclerpopupwindow.RecyclerPopupWindow
import kotlinx.android.synthetic.main.activity_main.*

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

        edittext.setOnClickListener {
            RecyclerPopupWindow(this, edittext, list1) { index: Int, item: String ->
                edittext.setText(item)
            }
        }

        text_input.setEndIconOnClickListener {
            DropdownPopup(this, text_input, list) { index: Int, item: String ->
                text_input.editText!!.setText(item)
            }
        }

        text_input1.setEndIconOnClickListener {
            RecyclerPopupWindow(this, text_input1, list1) { index: Int, item: String ->
                text_input1.editText!!.setText(item)
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