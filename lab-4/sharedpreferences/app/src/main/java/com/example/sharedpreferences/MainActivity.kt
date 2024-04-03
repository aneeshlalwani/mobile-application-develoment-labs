package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sharedpreferences.ui.theme.SharedpreferencesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharedpreferencesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    ColorPreferenceScreen(this)
                }
            }
        }
    }
}
@Composable
fun ColorPreferenceScreen(context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val savedColor = remember { mutableStateOf(sharedPreferences.getString("favoriteColor", "") ?: "") }
    val newColor = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextField(
            value = newColor.value,
            onValueChange = { newColor.value = it },
            label = { Text("Enter your favorite color") },
            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
        )
        Button(onClick = {
            val editor = sharedPreferences.edit()
            editor.putString("favoriteColor", newColor.value)
            editor.apply()
            savedColor.value = newColor.value
        },modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
            ) {
            Text("Save Color")
        }
        Button(onClick = {
            savedColor.value = sharedPreferences.getString("favoriteColor", "") ?: ""
        }, modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
            Text("Retrieve Color")
        }
        Text("Your favorite color: ${savedColor.value}", modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
        )}
}
