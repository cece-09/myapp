package com.example.myapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.data.MyTimestamp

class SearchViewModel: ViewModel() {

    /** dependencies */
//    private val repository = BaseRepository()
//    private val retrofit = RetrofitClient.getXMLInstance()
//    private val jusoService = retrofit.create(JusoService::class.java)

    /** livedata */
    private val now = MyTimestamp()

//    private var _start = MutableLiveData<Juso>()
//    private var _end = MutableLiveData<Juso>()
    private var _keyword = MutableLiveData<String>()
    private var _date = MutableLiveData(Triple(now.year, now.month, now.day))
    private var _time = MutableLiveData(Pair(now.hour, now.minute))
    private var _searchingJusoOf = MutableLiveData<String>()

//    val start: LiveData<Juso> get() = _start
//    val end: LiveData<Juso> get() = _end
    val keyword: LiveData<String> get() = _keyword
    val date: LiveData<Triple<Int, Int, Int>> get() = _date
    val time: LiveData<Pair<Int, Int>> get() = _time
    val searchingJusoOf: LiveData<String> get() = _searchingJusoOf

    fun setKeyword(km: String) {
        _keyword.value = km
    }

    /** set date value */
    fun setDate(year: Int, month: Int, day: Int) {
        _date.value = Triple(year, month, day)
    }

    /** set time value */
    fun setTime(hour: Int, minute: Int){
        _time.value = Pair(hour, minute)
    }

//    fun setJuso(juso: Juso) {
//        when(searchingJusoOf.value) {
//            "start" -> _start.value = juso
//            "end" -> _end.value = juso
//        }
//    }

    fun setSearchingJusoOf(selector: String) {
        when(selector) {
            "start" -> _searchingJusoOf.value = "start"
            "end" -> _searchingJusoOf.value = "end"
        }
    }

    /** get selected date string */
    fun getDate(): String? {
        return date.value?.let {
            "${it.second + 1}월 ${it.third}일"
        }
    }

    /** get selected time string */
    fun getTime(): String? {
        return time.value?.let {
            "${it.first}시 ${it.second}분"
        }
    }

    fun getDateTime(): Long? {
        return date.value?.let { date ->
            time.value?.let { time ->
                MyTimestamp(
                    date.first,
                    date.second,
                    date.third,
                    time.first,
                    time.second,
                    0
                ).asTimeMillis()
            }
        }
    }

//    fun getStart(): String? {
//        return start.value?.asString()
//    }
//
//    fun getEnd(): String? {
//        return end.value?.asString()
//    }
//
//    fun getKeyword(): String {
//        return keyword.value!!.toString()
//    }
//
//
//    suspend fun saveTeam() {
//        return repository.create(Team(
//            "sampleTeamId",
//            getDateTime()!!,
//            start.value!!,
//            end.value!!,
//            0,
//            4,
//            1
//        ).asDto()
//        )
//    }
}