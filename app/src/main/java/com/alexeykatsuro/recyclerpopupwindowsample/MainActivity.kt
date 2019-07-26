package com.alexeykatsuro.recyclerpopupwindowsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

}