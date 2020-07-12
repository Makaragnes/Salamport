package com.example.salamport.ui.fragments

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.salamport.R
import com.example.salamport.database.ContactData
import com.example.salamport.database.FirebaseData
import com.example.salamport.database.FirebaseData.myID
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_video_call_fragment.*

class VideoCallFragmentFragment : BaseFragment(R.layout.fragment_video_call_fragment) {

    private lateinit var adapter: ArrayAdapter<Pair<String, ContactData>>

    private lateinit var callRef: DatabaseReference

    override fun onResume() {
        super.onResume()

        //val listView: ListView = findViewById(R.id.list)
        adapter = context?.let { ContactsAdapter(it, ArrayList<Pair<String, ContactData>>(0)) }!!
        adapter.setNotifyOnChange(true)
        list.adapter = adapter

        list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.w("TAG", "clicked: " + adapter.getItem(position))
            adapter.getItem(position)?.first?.let { startVideoCall(it) }
        }
        val emptyTextView =  TextView(context)
        emptyTextView.setText("No contacts available")
        list.emptyView = emptyTextView

        init()

    }

    private fun init() {
        FirebaseData.init()

        callRef = FirebaseData.database.getReference("calls/$myID/id")


        textView.text = "My id: $myID"
    }

    private fun startVideoCall(key: String) {
        FirebaseData.getCallStatusReference(myID).setValue(true)
        FirebaseData.getCallIdReference(key).onDisconnect().removeValue()
        FirebaseData.getCallIdReference(key).setValue(myID)
        //startCall(this, key)
    }

    private fun receiveVideoCall(key: String) {
        //receiveCall(this, key)
    }

//    fun startCall(context: VideoCallFragmentFragment, id: String) {
//        val starter = Intent(context, VideoCallActivity2::class.java)
//        starter.putExtra("offer", true)
//        starter.putExtra("id", id)
//        context.startActivity(starter)
//    }
//
//    fun receiveCall(context: VideoCallFragmentFragment, id: String) {
//        val starter = Intent(context, VideoCallActivity2::class.java)
//        starter.putExtra("offer", false)
//        starter.putExtra("id", id)
//        context.startActivity(starter)
//    }

    private val callListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.exists()) {
                receiveVideoCall(dataSnapshot.getValue(String::class.java)!!)
                callRef.removeValue()
            }
        }

        override fun onCancelled(error: DatabaseError) {
        }
    }

//    private val usersListener = object : ValueEventListener {
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            adapter.clear()
//            if (!dataSnapshot.exists()) {
//                return
//            }
//            dataSnapshot.children.forEach {
//                if (it.exists() && it.key != myID)
//                    adapter.add(Pair(dataSnapshot.key, dataSnapshot.getValue(ContactData::class.java)))
//            }
//        }
//
//        override fun onCancelled(error: DatabaseError) {
//        }
//    }

    inner class ContactsAdapter(context: Context, contacts: List<Pair<String, ContactData>>) : ArrayAdapter<Pair<String, ContactData>>(context, 0, contacts) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var convertView = convertView
            val contact = getItem(position)
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item_contact, parent, false)
            }

            val txtName = convertView!!.findViewById(R.id.txtName) as TextView
            val imageView = convertView.findViewById(R.id.imageView) as ImageView
            // Populate the data into the template view using the data object
            txtName.text = contact?.second?.name
            imageView.setImageResource(if(contact?.second?.online!!) R.drawable.round_green else R.drawable.round_red)

            return convertView
        }
    }
}