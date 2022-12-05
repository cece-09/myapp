package com.example.myapp.data

import retrofit2.Call
import retrofit2.http.*
import java.util.*

data class FCMRes(
    var result: String? = null
)

data class EmailRes(
    var status: String? = null,
    var info: String? = null,
    var number: Number? = null
)

interface NodeServerInterface {
    @FormUrlEncoded
    @POST("/fcm")
    fun fcmPostRequest(
        @Field("title") title: String,
        @Field("body") body: String,
        @Field("tokens") tokens: Array<String>): Call<FCMRes>

    @FormUrlEncoded
    @POST("/mail")
    fun mailPostRequest(@Field("email") email: String): Call<EmailRes>
}

data class MyTimestamp(
    var year: Int,
    var month: Int,
    var day: Int,
    var hour: Int,
    var minute: Int,
    var second: Int,
) {
    constructor(
        now: Calendar = Calendar.getInstance(),
    ) : this(
        now.get(Calendar.YEAR),
        now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH),
        now.get(Calendar.HOUR_OF_DAY),
        now.get(Calendar.MINUTE),
        now.get(Calendar.SECOND)
    )

    fun asTimeMillis(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(this.year, this.month, this.day, this.hour, this.minute, this.second)
        return calendar.timeInMillis
    }
}