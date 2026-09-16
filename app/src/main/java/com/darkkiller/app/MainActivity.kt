package com.darkkiller.app

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rikka.shizuku.Shizuku
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var txtStatus: TextView
    private lateinit var btnTest: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtStatus = findViewById(R.id.txtStatus)
        btnTest = findViewById(R.id.btnTest)

        checkShizuku()

        btnTest.setOnClickListener {
            loadConfig("lagfix.json")
        }
    }

    private fun checkShizuku() {

        if (Shizuku.pingBinder()) {

            if (Shizuku.checkSelfPermission()
                == android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {

                txtStatus.text = "✅ Shizuku Connected"

            } else {

                Shizuku.requestPermission(1000)
                txtStatus.text = "⚠ Permission Required"
            }

        } else {

            txtStatus.text = "❌ Shizuku Not Running"
        }
    }

    private fun loadConfig(fileName: String) {

        try {

            val input = assets.open(fileName)

            val reader = BufferedReader(
                InputStreamReader(input)
            )

            val json = reader.readText()

            Toast.makeText(
                this,
                "Config Loaded:\n$json",
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {

            Toast.makeText(
                this,
                e.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
