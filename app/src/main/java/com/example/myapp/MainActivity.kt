package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapp.register.NaverLoginFragment
import com.example.myapp.register.RegisterActivity
import com.example.myapp.search.SearchActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerActivity()
    }

    private fun registerActivity() {
        val intent = Intent(this@MainActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun searchActivity() {
        val intent = Intent(this@MainActivity, SearchActivity::class.java)
        startActivity(intent)
    }

    private fun chkSharedPref(){

    }
}