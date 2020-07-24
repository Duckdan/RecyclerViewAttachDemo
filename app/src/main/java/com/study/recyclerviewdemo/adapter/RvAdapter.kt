package com.study.recyclerviewdemo.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.study.recyclerviewdemo.R
import com.study.recyclerviewdemo.bean.DataSource
import org.w3c.dom.Text

class RvAdapter(var context: Context, var datas: ArrayList<DataSource>) :
    RecyclerView.Adapter<RvAdapter.RvHolder>() {

    override fun getItemCount(): Int = datas?.size

    fun getGroupName(index: Int): String = datas?.get(index).groupName

    fun isGroup(index: Int): Boolean {
        return if (index == 0)
            true
        else {
            val currentGroupName = getGroupName(index)
            //上一个条目的组名
            val lastGroupName = getGroupName(index - 1)
            return lastGroupName != currentGroupName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvHolder {
        val itemView =
            LayoutInflater.from(context)
                .inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        itemView.setBackgroundColor(Color.GRAY)
        return RvHolder(itemView)
    }


    override fun onBindViewHolder(holder: RvHolder, position: Int) {
        datas?.apply {
            (holder.itemView as TextView).text = datas[position].content
        }
    }

    class RvHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}