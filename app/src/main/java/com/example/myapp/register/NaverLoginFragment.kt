package com.example.myapp.register

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.myapp.databinding.FragmentNaverLoginBinding
import com.example.myapp.viewmodel.RegisterViewModel
import com.navercorp.nid.NaverIdLoginSDK
import kotlinx.coroutines.launch

class NaverLoginFragment : Fragment() {
    private lateinit var binding: FragmentNaverLoginBinding
    private lateinit var viewmodel: RegisterViewModel
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]
        sharedPref = requireActivity().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNaverLoginBinding.inflate(inflater, container, false)

        autoLogin()
        init()

        return binding.root
    }

    private fun init() {
        val activity = context as RegisterActivity

        val clientId = "gr3SF8Z7Xn4PJYc5pFUp"
        val clientSecret = "VmnJ5wdiPn"
        val clientName = "prj1114"

        NaverIdLoginSDK.apply {
            showDevelopersLog(true)
            initialize(activity, clientId, clientSecret, clientName)
            isShowMarketLink = true
            isShowBottomTab = true
        }

        binding.naverLogin.setOnClickListener{
            lifecycleScope.launch{
                viewmodel.login(activity)
                activity.emailFragment()
            }
        }
    }

    private fun autoLogin() {
        when(val userId = getSharedPref()) {
            null -> Toast.makeText(context, "자동 로그인 실패. 네이버로 시작하기 버튼 클릭", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(context, "자동 로그인. User Id $userId", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSharedPref(): String? {
        return sharedPref.getString("id", null)
    }
}