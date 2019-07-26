package com.alexeykatsuro.recyclerpopupwindowsample

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.alexeykatsuro.recyclerpopupwindow.RecyclerPopupWindow
import kotlinx.android.synthetic.main.activity_scroll_view.*

class ScrollViewActivity : AppCompatActivity() {

    private val fullList =  MutableList(200) {
        "Item $it"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_view)

        linear_layout.children
            .filter { it is EditText }
            .map { it as EditText }
            .forEach { editText ->
                editText.isFocusable = false
                editText.isCursorVisible = false
                editText.isFocusableInTouchMode = false
                editText.setOnClickListener {
                    RecyclerPopupWindow(this, it, getRandomList()) { index: Int, item: String ->
                        editText.setText(item)
                    }
                }
            }
    }

    /**
     * For checking [RecyclerPopupWindow]'s behavior with different size content
     *
     * @return list of string with random size.
     */
    private fun getRandomList() = fullList.subList(0,(0 until fullList.size).random())

}
