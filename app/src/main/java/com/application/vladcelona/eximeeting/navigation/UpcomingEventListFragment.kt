package com.application.vladcelona.eximeeting.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.vladcelona.eximeeting.EximeetingApplication
import com.application.vladcelona.eximeeting.R
import com.application.vladcelona.eximeeting.event_managment.EventListAdapter
import com.application.vladcelona.eximeeting.event_managment.EventViewModel
import com.application.vladcelona.eximeeting.event_managment.EventViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private const val TAG = "UpcomingEventListFragment"

// TODO: Change Realtime Database for Firestore
class UpcomingEventListFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView

    private lateinit var databaseReference: DatabaseReference
    private lateinit var uid: String

    private val eventViewModel: EventViewModel by viewModels {
        EventViewModelFactory((activity?.application as EximeetingApplication).repository)
    }

    override fun onStart() {
        super.onStart()

        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_upcoming_event_list,
            container, false)

        recyclerView = view.findViewById<RecyclerView>(R.id.upcoming_recyclerview)
        val adapter = EventListAdapter(EventListAdapter.OnClickListener {
            Log.i(TAG, "Clicked an item")
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        eventViewModel.events.observe(viewLifecycleOwner) { events ->
            val upcomingEvents = events.filterIndexed { _, event ->
                event.getStatusCode() != 4
                        && Locale.getDefault().language == event.language
            }

            upcomingEvents.let { adapter.submitList(it) }
        }

        return view
    }

    override fun onResume() {
        super.onResume()

        val adapter = EventListAdapter(EventListAdapter.OnClickListener {
            Log.i(TAG, "Clicked an item")
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        eventViewModel.events.observe(viewLifecycleOwner) { events ->
            val upcomingEvents = events.filterIndexed { _, event ->
                event.getStatusCode() != 4
                        && Locale.getDefault().language == event.language
            }

            upcomingEvents.let { adapter.submitList(it) }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpcomingEventListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}