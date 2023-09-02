package com.example.tamatemplusassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.DialogFragment
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

        //This will prevent the user from interacting with the WebView until it's fully loaded
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
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
                webViewClient = WebViewClient()

                //load the needed url
                loadUrl(BASE_URL)


            }

            viewBinding.backButton.setOnClickListener(this)
            viewBinding.forwardButton.setOnClickListener(this)
            viewBinding.refreshButton.setOnClickListener(this)
            viewBinding.closeButton.setOnClickListener(this)
        }
    }

    private fun hideLoadingProgress() {
        //This will allow the user to interact with the WebView and hide the loadingProgress
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        binding?.loadingProgressContainer?.visibility = View.GONE
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
                //This will reload the Original page
                binding?.webView?.loadUrl(BASE_URL)

                //And This will reload the current stepped page
                //binding?.webView?.reload()
            }

            R.id.closeButton -> {
                //Close the dialog (I'm Using DialogFragment)
                dismiss()
            }
        }

    }

    // Overriding WebViewClient functions
    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView?, request: WebResourceRequest?
        ): Boolean {
            //this will load the url in the WebView
            view?.loadUrl(request?.url.toString())
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            // Page loading is finished, hide the loadingProgress
            super.onPageFinished(view, url)
            hideLoadingProgress()
        }

        override fun onReceivedError(
            view: WebView?, request: WebResourceRequest?, error: WebResourceError?
        ) {
            // hide the ProgressBar if there is an error
            hideLoadingProgress()
        }

    }
}


