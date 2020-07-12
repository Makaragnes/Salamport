package com.example.salamport.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.salamport.R
import com.example.salamport.utilits.APP_ACTIVITY
import kotlinx.android.synthetic.main.fragment_intalagent_bot.*
import kotlinx.android.synthetic.main.fragment_maps.*

class IntalagentBotFragment : BaseFragment(R.layout.fragment_intalagent_bot) {

    private val url = "http://192.168.1.43:80/bot"

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "AI-помощник"

        bot_web_view.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        val webSettings = bot_web_view.settings

        webSettings.javaScriptEnabled = true
        bot_web_view.loadUrl(url)
    }
}