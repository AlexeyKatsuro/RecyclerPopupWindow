package com.alexeykatsuro.recyclerpopupwindow

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

typealias OnLayoutCompletedObserver = (width: Int, height: Int) -> Unit

class ObservableLinearLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    private val layoutCompletedListeners = mutableSetOf<OnLayoutCompletedObserver>()

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        layoutCompletedListeners.forEach {
            it.invoke(width, height)
        }
    }

    fun addOnLayoutCompletedListener(observer: OnLayoutCompletedObserver) {
        layoutCompletedListeners.add(observer)
    }

    fun removeOnLayoutCompletedListener(observer: OnLayoutCompletedObserver) {
        layoutCompletedListeners.remove(observer)
    }
}