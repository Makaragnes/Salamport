package com.example.salamport.ui.fragments

import androidx.fragment.app.Fragment
import com.example.salamport.R
import com.example.salamport.utilits.APP_ACTIVITY
import com.example.salamport.utilits.hideKeyboard

/* Главный фрагмент, содержит все чаты, группы и каналы с которыми взаимодействует пользователь*/

class MainFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Salamport"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
    }
}
