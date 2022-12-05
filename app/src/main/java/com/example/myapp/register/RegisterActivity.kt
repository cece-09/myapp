package com.example.myapp.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp.R
import com.example.myapp.search.SearchActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        naverLoginFragment()
    }

    fun naverLoginFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_frame, NaverLoginFragment())
            .commit()
    }
    fun emailFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_frame, EmailFragment())
            .commit()
    }
    fun nicknameFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_frame, NicknameFragment())
            .commit()
    }
    fun searchActivity() {
        val intent = Intent(this@RegisterActivity, SearchActivity::class.java)
        startActivity(intent)
    }
}