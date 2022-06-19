package com.cf.tourism

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.f1.*

class HomeFragment : Fragment() {

    private val journeyAdapter = JourneyAdapter()

    private fun click(title: String): View.OnClickListener {
        return View.OnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    FakeActivity::class.java
                ).apply { putExtra("title", title) })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.f1, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        map.setOnClickListener(click("地图"))
        route.setOnClickListener(click("路线"))
        food.setOnClickListener(click("美食"))
        viewpoint.setOnClickListener(click("景点"))

        rcv.adapter = journeyAdapter

        Glide.with(this)
            .load("https://s3.bmp.ovh/imgs/2022/06/18/e2485cadf00619ba.png")
            .into(banner)

        banner.setOnClickListener {
            (requireActivity() as MainActivity).tablayout.getTabAt(1)?.select()
        }

        dataViewModel(requireActivity()).addChangeCallback {
            Log.d("ysw", "onViewCreated: ${it.size}")
            journeyAdapter.apply {
                data.apply {
                    clear()
                    addAll(it)
                    notifyDataSetChanged()
                }
            }
        }
    }

    open class JourneyAdapter : RecyclerView.Adapter<JourneyAdapter.Vh>() {
        class Vh(v: View) : RecyclerView.ViewHolder(v) {
            val t1 = v.findViewById<TextView>(R.id.t1)
            val t2 = v.findViewById<TextView>(R.id.t2)
            val t3 = v.findViewById<TextView>(R.id.t3)
        }

        val data = mutableListOf<Journey>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
            return Vh(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: Vh, position: Int) {
            with(data[position]) {
                holder.apply {
                    t1.text = "行程名称：$name"
                    //t2.text = transportation
                    t3.text = "开始时间：$time"
                    itemView.setOnClickListener {
                        it.context.startActivity(Intent(it.context, DetailActivity::class.java).apply {
                            putExtra("name", name)
                            putExtra("time", time)
                            putExtra("destination", destination)
                            putExtra("transportation", transportation)
                        })
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }
    }
}