package com.example.firebasechatt

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {

    private const val USERS_COLLECTION = "users"
    private const val MESSAGES_COLLECTION = "messages"

    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private fun currentUser() = auth.currentUser

    fun signIn(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun signUp(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUserId(): String? {
        return currentUser()?.uid
    }

    fun getCurrentUserEmail(): String? {
        return currentUser()?.email
    }

    fun saveMessage(message: Message) {
        val currentUserId = getCurrentUserId()
        if (currentUserId != null) {
            firestore.collection(MESSAGES_COLLECTION)
                .add(message.copy(userId = currentUserId))
        }
    }

    fun getMessages(onSuccess: (List<Message>) -> Unit, onError: (Exception) -> Unit) {
        firestore.collection(MESSAGES_COLLECTION)
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val messages = mutableListOf<Message>()
                for (document in querySnapshot) {
                    val message = document.toObject(Message::class.java)
                    messages.add(message)
                }
                onSuccess(messages)
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
}
