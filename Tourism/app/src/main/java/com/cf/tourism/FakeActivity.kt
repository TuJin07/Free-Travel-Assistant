package com.cf.tourism

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_fake.*

class FakeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake)

        setSupportActionBar(toolbar)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = intent.getStringExtra("title")
        }

        toolbar.apply {
            setNavigationOnClickListener {
                finishAndRemoveTask()
            }
        }
    }
}