package com.example.myapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.myapp.data.MyFirestore
import com.example.myapp.data.User
import com.example.myapp.util.NaverLogin
import com.example.myapp.util.NodeServer

class RegisterViewModel : ViewModel() {
    private val tag = "APPLE/ RegisterViewModel"

    private lateinit var naverLogin: NaverLogin
    private lateinit var nodeServer: NodeServer
    private val firestore = MyFirestore()

    /** livedata */
    private var _userId = MutableLiveData<String>()
    private var _gender = MutableLiveData<String>()
    private var _nickname = MutableLiveData<String>()
    private var _email = MutableLiveData<String>()
    private var _code = MutableLiveData<String>()

    val userId: LiveData<String> get() = _userId
    val gender: LiveData<String> get() = _gender
    val nickname: LiveData<String> get() = _nickname
    val email: LiveData<String> get() = _email
    val code: LiveData<String> get() = _code

    fun setEmail(email:String) {
        _email.value = email
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    suspend fun login(context: Context) {
        naverLogin = NaverLogin()
        naverLogin.login(context)
        naverLogin.getProfile().let {
            _userId.value = it.userId
            _gender.value = it.gender
        }
    }

    suspend fun sendEmail(){
        nodeServer = NodeServer()
        email.value?.let {
            _code.value = nodeServer.sendEmail(it)
        }
    }

    suspend fun saveUser(){
        Log.d(tag, "$userId, $gender, $nickname, $email")
        if(chkNull()) {
            return firestore.create(
                User(userId = userId.value!!,
                    gender = gender.value!!,
                    email = email.value!!,
                    nickname = nickname.value!!
                ).asDto())
        } else {
            TODO()
        }
    }

    suspend fun isUniqueNickname(): Boolean {
        return firestore.chkUnique("User", Pair("email", email.value!!)).let {
            when(it) {
                true -> it
                false -> it
                null -> false
            }
        }
    }

    suspend fun isUniqueEmail(): Boolean {
        return firestore.chkUnique("User", Pair("email", email.value!!)).let {
            when(it) {
                true -> it
                false -> it
                null -> false
            }
        }
    }

    private fun chkNull(): Boolean {
        return (userId.value != null) &&
                (gender.value != null) &&
                (email.value != null) &&
                (nickname.value != null)
    }
}