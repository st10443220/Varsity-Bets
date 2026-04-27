package com.keeganboshoff.mobileapplication.data.service.implementation

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.keeganboshoff.mobileapplication.data.service.AccountService

class AccountServiceImplementation : AccountService {
    // Sign In
    override fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.exception) // Pass error back, if any
            }
    }

    // Create Account
    override fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                onResult(it.exception) // Pass error back, if any
            }
    }
}