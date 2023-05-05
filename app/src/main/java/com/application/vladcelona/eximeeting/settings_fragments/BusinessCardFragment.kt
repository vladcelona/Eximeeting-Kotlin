package com.application.vladcelona.eximeeting.settings_fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.application.vladcelona.eximeeting.R
import com.application.vladcelona.eximeeting.data_classes.User
import com.application.vladcelona.eximeeting.databinding.FragmentBusinessCardBinding
import com.application.vladcelona.eximeeting.firebase.EximeetingFirebase
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val TAG = "BusinessCardFragment"

class BusinessCardFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentBusinessCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE

        val bottomNavView = activity?.findViewById(R.id.bottom_nav_view) as BottomNavigationView
        bottomNavView.visibility = View.INVISIBLE

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBusinessCardBinding.inflate(inflater, container, false)

        val databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)!!

                binding.businessCardFullName.text = user.fullName
                binding.businessCardEmail.text = user.email
                binding.businessCardPhoneNumber.text = user.phoneNumber
                binding.businessCardPosition.text = user.position
                binding.businessCardWebsite.text = user.website

                binding.businessCardWebsiteQrCode.setImageBitmap(user.convertToQRCode())
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to get data from Firebase",
                    Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BusinessCardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}