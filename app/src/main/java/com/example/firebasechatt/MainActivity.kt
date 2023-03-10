package com.example.firebasechatt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // Skapa en instans av Firebase Authentication
    private lateinit var auth: FirebaseAuth

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisera Firebase Authentication
        auth = Firebase.auth

        // Konfigurera knapp för inloggning
        val loginButton: Button = findViewById(R.id.login_button)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton.setOnClickListener {
            signIn()
        }
    }

    // Funktion för inloggning med e-post och lösenord
    private fun signIn() {
        val email: String = emailEditText.text.toString()
        val password: String = passwordEditText.text.toString()

        // Validera e-post och lösenord
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fyll i e-post och lösenord", Toast.LENGTH_SHORT).show()
            return
        }

        // Logga in med Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inloggning lyckades, öppna ChatActivity
                    val intent = Intent(this, ChatActivity::class.java)
                    startActivity(intent)
                } else {
                    // Inloggning misslyckades, visa felmeddelande
                    Toast.makeText(this, "Fel e-post eller lösenord", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
