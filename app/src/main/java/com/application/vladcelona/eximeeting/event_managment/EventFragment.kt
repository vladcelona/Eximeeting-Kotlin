package com.application.vladcelona.eximeeting.event_managment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.application.vladcelona.eximeeting.R
import com.application.vladcelona.eximeeting.data_classes.event.Event
import com.application.vladcelona.eximeeting.data_classes.user.User
import com.application.vladcelona.eximeeting.databinding.FragmentEventBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.yandex.mapkit.Animation
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import kotlin.random.Random

private const val TAG = "EventFragment"
private const val ARG_EVENT_ID = "event_id"

class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding

    private lateinit var databaseReference: DatabaseReference
    private lateinit var user: User
    private lateinit var uid: String
    private lateinit var eventId: String


    // TODO: Change Realtime Database for Firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventBinding.inflate(inflater, container, false)

        binding.fragmentEventName.text = arguments?.getString("name")
        binding.fragmentEventDate.text = arguments?.getString("date")
        binding.fragmentEventLocation.text = arguments?.getString("location")
        binding.fragmentEventOrganizer.text = arguments?.getString("organizer")
        binding.fragmentEventAddress.text = arguments?.getString("address")
        binding.fragmentEventStatus.text = Event.convertStatusCode(arguments?.getInt("status"))
        binding.fragmentEventDescription.text = arguments?.getString("description")
        binding.fragmentEventSpeakers.text = arguments?.getString("speakers")
        binding.fragmentEventModerators.text = arguments?.getString("moderators")
        
        eventId = arguments?.getInt("id").toString()

        binding.fragmentEventStatus.setTextColor(
            Event.getStatusCodeColor
            (arguments?.getInt("status")))

        binding.businessProgrammeButton.setOnClickListener {
            findNavController().navigate(R.id.businessProgrammeFragment,
                bundleOf("businessProgramme" to arguments
                    ?.getSerializable("businessProgramme")))
        }

        val randomPoint: Point = Point(Random.nextDouble(52.751574, 64.751574),
            Random.nextDouble(30.573856, 120.573856))

        binding.fragmentEventLocationMap.map.move(
            CameraPosition(randomPoint, 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F), null
        )

        binding.fragmentEventLocationMap.map.mapObjects.addPlacemark(randomPoint)

        if (uid.isNotEmpty()) {
            setFloatingButton()
        }

        return binding.root
    }


    // TODO: Change Realtime Database for Firestore
    private fun setFloatingButton() {


    }
}