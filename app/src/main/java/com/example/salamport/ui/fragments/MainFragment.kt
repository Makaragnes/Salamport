package com.example.salamport.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.salamport.R
import com.example.salamport.database.*
import com.example.salamport.models.CommonModel
import com.example.salamport.ui.fragments.single_chat.SingleChatFragment
import com.example.salamport.utilits.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.contact_item.view.*
import kotlinx.android.synthetic.main.fragment_chats.*
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.list_item_contact.view.*

/* Главный фрагмент, содержит все чаты, группы и каналы с которыми взаимодействует пользователь*/

class MainFragment : BaseFragment(R.layout.fragment_chats) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<CommonModel, MainFragment.ChatsHolder>
    private lateinit var mRefContacts: DatabaseReference
    private lateinit var mRefUsers: DatabaseReference
    private lateinit var mRefUsersListener: AppValueEventListener
    private  var mapListeners = hashMapOf<DatabaseReference, AppValueEventListener>()


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Чаты"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
        initRecycleView()
    }

    private fun initRecycleView() {
        mRecyclerView = chats_recycle_view
        mRefContacts = REF_DATABASE_ROOT.child(
            NODE_PHONES_CONTACTS
        ).child(CURRENT_UID)

        //Настройка для адаптера, где указываем какие данные и откуда получать
        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefContacts, CommonModel::class.java)
            .build()

        //Адаптер принимает данные, отображает в холдере
        mAdapter = object : FirebaseRecyclerAdapter<CommonModel, ChatsHolder>(options) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsHolder {
                //Запускается тогда когда адаптер получает доступ к ViewGroup
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.contact_item, parent, false)
                return ChatsHolder(view)
            }

            // Заполняет holder
            override fun onBindViewHolder(
                holder: ChatsHolder,
                position: Int,
                model: CommonModel
            ) {
                mRefUsers = REF_DATABASE_ROOT.child(
                    NODE_USERS
                ).child(model.id)

                mRefUsersListener = AppValueEventListener {
                    val contact = it.getCommonModel()

                    if (contact.fullname.isEmpty()){
                        holder.name.text = model.fullname
                    } else holder.name.text = contact.fullname

                    holder.status.text = contact.state
                    holder.photo.downloadAndSetImage(contact.photoUrl)
                    holder.itemView.setOnClickListener { replaceFragment(
                        SingleChatFragment(
                            model
                        )
                    ) }
                }

                mRefUsers.addValueEventListener(mRefUsersListener)
                mapListeners[mRefUsers] = mRefUsersListener
            }
        }

        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()

    }

    // Холдер для захвата ViewGroup
    class ChatsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.contact_fullname1
        val status: TextView = view.contact_status
        val photo: CircleImageView = view.contact_photo
    }

    override fun onPause() {
        super.onPause()
        mAdapter.stopListening()
        println()
        mapListeners.forEach {
            it.key.removeEventListener(it.value)
        }
        println()
    }
}
