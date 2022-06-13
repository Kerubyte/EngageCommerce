package com.kerubyte.engagecommerce.feature.auth.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.kerubyte.engagecommerce.feature.auth.domain.Authenticator
import javax.inject.Inject

class AuthenticatorImpl
    @Inject constructor(
        private val firebaseAuth: FirebaseAuth
    ): Authenticator {

    override suspend fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun loginUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }
}