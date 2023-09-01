package com.example.tamatemplusassignment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.tamatemplusassignment.databinding.FragmentTamatemWebViewBinding


//declare the base url for the WebView as a constant
private const val BASE_URL = "https://tamatemplus.com"

class TamatemWebViewFragment : DialogFragment(), View.OnClickListener {
    //Use ViewBinding to access the views
    private var binding: FragmentTamatemWebViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, parent: ViewGroup?, savedInstanseState: Bundle?
    ): View? {
        //Initialize the ViewBinding
        binding = FragmentTamatemWebViewBinding.inflate(inflater, parent, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Initialize the UI once the view is created
        initUI()
    }

    private fun initUI() {
        //Initialize the WebView and make sure it's not null and setup the WebView settings
        binding?.let { viewBinding ->
            viewBinding.webView.apply {
                settings.apply {
                    //setup the WebView needed settings
                    loadWithOverviewMode = true
                    javaScriptEnabled = true
                    allowContentAccess = true
                    domStorageEnabled = true
                    useWideViewPort = true
                }

                //setup the WebViewClient to handle the WebView events and show the ProgressBar when needed
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        //once the page is started loading, show the loadingProgress
                        viewBinding.loadingProgress.visibility = View.VISIBLE
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?, request: WebResourceRequest?
                    ): Boolean {
                        // This will show the progress while the page is loading
                        viewBinding.loadingProgress.visibility = View.VISIBLE
                        return true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        // Page loading is finished, hide the loadingProgress
                        viewBinding.loadingProgress.visibility = View.GONE
                    }

                    override fun onReceivedError(
                        view: WebView?, request: WebResourceRequest?, error: WebResourceError?
                    ) {
                        // hide the ProgressBar if there is an error
                        viewBinding.loadingProgress.visibility = View.GONE
                    }

                }


                //load the needed url
                loadUrl(BASE_URL)


            }

            viewBinding.backButton.setOnClickListener(this)
            viewBinding.forwardButton.setOnClickListener(this)
            viewBinding.refreshButton.setOnClickListener(this)
            viewBinding.closeButton.setOnClickListener(this)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.backButton -> {
                //Go back if there is a page to go back toww
                binding?.webView?.let {
                    if (it.canGoBack()) it.goBack()
                }
            }

            R.id.forwardButton -> {
                //Go forward if there is a page to go forward to
                binding?.webView?.let {
                    if (it.canGoForward()) it.goForward()
                }
            }

            R.id.refreshButton -> {
                //This will reload the page
                binding?.webView?.loadUrl(BASE_URL)

                //And This will reload the current page
                //binding?.webView?.reload()
            }

            R.id.closeButton -> {
                //Close the dialog (I'm Using DialogFragment)
                dismiss()
            }
        }

    }
}


