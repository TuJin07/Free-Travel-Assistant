package com.cf.tourism

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(tb)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = intent.getStringExtra("name")
        }

        tb.setNavigationOnClickListener {
            finishAndRemoveTask()
        }

        intent.apply {
            tv1.text = "行程名称：" + getStringExtra("name")
            tv2.text = "目的地：" + getStringExtra("destination")
            tv3.text = "交通方式：" + getStringExtra("destination")
            tv4.text = "时间：" + getStringExtra("time")
        }
    }
}