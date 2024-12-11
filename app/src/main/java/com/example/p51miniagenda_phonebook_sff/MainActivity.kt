package com.example.p51miniagenda_phonebook_sff

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val save = findViewById<Button>(R.id.save_botton)
        val get = findViewById<Button>(R.id.getPhone)
        val player = findViewById<EditText>(R.id.editText_name)
        val telefono = findViewById<EditText>(R.id.editTextPhone2)
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        val name = player.text.toString()
        val telefono2 = telefono.text.toString()

        save.setOnClickListener {
                     val editor: SharedPreferences.Editor = sharedPref.edit()

            editor.putString("name", name)
            editor.putString("telefono", telefono2)

            editor.apply()
        }

        get.setOnClickListener {

            val name: String =
                sharedPref.getString("name", name) ?: "NameDefault"

            val telefono2: String =
                sharedPref.getString("telefono", telefono2) ?: "PhoneDefault"

        }
    }
}