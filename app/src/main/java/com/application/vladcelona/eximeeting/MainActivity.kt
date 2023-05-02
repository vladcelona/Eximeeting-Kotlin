package com.application.vladcelona.eximeeting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.application.vladcelona.eximeeting.data_classes.Event
import com.application.vladcelona.eximeeting.data_classes.User
import com.application.vladcelona.eximeeting.databinding.ActivityMainBinding
import com.application.vladcelona.eximeeting.event_managment.*
import com.application.vladcelona.eximeeting.navigation.CompletedEventListFragment
import com.application.vladcelona.eximeeting.navigation.UpcomingEventListFragment
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MainActivity : AppCompatActivity() {

//    lateinit var mainBinding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        navController = this.findNavController(R.id.nav_host_fragment)

        // Find reference to bottom navigation view
        navView = findViewById(R.id.bottom_nav_view)

        // Hook your navigation controller to bottom navigation view
        navView.setupWithNavController(navController)
        navView.visibility = View.INVISIBLE

        val navOptions = NavOptions.Builder().setPopUpTo(R.id.startFragment, true).build()

        if (User.checkAccess()) {
            Toast.makeText(this@MainActivity,
                "You have been logged in successfully", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.action_startFragment_to_upcomingEventsListFragment,
                bundleOf(), navOptions)
            navView.visibility = View.VISIBLE
        }

//        val databaseReference = FirebaseDatabase.getInstance().getReference("Users")
//        // Check if user has an account in firebase
//        FirebaseAuth.getInstance().currentUser.let { it?.let { it1 ->
//            databaseReference.child(it1.uid).get().addOnCompleteListener {
//                    task: Task<DataSnapshot> ->
//                if (task.isSuccessful && task.result.exists()) {
//                    Toast.makeText(this@MainActivity,
//                        "You have been logged in successfully", Toast.LENGTH_SHORT).show()
//                    navController.navigate(R.id.action_startFragment_to_upcomingEventsListFragment,
//                        bundleOf(), navOptions)
//                    navView.visibility = View.VISIBLE
//                }
//            }
//        }
//        }
    }
}