package com.alexeykatsuro.recyclerpopupwindow

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

typealias OnLayoutCompletedObserver = (width: Int, height: Int) -> Unit

class LinearLM(context: Context) : LinearLayoutManager(context) {

    private val layoutCompletedObservers = mutableSetOf<OnLayoutCompletedObserver>()

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {

        val widthPixels = View.MeasureSpec.getSize(widthSpec)
        val heightPixels = View.MeasureSpec.getSize(heightSpec)
        Log.e("onMeasure LLM", "widthPixels $widthPixels , heightPixels $heightPixels")
        super.onMeasure(recycler, state, widthSpec, heightSpec)
    }

    override fun onLayoutCompleted(state: RecyclerView.State?) {
        super.onLayoutCompleted(state)
        Log.e("onLayoutCompleted LLM", "width $width , height $height")
        layoutCompletedObservers.forEach {
            it.invoke(width, height)
        }
    }

    fun addOnLayoutCompletedObserver(observer: OnLayoutCompletedObserver) {
        layoutCompletedObservers.add(observer)
    }

    fun removeOnLayoutCompletedObserver(observer: OnLayoutCompletedObserver) {
        layoutCompletedObservers.remove(observer)
    }
}