package com.study.recyclerviewdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.recyclerviewdemo.adapter.RvAdapter
import com.study.recyclerviewdemo.bean.DataSource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var datas = arrayListOf<DataSource>()
        for (i in 0 until 100) {
            if (i < 20)
                datas.add(DataSource("萧峰$i", "天龙八部"))
            else if (i < 40)
                datas.add(DataSource("杨过$i", "神雕侠侣"))
            else if (i < 60)
                datas.add(DataSource("秦王$i", "大唐王朝"))
            else if (i < 80)
                datas.add(DataSource("刘邦$i", "大汉王朝"))
            else
                datas.add(DataSource("胡斐$i", "雪山飞狐"))
        }
        rv.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rv.addItemDecoration(AttachItemDecoration(applicationContext))
        rv.adapter = RvAdapter(applicationContext, datas)
    }
}
