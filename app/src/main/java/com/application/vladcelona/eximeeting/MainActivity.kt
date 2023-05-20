package com.application.vladcelona.eximeeting

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.application.vladcelona.eximeeting.data_classes.User
import com.application.vladcelona.eximeeting.firebase.EximeetingFirebase
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onStart() {
        super.onStart()
//        EximeetingFirebase.pushEventData()
//        EximeetingFirebase.getEventData()
//
//        val filesDir: File = File("/data/data/com.application.vladcelona.eximeeting/databases")
//        for (file in filesDir.listFiles()!!) {
//            file.delete()
//            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
//        }
//
//        filesDir.parentFile?.createNewFile()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = this.findNavController(R.id.nav_host_fragment)

        // Find reference to bottom navigation view
        navView = findViewById(R.id.bottom_nav_view)

        // Hook navigation controller to bottom navigation view
        navView.setupWithNavController(navController)
        navView.visibility = View.INVISIBLE

        val navOptions = NavOptions.Builder().setPopUpTo(R.id.startFragment, true).build()
        navController.navigate(R.id.startFragment, bundleOf(), navOptions)
    }
}