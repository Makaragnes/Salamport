package com.example.salamport.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.salamport.R
import com.example.salamport.database.USER
import com.example.salamport.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override fun onResume() {
        super.onResume()
        initFildes()
    }

    private fun initFildes() {
        profile_full_name.text = USER.fullname
        profile_user_photo.downloadAndSetImage(USER.photoUrl)
        settings_phone_number.text = USER.phone
        settings_username.text = USER.fullname
        settings_bio.text = "28 лет"
        settings_bio1.text = "Россия"
        settings_bio3.text = "Целеустремленный молодой гражданин"
    }
}