package com.cf.tourism

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            if (name.editableText.isNotEmpty() && ps.editableText.isNotEmpty()) {
                if (name.editableText.toString() == UserManager.user.count
                    && ps.editableText.toString() == UserManager.user.ps) {
                    finishAndRemoveTask()
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }
}