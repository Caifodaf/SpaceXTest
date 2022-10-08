package ru.android.spacextest.utils

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// отдельный отступ сбоку для первого элемента RecyclerView
class SpaceItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        // вычисление пикселей по DP. Здесь отступ будет *8dp*
        val margin = 16
        val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            margin.toFloat(),
            view.resources.displayMetrics
        )
            .toInt()
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = space
        }
    }
}