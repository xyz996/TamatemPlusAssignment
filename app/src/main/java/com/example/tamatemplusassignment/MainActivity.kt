package com.example.tamatemplusassignment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.tamatemplusassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {


    //Use ViewBinding to access the views
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Initialize the ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //Set the click listener for the button
        binding?.openWebViewButton?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view?.id == R.id.openWebViewButton) {
            openWebViewFragment()
        }
    }

    private fun openWebViewFragment() {


        //Uncomment this line to show the WebView as a Dialog
        //TamatemWebViewFragment().show(supportFragmentManager, "TamatemWebViewFragment")

        // this will show the WebView as a Screen
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(android.R.id.content, TamatemWebViewFragment())
        fragmentTransaction.commit()
    }


}
