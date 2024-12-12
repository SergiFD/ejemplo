package com.example.p51miniagenda_phonebook_sff

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var player: EditText
    private lateinit var telefono: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        player = findViewById(R.id.editText_name)
        telefono = findViewById(R.id.editTextPhone2)
        val clear = findViewById<Button>(R.id.buttonClear)
        val save = findViewById<Button>(R.id.save_botton)
        val getPhone = findViewById<Button>(R.id.getPhone)
        val sharedPref = getPreferences(MODE_PRIVATE)

        save.setOnClickListener {
            val name = player.text.toString().trim().uppercase()
            val telefono2 = telefono.text.toString().trim().uppercase()

            if (name.isEmpty() || (telefono2.isEmpty()) && !sharedPref.contains(name)) {
                // Mostrar mensajes de error si alguno de los campos está vacío
                Toast.makeText(this, getString(R.string.no_buits), Toast.LENGTH_SHORT).show()
                if (name.isEmpty()) player.error = getString(R.string.no_buitN)
                if (telefono2.isEmpty()) telefono.error = getString(R.string.no_buitT)
            } else {

                // Si ambos campos están llenos, proceder a guardar los datos
                if (sharedPref.contains(name)) {
                    if (telefono2.isNotEmpty()) {
                        preguntarAct(name) //actualizar
                    } else {
                        preguntarEli(name) //quiere eliminar
                    }
                } else {
                    // Si el nombre no existe, guardar los datos
                    val editor: SharedPreferences.Editor = sharedPref.edit()
                    editor.putString(name, telefono2)
                    editor.apply()
                    Toast.makeText(this, getString(R.string.save_data), Toast.LENGTH_SHORT).show()
                    clear()
                }
            }
        }

        getPhone.setOnClickListener {
            val name = player.text.toString()

            if (name.isNotEmpty()) {
                val telefonoGuardado = sharedPref.getString(name.uppercase(), null)
                if (telefonoGuardado != null) {
                    telefono.setText(telefonoGuardado)
                } else {
                    telefono.setText("")
                    Toast.makeText(this, getString(R.string.no_trobat), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.introduir_nom), Toast.LENGTH_SHORT).show()
            }
        }

        clear.setOnClickListener {
            clear()
        }
    }

    fun preguntarEli(name: String) {
        val (dialogView, dialog) = crearDialogo(R.layout.dialog_custom)

        val cancelarButton = dialogView.findViewById<Button>(R.id.dialog_Cancelar_button)
        val deleteButton = dialogView.findViewById<Button>(R.id.dialog_actualizar_button)

        deleteButton.setOnClickListener {
            val sharedPref = getPreferences(MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.remove(name.uppercase())
            editor.apply()
            Toast.makeText(this, getString(R.string.eliminat), Toast.LENGTH_SHORT).show()
            clear()
            cerrarDialogo(dialog)
        }
        cancelarButton.setOnClickListener {
            cerrarDialogo(dialog, getString(R.string.cancelat))
        }

        dialog.show()
    }

    fun preguntarAct(name: String) {
        val (dialogView, dialog) = crearDialogo(R.layout.dialog_custom_update)

        val cancelarButton = dialogView.findViewById<Button>(R.id.dialog_Cancelar_button)
        val updateButton = dialogView.findViewById<Button>(R.id.dialog_actualizar_button)

        updateButton.setOnClickListener {
                val sharedPref = getPreferences(MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.apply()
                Toast.makeText(this, "Actualizado correctamente.", Toast.LENGTH_SHORT).show()
                clear()
                cerrarDialogo(dialog)
        }
        cancelarButton.setOnClickListener {
            cerrarDialogo(dialog, getString(R.string.cancelat))
        }

        dialog.show()
    }

    fun clear() {
        player.text.clear()
        telefono.text.clear()
    }

    private fun crearDialogo(layoutResId: Int): Pair<View, AlertDialog> {
        val dialogView = layoutInflater.inflate(layoutResId, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()
        return Pair(dialogView, dialog)
    }

    private fun cerrarDialogo(dialog: AlertDialog, mensaje: String? = null) {
        mensaje?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        dialog.dismiss()
    }
}
