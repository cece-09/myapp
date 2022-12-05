package com.example.myapp.data

interface Domain {
    fun asDto(): Dto
}

interface Dto {
    val collection: String
    val documentId: String
    val data: HashMap<String, Any>
    fun asDomain(): Domain
}

data class User(
    val userId: String = "",
    val gender: String = "",
    val email: String = "",
    val nickname: String = "",
    // val token: HashMap<String, String> = hashMapOf("" to "")
): Domain {
    override fun asDto(): Dto {
        return UserDto(
            collection = "User",
            documentId = this.userId,
            data = hashMapOf(
                "gender" to this.gender,
                "email" to this.email,
                "nickname" to this.nickname
            )
        )
    }
}

data class UserDto(
    override val collection: String = "User",
    override val documentId: String = "",
    override val data: HashMap<String, Any> = hashMapOf("" to "")
): Dto {
    override fun asDomain(): Domain {
        return User(
            userId = this.documentId,
            gender = this.data["gender"] as String,
            nickname = this.data["nickname"] as String,
            email = this.data["email"] as String
        )
    }
}
