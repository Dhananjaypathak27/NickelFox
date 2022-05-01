package com.inxparticle.nickelfox

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.inxparticle.nickelfox.databinding.ActivityMainBinding
import com.inxparticle.nickelfox.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainActivity"
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityMainBinding =DataBindingUtil.setContentView(this,R.layout.activity_main)
        activityMainBinding.lifecycleOwner = this
        activityMainBinding.viewModel =viewModel
        viewModel.fetchJsonPlaceHolderData()

        lifecycleScope.launchWhenCreated {
            viewModel.placeHolder.collect{event->
            when(event){
                is MainViewModel.JsonPlaceHolderEvent.Success->
                {
                    Log.e(TAG, "onCreate: ${event.result.body}" )
                }
                is MainViewModel.JsonPlaceHolderEvent.Failure->{
                    Log.e(TAG, "onCreate: ${event.errorText}")
                     Snackbar.make(activityMainBinding.parentLayout, "error - @{${event.errorText}}", Snackbar.LENGTH_LONG).show()
                }
                else -> Unit
            }
            }
        }
    }
}