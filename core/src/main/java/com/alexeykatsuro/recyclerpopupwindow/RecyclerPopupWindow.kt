package com.alexeykatsuro.recyclerpopupwindow

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.PopupWindow
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.widget.PopupWindowCompat
import androidx.recyclerview.widget.RecyclerView

typealias onItemSelected<T> = (index: Int, item: T) -> Unit

class RecyclerPopupWindow<T : Any>(
    context: Context,
    private val anchor: View,
    items: List<T>,
    mapToString: T.() -> String = { toString() },
    onSelected: onItemSelected<T>
) {


    private val popupWindow: PopupWindow
    private val adapter: RecyclerView.Adapter<TextPopupViewHolder<T>>
    private val attrs: AttributeSet? = null
    @AttrRes
    private val defStyleAttr: Int = R.attr.listPopupWindowStyle
    @StyleRes
    private val defStyleRes: Int = 0

    private var height: Int = WindowManager.LayoutParams.WRAP_CONTENT
    private var width: Int = anchor.width
    /**
     * vertical padding between [anchor] and [popupWindow]
     */
    private var yOffSetIn = context.resources.getDimensionPixelOffset(R.dimen.default_in_y_offset)

    /**
     * vertical padding between [popupWindow] and bottom or top(if popupWindow located above [anchor]) of screen
     */
    private var yOffsetOut: Int = context.resources.getDimensionPixelOffset(R.dimen.default_out_y_offset)
        set(value) {
            field = value + yOffSetIn
        }

    private val maxHeightPopup: Int
        get() = popupWindow.getMaxAvailableHeight(anchor) - yOffsetOut

    init {
        popupWindow = PopupWindow(context, attrs, defStyleAttr, defStyleRes)
        popupWindow.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED

        subscribeOnAnchorLayoutChanges()

        val onClick = { index: Int, item: T ->
            onSelected(index, item)
            popupWindow.dismiss()
        }
        adapter = TextPopupAdapter(items, mapToString, onClick)
        val recyclerView = createRecycleView(context)
        val lm = ObservableLinearLinearLayoutManager(context)
        subscribeOnLayoutManagerLayoutChanges(lm)
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter


        popupWindow.contentView = recyclerView
        PopupWindowCompat.setOverlapAnchor(popupWindow, false)
        popupWindow.height = height
        popupWindow.width = width
        popupWindow.isFocusable = true
        popupWindow.isClippingEnabled = false

        popupWindow.showAsDropDown(anchor, 0, yOffSetIn, Gravity.START)
    }

    private fun subscribeOnAnchorLayoutChanges() {
        val listener = View.OnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            popupWindow.update()
        }
        anchor.addOnLayoutChangeListener(listener)
        popupWindow.setOnDismissListener {
            anchor.removeOnLayoutChangeListener(listener)
        }
    }

    private fun subscribeOnLayoutManagerLayoutChanges(lm: ObservableLinearLinearLayoutManager) {
        val listener: OnLayoutCompletedObserver = { width, height ->
            popupWindow.update(anchor,0, yOffSetIn,this.width,resolveHeight(height))
        }
        lm.addOnLayoutCompletedListener(listener)
        popupWindow.setOnDismissListener {
            lm.removeOnLayoutCompletedListener(listener)
        }
    }

    private fun resolveHeight(recyclerViewHeight: Int): Int = Math.min(recyclerViewHeight,maxHeightPopup)


    private fun createRecycleView(context: Context): RecyclerView {
        val root: ViewGroup = anchor.rootView as ViewGroup
        return LayoutInflater.from(context)
            .inflate(R.layout.rpw_dropdown_list, root, false) as RecyclerView
    }
}
