package ch.ffhs.esa.hereiam.services

import ch.ffhs.esa.hereiam.HereIAmApplication
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

interface AuthenticationService {
    suspend fun loginUser(email: String, password: String)
    suspend fun registerUser(email: String, password: String)
    fun signOut()
    fun updateCurrentUser()
}

class AuthenticationServiceFirebaseAuth : AuthenticationService {
    private val fbAuth = FirebaseAuth.getInstance()

    override suspend fun loginUser(email: String, password: String) {
        fbAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun registerUser(email: String, password: String) {
        fbAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override fun signOut() {
        fbAuth.signOut()
        updateCurrentUser()
    }

    override fun updateCurrentUser() {
        HereIAmApplication.currentUser = fbAuth.currentUser
    }
}