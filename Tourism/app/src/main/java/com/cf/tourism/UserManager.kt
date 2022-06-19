package com.cf.tourism

object UserManager {
    val user by lazy {
        User(
            "19030400025",
            "serum",
            "123456"
        )
    }

    fun updateName(name: String) {
        user.name = name
    }

    fun updatePs(ps: String) {
        user.ps = ps
    }
}