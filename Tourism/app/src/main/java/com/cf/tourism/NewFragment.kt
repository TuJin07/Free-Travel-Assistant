package com.cf.tourism

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TimePicker
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.f2.*
import java.util.*

class NewFragment : Fragment() {

    private val journeyAdapter = NewAdapter()

    /**
     * 时间选择器中选择的时间
     */
    private var pickedTime: String = ""

    /**
     * 先选日期 2022年6月18日
     */
    private var pickedData: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.f2, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // 获取日历
        val c = Calendar.getInstance()


        newRcv.adapter = journeyAdapter

        fab.setOnClickListener {
            // 显示编辑栏
            editRoot.isVisible = true
        }

        e5.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    Log.d("ysw", "onViewCreated: year=$year, month=$month, dayOfMonth=$dayOfMonth")
                    // bug, 月份需要+1
                    pickedData = "${year}年${month + 1}月${dayOfMonth}日"
                    e5.text = pickedData
                },
                c.get(Calendar.YEAR),
                // 月需要+1
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // 显示时间选择器
        e3.setOnClickListener {
            TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    Log.d("ysw", "onViewCreated: hourOfDay=$hourOfDay, m=$minute")
                    // 设置时间，默认就是本年 2022年
                    pickedTime = "${hourOfDay}时${minute}分"
                    e3.text = pickedTime
                },
                c.get(Calendar.HOUR),
                c.get(Calendar.MINUTE),
                false
            ).show()
        }

        // 设置编辑栏, 点击 save 按钮
        save.setOnClickListener {
            if (e1.editableText.toString().isNotEmpty()
                && e2.editableText.toString().isNotEmpty()
                && e4.editableText.toString().isNotEmpty()
            ) {

                journeyAdapter.apply {
                    data.add(
                        Journey(
                            name = textFromEditView(e4),
                            destination = textFromEditView(e1),
                            transportation = textFromEditView(e2),
                            time = pickedData + pickedTime
                        )
                    )

                    // 同时赋值给ViewModel
                    dataViewModel(requireActivity()).setValue(data)

                    notifyDataSetChanged()
                    editRoot.isVisible = false
                }
            }
        }
    }

    private fun textFromEditView(e: EditText): String = e.editableText.toString()

    inner class NewAdapter : HomeFragment.JourneyAdapter() {
        /**
         * 需要设置不同的文本，与[HomeFragment]中的做区别，但是数据是一致的
         *
         * @param holder Vh
         * @param position Int
         */
        override fun onBindViewHolder(holder: Vh, position: Int) {
            super.onBindViewHolder(holder, position)
            with(data[position]) {
                holder.apply {
                    t1.text = "目的地：$destination"
                    t2.text = "交通方式：$transportation"
                    t3.text = "时间：$time"
                }
            }
        }
    }
}