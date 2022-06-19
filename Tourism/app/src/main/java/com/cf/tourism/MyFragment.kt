package com.cf.tourism

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.f3.*


class MyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.f3, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataViewModel(requireActivity()).uriLiveData.observe(requireActivity()) {
            header.setImageURI(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(this).load(UserManager.user.url).into(header)
        username.apply {
            text = UserManager.user.name
            setOnClickListener {
                showInputDialog()
            }
        }

        header.setOnClickListener {
            (requireActivity() as MainActivity).choosePhotoLauncher.launch("image/*")
        }

        exit.setOnClickListener {
            requireActivity().finishAndRemoveTask()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        contract.setOnClickListener {
            startActivity(Intent(requireContext(), ContractActivity::class.java))
        }
    }

    /**
     * 编辑对话框
     */
    private fun showInputDialog() {
        val editText = EditText(requireContext())

        // 配置编辑框
        editText.apply {
            hint = "输入新名称"
            background = ContextCompat.getDrawable(requireContext(), R.drawable.tv)
            setPadding(30, 20, 0, 20)
        }

        val inputDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        inputDialog.setTitle("修改昵称").setView(editText)
        inputDialog.setPositiveButton("确定"
        ) { dialog, which ->
            UserManager.user.name = editText.editableText.toString()
            username.text = editText.editableText.toString()
        }.show().apply {
            // 需要添加到window上才能更新外边距！
            editText.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = 40
                leftMargin = 60
                rightMargin = 60
            }
        }
    }
}