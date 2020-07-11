package com.example.salamport.ui.fragments.testFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.salamport.R
import com.example.salamport.ui.fragments.BaseFragment


class SomeTestFragment : BaseFragment(R.layout.fragment_some_test) {

    override fun onResume() {
        super.onResume()
        initToolbar()
        initResucleView()

    }

    private fun initToolbar() {

    }

    private fun initResucleView() {

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}