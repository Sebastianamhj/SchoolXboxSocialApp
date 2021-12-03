package com.sebastianamhj.schoolfinalproject.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.sebastianamhj.schoolfinalproject.ui.main.DisplayUserList
import com.sebastianamhj.schoolfinalproject.ui.main.MainView
import com.sebastianamhj.schoolfinalproject.ui.theme.SchoolFinalProjectTheme
import com.sebastianamhj.schoolfinalproject.utils.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            SchoolFinalProjectTheme() {
                Scaffold() {
                    Navigation(navController = navController)
                }
            }
        }

        FirebaseApp.initializeApp(this)
    }
}