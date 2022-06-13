package com.kerubyte.engagecommerce.feature.auth.domain

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface Authenticator {

    suspend fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>

    suspend fun loginUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>

    fun getCurrentUserUid(): String?
}