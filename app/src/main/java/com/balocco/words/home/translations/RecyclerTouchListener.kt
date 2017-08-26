package com.balocco.words.home.translations

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent

private const val INVALID_POSITION = -1

class RecyclerTouchListener(
        private val itemTouchListener: ItemTouchListener
) : RecyclerView.OnItemTouchListener {

    private var rect: Rect? = null
    private var lastNotifiedPosition: Int = -1

    override fun onInterceptTouchEvent(recyclerView: RecyclerView, event: MotionEvent): Boolean {
        val child = recyclerView.findChildViewUnder(event.x, event.y)
        val action = event.action

        if (action == MotionEvent.ACTION_DOWN) {
            rect = Rect(
                    recyclerView.left,
                    recyclerView.top,
                    recyclerView.right,
                    recyclerView.bottom)
        }

        if (action == MotionEvent.ACTION_MOVE && rect != null) {
            if (!rect!!.contains(
                    (recyclerView.left + event.x).toInt(),
                    (recyclerView.top + event.y).toInt())) {
                notifyListener(INVALID_POSITION, MotionEvent.ACTION_OUTSIDE)
                lastNotifiedPosition = INVALID_POSITION
                return false
            }
        }

        val position = recyclerView.getChildAdapterPosition(child)
        if (child != null && position != lastNotifiedPosition) {
            lastNotifiedPosition = position
            notifyListener(lastNotifiedPosition, event.action)
        }

        if (action == MotionEvent.ACTION_UP) {
            lastNotifiedPosition = INVALID_POSITION
            notifyListener(lastNotifiedPosition, event.action)
        }

        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        // Don't do anything
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // Don't do anything
    }

    private fun notifyListener(position: Int, eventAction: Int) {
        itemTouchListener.onItemTouched(position, eventAction)
    }

    interface ItemTouchListener {
        fun onItemTouched(position: Int, eventAction: Int)
    }
}