package com.application.vladcelona.eximeeting.firebase

import android.util.Log
import com.application.vladcelona.eximeeting.data_classes.Event
import com.application.vladcelona.eximeeting.data_classes.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlin.random.Random

private const val TAG = "EximeetingFirebase"

class EximeetingFirebase {

    companion object {

        fun getEventData(): List<Event> {
            val events: ArrayList<Event> = ArrayList()

            val databaseReference: DatabaseReference = FirebaseDatabase
                .getInstance().getReference("Events")
            databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (eventSnapshot in snapshot.children) {
                        val jsonEvent: String? = eventSnapshot.getValue(String::class.java)
                        if (jsonEvent != null) {
                            events.add(Gson().fromJson(jsonEvent, Event::class.java))
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "The load of Events has been cancelled")
                }

            })

            val eventsId: HashMap<String, Boolean> = HashMap()
            for (event in events) {
                eventsId[event.id.toString()] = false
            }

            Log.i(TAG, Gson().toJson(eventsId))

            return events
        }
    }
}