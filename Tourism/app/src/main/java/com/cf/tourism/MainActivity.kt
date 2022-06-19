package com.cf.tourism

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.f1.*
import kotlinx.android.synthetic.main.f3.*

class MainActivity : AppCompatActivity() {

    lateinit var choosePhotoLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        choosePhotoLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            dataViewModel(this).uriLiveData.value = it
        }

        val title = arrayOf(
            "首页",
            "新建行程",
            "我的"
        )

        TabLayoutMediator(tablayout, vp2.apply {
            adapter = Vp2Adpater(this@MainActivity)
            offscreenPageLimit = title.size
        }, false) { tab, position ->
            tab.text = title[position]
        }.attach()
    }

    inner class Vp2Adpater(fa: FragmentActivity): FragmentStateAdapter(fa) {

        private val data = mutableListOf(
            HomeFragment(),
            NewFragment(),
            MyFragment()
        )

        override fun getItemCount(): Int = data.size

        override fun createFragment(position: Int): Fragment = data[position]
    }
}