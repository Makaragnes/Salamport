package com.example.salamport.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.salamport.R
import com.example.salamport.utilits.APP_ACTIVITY

class AnserFragment : BaseFragment(R.layout.fragment_anser) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "О нас"
    }
}