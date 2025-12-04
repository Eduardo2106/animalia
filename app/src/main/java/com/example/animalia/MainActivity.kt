// MainActivity.kt
package com.example.animalia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animalia.ui.MenuFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Carga el Fragmento de Menú al iniciar la Activity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MenuFragment())
                .commit()
        }
    }

    /**
     * Función para cambiar entre Fragmentos (Navegación)
     * @param fragment El nuevo fragmento a mostrar
     * @param tag Una etiqueta para el fragmento
     */
    fun navigateTo(fragment: androidx.fragment.app.Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag) // Permite volver al fragmento anterior con el botón 'Atrás'
            .commit()
    }
}