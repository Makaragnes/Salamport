package com.example.salamport.ui.fragments.testFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.salamport.R
import com.example.salamport.ui.fragments.BaseFragment


class TestListFragment : BaseFragment(R.layout.fragment_test_list) {

    private lateinit var mRecycleView: RecyclerView

    override fun onResume() {
        super.onResume()

        initToolbar()
        initResucleView()


    }

    private fun initResucleView() {

    }

    private fun initToolbar() {

    }
}