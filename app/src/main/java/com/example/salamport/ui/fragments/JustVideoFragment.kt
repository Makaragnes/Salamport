package com.example.salamport.ui.fragments

import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.salamport.R
import kotlinx.android.synthetic.main.fragment_just_video.*


class JustVideoFragment : BaseFragment(R.layout.fragment_just_video) {

    val url = "https://appr.tc/r/02511122223"

    override fun onResume() {
        super.onResume()

            webView.webViewClient = object : WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
        val webSettings = webView.settings
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)


            }
        })

        webSettings.javaScriptEnabled = true
        webView.loadUrl(url)
    }
}