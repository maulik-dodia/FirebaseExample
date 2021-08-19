package com.firebaseexample.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.firebaseexample.R
import com.firebaseexample.adapter.ProductListAdapter
import com.firebaseexample.databinding.ActivityMainBinding
import com.firebaseexample.model.BaseRes
import com.firebaseexample.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.resultsLiveData.observe(this, { response ->
            when (response) {
                is BaseRes.Loading -> {
                    showProgressBar()
                }
                is BaseRes.Success -> {
                    response.data?.let { successRes ->
                        val productListAdapter = ProductListAdapter(this, successRes as ArrayList<String>)
                        binding.rvProductList.adapter = productListAdapter
                    }
                }
                is BaseRes.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(
                            this,
                            String.format(getString(R.string.str_error_occurred), message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        binding.apply {
            progressBar.visibility = View.GONE
            rvProductList.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}