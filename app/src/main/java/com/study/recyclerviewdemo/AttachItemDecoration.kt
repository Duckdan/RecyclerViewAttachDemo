package com.study.recyclerviewdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.recyclerviewdemo.adapter.RvAdapter

class AttachItemDecoration : RecyclerView.ItemDecoration {
    val TAG = "AttachItemDecoration"

    var groupHeight: Int
    var groupPaint = Paint()
    var textRect = Rect()

    constructor(applicationContext: Context) {
        groupHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            60f,
            applicationContext.resources.displayMetrics
        ).toInt()
        groupPaint.color = Color.BLACK
        groupPaint.textSize = 60f
        groupPaint.isAntiAlias = true
    }

    /**
     * 最先调用
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //此处的findFirstVisibleItemPosition可能为-1
        val findFirstVisibleItemPosition =
            (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        val childLayoutPosition = parent.getChildLayoutPosition(view)
        Log.e(
            TAG,
            "$childAdapterPosition===getItemOffsets===$findFirstVisibleItemPosition===$childLayoutPosition"
        )

        val rvAdapter = parent.adapter as RvAdapter
        if (childAdapterPosition == 0) {
            outRect.set(0, groupHeight, 0, 0)
        } else {
            if (rvAdapter.isGroup(childAdapterPosition)) {
                outRect.set(0, groupHeight, 0, 0)
            } else {
                outRect.set(0, 10, 0, 0)
            }
        }
    }

    /**
     * 次之
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
//        Log.e(TAG, "onDraw")
        if (parent.adapter is RvAdapter) {
            val rvAdapter = parent.adapter as RvAdapter
            val childCount = parent.childCount
            val left = parent.paddingLeft
            val top = parent.paddingTop
            val right = parent.width - parent.paddingRight
            for (i in 0 until childCount) {
                //获取指定位置的view
                val childView = parent.getChildAt(i)
                val childAdapterPosition = parent.getChildAdapterPosition(childView)
                val isGroup = rvAdapter.isGroup(childAdapterPosition)
                if (isGroup && childView.top.toFloat() - groupHeight - top >= 0) {
                    val groupName = rvAdapter.getGroupName(childAdapterPosition)
                    groupPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                    groupPaint.color = Color.BLUE
                    c.drawRect(
                        left.toFloat(),
                        childView.top.toFloat() - groupHeight,
                        right.toFloat(),
                        childView.top.toFloat(),
                        groupPaint
                    )
                    groupPaint.color = Color.RED
                    c.drawText(
                        groupName,
                        0,
                        groupName.length,
                        left.toFloat(),
                        (childView.top - groupHeight / 2 + textRect.height() / 2).toFloat(),
                        groupPaint
                    )
                }
            }
        }
    }


    /**
     * 最后调用，可以覆盖Item
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        Log.e(TAG, "onDrawOver")
        if (parent.adapter is RvAdapter) {
            val rvAdapter = parent.adapter as RvAdapter
            val childCount = parent.childCount
            val left = parent.paddingLeft
            val top = parent.paddingTop
            val right = parent.width - parent.paddingRight
            val findFirstVisibleItemPosition =
                (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val isGroup = rvAdapter.isGroup(findFirstVisibleItemPosition + 1)
            val groupName = rvAdapter.getGroupName(findFirstVisibleItemPosition)

            //拿到第一个可视条目
            parent.findViewHolderForAdapterPosition(findFirstVisibleItemPosition)?.apply {
                groupPaint.getTextBounds(groupName, 0, groupName.length, textRect)
                //判断第二个可是条目的组名是否生效
                if (isGroup) {
                    val bottom = groupHeight.coerceAtMost(itemView.bottom - top)
                    groupPaint.color = Color.YELLOW
                    c.drawRect(
                        left.toFloat(),
                        top.toFloat(),
                        right.toFloat(),
                        bottom.toFloat(),
                        groupPaint
                    )
                    groupPaint.color = Color.BLACK
                    c.drawText(
                        groupName,
                        0,
                        groupName.length,
                        left.toFloat(),
                        bottom.toFloat()  - groupHeight / 2 + textRect.height() / 2,
                        groupPaint
                    )
                } else {
                    groupPaint.color = Color.YELLOW
                    c.drawRect(
                        left.toFloat(),
                        top.toFloat(),
                        right.toFloat(),
                        top.toFloat() + groupHeight,
                        groupPaint
                    )
                    groupPaint.color = Color.BLACK
                    c.drawText(
                        groupName,
                        0,
                        groupName.length,
                        left.toFloat(),
                        top.toFloat() + groupHeight / 2 + textRect.height() / 2,
                        groupPaint
                    )
                }
            }
        }
    }
}

