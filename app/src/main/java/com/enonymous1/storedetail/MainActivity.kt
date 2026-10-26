package com.enonymous1.storedetail

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    StoreDetailScreen(
                        onShare = { text ->
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                            }
                            startActivity(Intent.createChooser(intent, "Share"))
                        },
                        onOpen = {
                            Toast.makeText(
                                this,
                                "Demo detail page: Field Notes is fictional, so there is nothing installed to open.",
                                Toast.LENGTH_LONG,
                            ).show()
                        },
                        onInfo = { message ->
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                        },
                    )
                }
            }
        }
    }
}
