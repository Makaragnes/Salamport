package com.example.salamport.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.salamport.R
import kotlinx.android.synthetic.main.fragment_maps.*
import java.net.URL


class MapsFragment : BaseFragment(R.layout.fragment_maps) {

    //private lateinit var mapWebView: WebView
    val url = "https://studio.here.com/viewer/?project_id=adf9413d-64a7-44c0-b5b5-7e65c931bdd6"

    override fun onResume() {
        super.onResume()

        map_web_view.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        val webSettings = map_web_view.settings

        webSettings.javaScriptEnabled = true
        map_web_view.loadUrl(url)
    }
}