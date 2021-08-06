package com.example.notes.domain.repository

import com.example.notes.domain.dataSource.AuthDataSource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val dataSource: AuthDataSource
) {
    fun login(email: String, password: String) = dataSource.login(email, password)

    fun register(email: String, password: String) = dataSource.register(email, password)

    fun getUser() = dataSource.getUser()
}